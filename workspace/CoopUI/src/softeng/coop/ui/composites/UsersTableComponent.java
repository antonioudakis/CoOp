package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

@SuppressWarnings("serial")
public class UsersTableComponent
	extends TableComponent<Department, AuthenticatedUser>
	implements IUserTableView
{
	private UserType userClassType;
	
	public UsersTableComponent(
			List<ColumnSpecification> columnSpecifications) 
	{
		super(columnSpecifications);
	}
	
	public UsersTableComponent()
	{
		super(getDefaultColumnSpecifications());
	}
	
	private static List<ColumnSpecification> 
		getDefaultColumnSpecifications() 
	{
		ArrayList<ColumnSpecification> specifications = new ArrayList<ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("surname", "SURNAME_CAPTION"));
		specifications.add(new ColumnSpecification("name", "NAME_CAPTION"));
		
		return specifications;
	}

	@Override
	public Class<?> getType() 
	{
		return AuthenticatedUser.class;
	}

	@Override
	protected IModalView<BeanItem<AuthenticatedUser>> showAddForm(
			BeanItem<AuthenticatedUser> item) 
	{
		return null;
	}

	@Override
	protected IModalView<BeanItem<AuthenticatedUser>> showEditForm(
			BeanItem<AuthenticatedUser> item) 
	{
		return null;
	}

	@Override
	protected AuthenticatedUser createNewElement() 
	{
		Language preferredLanguage = getContext().getSession().getAuthenticatedUser().getPreferredLanguage();
		
		if (this.userClassType == UserType.Student)
		{
			Student student = Constructor.createStudent();
								
			student.setDepartment(getContext().getSession().getAuthenticatedUser().getDepartment());
			
			student.setPreferredLanguage(preferredLanguage);
			
			student.setName("-");
			student.setSurname("-");
			
			return student;
		}
		else if (this.userClassType == UserType.Professor)
		{
			Professor professor = Constructor.createProfessor();
			
			professor.setDepartment(getContext().getSession().getAuthenticatedUser().getDepartment());
			
			professor.setPreferredLanguage(preferredLanguage);
			
			professor.setName("-");
			professor.setSurname("-");
			
			return professor;
		}
		else if (this.userClassType == UserType.Faculty)
		{
			FacultyUser facultyUser = Constructor.createFacultyUser();
			
			facultyUser.setDepartment(getContext().getSession().getAuthenticatedUser().getDepartment());
			
			facultyUser.setPreferredLanguage(preferredLanguage);
			
			facultyUser.setName("-");
			facultyUser.setSurname("-");
			
			return facultyUser;
		}
		
		return null;
	}

	@Override
	protected Presenter<Collection<AuthenticatedUser>, ICoopContext, ? extends IView<Collection<AuthenticatedUser>, ICoopContext>> 
		supplyPresenter() 
	{
		return new UsersTablePresenter(this);
	}

	@Override
	public void setUserType(UserType classType) 
	{
		this.userClassType = classType;
		
	}

	@Override
	public UserType getUserType() 
	{
		return userClassType;
	}

	@Override
	protected void addToParent(AuthenticatedUser item) 
	{
		item.setDepartment(getParentModel());
	}

	@Override
	protected void removeFromParent(AuthenticatedUser item) 
	{
		item.setDepartment(null);
	}

}
