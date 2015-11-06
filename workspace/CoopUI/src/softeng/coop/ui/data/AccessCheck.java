package softeng.coop.ui.data;

import java.util.*;

import softeng.coop.business.*;
import softeng.coop.business.universities.*;
import softeng.coop.business.coops.*;

import softeng.coop.dataaccess.*;

/**
 * Utility for checking composite security claims.
 */
public class AccessCheck
{
	private Session session;
	
	private boolean hasResolvedUniversityAccess, hasResolvedCoopAccess;
	
	private boolean canBrowseUniversities, canBrowseDepartments, canBrowseDivisions;
	
	private boolean canBrowseDepartmentsCoops;
	
	private UniversitiesManager universitiesManager;
	
	private static List<String> nameDisplayColumns = createDisplayColumns();
	
	private static Object[] sortColumns = { "name" };
	private static boolean[] sortColumnsOrders = { true };
	
	public AccessCheck(Session session)
	{
		if (session == null) 
			throw new IllegalArgumentException("Argument 'session' must not be null.");
		
		this.session = session;
		
		this.hasResolvedUniversityAccess = false;
		this.hasResolvedCoopAccess = false;
	}
	
	private static List<String> createDisplayColumns()
	{
		ArrayList<String> list = new ArrayList<String>(1);
		
		list.add("name");
		
		return list;
	}

	private void resolveUniversityAccess()
	{
		if (hasResolvedUniversityAccess) return;
		
		AuthorizationContext authorizationContext = session.getAuthorizationContext();
		
		EntityAccess universityAccess = 
			authorizationContext.hasEntityAccess(University.class.getSimpleName());
		
		EntityAccess departmentAccess =
			authorizationContext.hasEntityAccess(Department.class.getSimpleName());
		
		EntityAccess divisionAccess =
			authorizationContext.hasEntityAccess(Division.class.getSimpleName());
		
		universitiesManager = session.getUniversitiesManager();
		
		if (universitiesManager != null &&
				universityAccess != null &&
				departmentAccess != null &&
				divisionAccess != null)
		{
			canBrowseUniversities = 
				universityAccess.isAccessingAllDepartments() &&
				departmentAccess.isAccessingAllDepartments() &&
				divisionAccess.isAccessingAllDepartments() &&
				universityAccess.isReadable() && 
				departmentAccess.isReadable() &&
				divisionAccess.isReadable();
			
			canBrowseDepartments = 
				departmentAccess.isAccessingAllDepartments() &&
				divisionAccess.isAccessingAllDepartments() &&
				universityAccess.isReadable() && 
				departmentAccess.isReadable() &&
				divisionAccess.isReadable();
			
			canBrowseDivisions = true;
		}
		else
		{
			canBrowseUniversities = false;
			canBrowseDepartments = false;
			canBrowseDivisions = true;
		}
	}
	
	private void resolveCoopAccess()
	{
		if (hasResolvedCoopAccess) return;
		
		AuthorizationContext authorizationContext = session.getAuthorizationContext();
		
		EntityAccess coopAccess = 
			authorizationContext.hasEntityAccess(CoOp.class.getSimpleName());

		CoOpsManager coopsManager = session.getCoOpsManager();
		
		if (coopsManager != null &&
				coopAccess != null)
		{
			canBrowseDepartmentsCoops = coopAccess.isAccessingAllDepartments();
		}
		else
		{
			canBrowseDepartmentsCoops = false; 
		}
	}
	
	/**
	 * Forces the security claims to be computed anew
	 * when they are queried the next time.
	 */
	public void flush()
	{
		hasResolvedUniversityAccess = false;
	}
	
	public boolean canBrowseUniversities()
	{
		resolveUniversityAccess();
		
		return canBrowseUniversities;
	}
	
	public boolean canBrowseDepartments()
	{
		resolveUniversityAccess();
		
		return canBrowseDepartments;
	}
	
	public boolean canBrowseDivisions()
	{
		resolveUniversityAccess();
		
		return canBrowseDivisions;
	}
	
	public boolean canBrowseDepartmentsCoops()
	{
		resolveCoopAccess();
		
		return canBrowseDepartmentsCoops;
	}
	
	/**
	 * Get a container for the divisions of the non-null department,
	 * else return an empty container if the department is null.
	 */
	public DataItemContainer<Division> getDivisionsContainer(Department department)
	{
		List<Division> divisions;
		
		resolveUniversityAccess();
		
		if (!canBrowseDivisions || department == null) 
		{
			divisions = new ArrayList<Division>(0);
		}
		else
		{
			divisions = new ArrayList<Division>(department.getDivisions());
		}
		
		DataItemContainer<Division> container = new DataItemContainer<Division>(
				Division.class, 
				divisions, 
				universitiesManager,
				nameDisplayColumns);
		
		container.sort(sortColumns, sortColumnsOrders);
		
		return container;
	}
	
	/**
	 * Get a container for the divisions of the department of the user if the
	 * operator has access to divisions, else return the single division of the user.
	 */
	public DataItemContainer<Division> getDivisionsContainer(FacultyUser user)
	{
		if (user == null) 
			throw new IllegalArgumentException("Argument 'user' must not be null.");
		
		List<Division> divisions;
		
		resolveUniversityAccess();
		
		if (!canBrowseDivisions) 
		{
			divisions = new ArrayList<Division>(1);
			divisions.add(user.getDivision());
		}
		else
		{
			divisions = new ArrayList<Division>(user.getDepartment().getDivisions());
		}
		
		DataItemContainer<Division> container = new DataItemContainer<Division>(
				Division.class, 
				divisions, 
				universitiesManager,
				nameDisplayColumns);
		
		container.sort(sortColumns, sortColumnsOrders);
		
		return container;
	}
	
	/**
	 * Get a container for the departments of the non-null university,
	 * else return an empty container if the university is null 
	 * or the department of the authenticated user if no access is granted.
	 */
	public DataItemContainer<Department> getDepartmentsContainer(University university)
	{
		List<Department> departments;
		
		resolveUniversityAccess();
		
		if (university == null)
		{
			departments = new ArrayList<Department>(0);
		}
		else if (!canBrowseDepartments)
		{
			departments = new ArrayList<Department>(1);
			
			departments.add(session.getAuthenticatedUser().getDepartment());
		}
		else
		{
			departments = new ArrayList<Department>(university.getDepartments());
		}
		
		DataItemContainer<Department> container = new DataItemContainer<Department>(
				Department.class, 
				departments, 
				universitiesManager, 
				nameDisplayColumns);
		
		container.sort(sortColumns, sortColumnsOrders);
		
		return container;
	}
	
	/**
	 * Get a container for the departments of the university of the user if the
	 * operator has access to departments, else return the single department of the user.
	 */
	public DataItemContainer<Department> getDepartmentsContainer(AuthenticatedUser user)
	{
		if (user == null) 
			throw new IllegalArgumentException("Argument 'user' must not be null.");
		
		List<Department> departments;
		
		resolveUniversityAccess();
		
		if (!canBrowseDepartments)
		{
			departments = new ArrayList<Department>(1);
			
			departments.add(user.getDepartment());
		}
		else
		{
			departments = new ArrayList<Department>(user.getDepartment().getUniversity().getDepartments());
		}
		
		DataItemContainer<Department> container = new DataItemContainer<Department>(
				Department.class, 
				departments, 
				universitiesManager, 
				nameDisplayColumns);
		
		container.sort(sortColumns, sortColumnsOrders);
		
		return container;
	}
	
	/**
	 * Get a container for the universities,
	 * else return the university of the authenticated user.
	 */
	public DataItemContainer<University> getUniversitiesContainer()
	{
		List<University> universities;
		
		resolveUniversityAccess();
		
		if (!canBrowseUniversities) 
		{
			universities = new ArrayList<University>(1);
			
			universities.add(session.getAuthenticatedUser().getDepartment().getUniversity());
		}
		else
		{
			UniversitiesSearchCriteria criteria = new UniversitiesSearchCriteria();
			
			SearchResult<University> searchResult = universitiesManager.searchUniversities(criteria);
		
			universities = searchResult.getList();
		}
		
		DataItemContainer<University> container = new DataItemContainer<University>(
				University.class, 
				universities, 
				universitiesManager, 
				nameDisplayColumns);
		
		container.sort(sortColumns, sortColumnsOrders);
		
		return container;
	}
}
