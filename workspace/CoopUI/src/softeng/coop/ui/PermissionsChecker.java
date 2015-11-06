package softeng.coop.ui;

import softeng.coop.business.*;

import softeng.coop.dataaccess.*;

/**
 * High-level helper for permission checks.
 */
public class PermissionsChecker
{
	private Session session;
	
	private AuthorizationContext authorizationContext;
	
	public PermissionsChecker(ICoopContext context)
	{
		if (context == null) 
			throw new IllegalArgumentException("Argument 'context' must not be null.");
		
		session = context.getSession();
		
		if (session == null)
			throw new CoopUISystemException("Session should be available.");
		
		authorizationContext = session.getAuthorizationContext();
	}
	
	public boolean canBrowseUniversities()
	{
		EntityAccess universityAccess = 
			authorizationContext.hasEntityAccess(University.class.getSimpleName());
		
		EntityAccess departmentAccess =
			authorizationContext.hasEntityAccess(Department.class.getSimpleName());
		
		EntityAccess divisionAccess =
			authorizationContext.hasEntityAccess(Division.class.getSimpleName());

		if (session.getUniversitiesManager() != null &&
				universityAccess != null &&
				departmentAccess != null &&
				divisionAccess != null)
		{
			return 
				universityAccess.isAccessingAllDepartments() &&
				departmentAccess.isAccessingAllDepartments() &&
				divisionAccess.isAccessingAllDepartments() &&
				universityAccess.isReadable() && 
				departmentAccess.isReadable() &&
				divisionAccess.isReadable();
			
		}
		
		return false;
	}
	
	public boolean canBrowseDepartments()
	{
		EntityAccess universityAccess = 
			authorizationContext.hasEntityAccess(University.class.getSimpleName());
		
		EntityAccess departmentAccess =
			authorizationContext.hasEntityAccess(Department.class.getSimpleName());
		
		EntityAccess divisionAccess =
			authorizationContext.hasEntityAccess(Division.class.getSimpleName());

		if (session.getUniversitiesManager() != null &&
				universityAccess != null &&
				departmentAccess != null &&
				divisionAccess != null)
		{
			return 
				departmentAccess.isAccessingAllDepartments() &&
				divisionAccess.isAccessingAllDepartments() &&
				universityAccess.isReadable() && 
				departmentAccess.isReadable() &&
				divisionAccess.isReadable();
		}
		
		return false;
	}
}
