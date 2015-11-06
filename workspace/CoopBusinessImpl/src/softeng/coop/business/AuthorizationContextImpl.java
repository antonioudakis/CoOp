package softeng.coop.business;

import softeng.coop.dataaccess.*;

import java.util.*;

public class AuthorizationContextImpl extends AuthorizationContext
{
	private Set<String> managersSet;
	
	private Map<String, Permission> permissionsMap;

	private Map<String, EntityAccess> entityAccessesMap;
	
	public AuthorizationContextImpl(AuthenticatedUser user)
	{
		if (user == null) throw new IllegalArgumentException("Argument 'user' must not be null.");
		
		this.userName = user.getUserName();
		this.userId = user.getId();
		if (user.getDepartment() != null) 
			this.departmentId = user.getDepartment().getId();
		
		this.managersSet = new HashSet<String>();
		this.permissionsMap = new HashMap<String, Permission>();
		this.entityAccessesMap = new HashMap<String, EntityAccess>();
		
		this.buildMaps(user);
	}

	public String getUserName()
	{
		return this.userName;
	}
	
	public int getUserId()
	{
		return this.userId;
	}
	
	public boolean hasManager(String manager)
	{
		return this.managersSet.contains(manager);
	}
	
	public Iterable<String> getManagers()
	{
		return this.managersSet;
	}
	
	public boolean hasPermission(String permissionName)
	{
		return this.permissionsMap.containsKey(permissionName);
	}
	
	public Iterable<String> getPermissionNames()
	{
		return this.permissionsMap.keySet();
	}
	
	public EntityAccess hasEntityAccess(String entityName)
	{
		EntityAccess entityAccess = this.entityAccessesMap.get(entityName);
		
		if (entityAccess != null)
			return this.copyEntityAccess(entityAccess);
		else
			return null;
	}
	
	private void buildMaps(AuthenticatedUser user)
	{
		this.managersSet.clear();
		this.permissionsMap.clear();
		this.entityAccessesMap.clear();
		
		for (Role role : user.getRoles())
		{
			for (Permission permission : role.getPermissions())
			{
				this.managersSet.add(permission.getManagerName());

				this.permissionsMap.put(permission.getName(), copyPermission(permission));
				
				for (EntityAccess entityAccess : permission.getEntityAccesses())
				{
					EntityAccess existingEntityAccess = this.entityAccessesMap.get(entityAccess.getEntityName());
					
					if (existingEntityAccess != null)
					{
						// Merge
						mergeEntityAccess(entityAccess, existingEntityAccess);
					}
					else
					{
						// Copy
						EntityAccess newEntityAccess = copyEntityAccess(entityAccess);
						
						this.entityAccessesMap.put(newEntityAccess.getEntityName(), newEntityAccess);
					}
				}
			}
		}
	}

	private void mergeEntityAccess(EntityAccess entityAccess, EntityAccess existingEntityAccess)
	{
		if (entityAccess.isReadable()) existingEntityAccess.setReadable(true);
		if (entityAccess.isOwnReadable()) existingEntityAccess.setOwnReadable(true);
		if (entityAccess.isWritable()) existingEntityAccess.setWritable(true);
		if (entityAccess.isOwnWritable()) existingEntityAccess.setOwnWritable(true);
		if (entityAccess.isAccessingAllDepartments()) existingEntityAccess.setAccessingAllDepartments(true);
	}

	private EntityAccess copyEntityAccess(EntityAccess entityAccess)
	{
		EntityAccess newEntityAccess = new EntityAccess();
		
		newEntityAccess.setEntityName(entityAccess.getEntityName());
		newEntityAccess.setReadable(entityAccess.isReadable());
		newEntityAccess.setOwnReadable(entityAccess.isOwnReadable());
		newEntityAccess.setWritable(entityAccess.isWritable());
		newEntityAccess.setOwnWritable(entityAccess.isOwnWritable());
		newEntityAccess.setAccessingAllDepartments(entityAccess.isAccessingAllDepartments());
		
		return newEntityAccess;
	}
	
	private Permission copyPermission(Permission permission)
	{
		Permission newPermission = new Permission();
		
		newPermission.setId(permission.getId());
		newPermission.setName(permission.getName());
		newPermission.setManagerName(permission.getManagerName());
		
		return newPermission;
	}

}
