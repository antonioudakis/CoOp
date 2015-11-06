package softeng.coop.ui.composites;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;

public class RequirementsCheckListField
extends CheckListField<Requirement>
{
	private Registration registration;
	
	public RequirementsCheckListField()
	{
		super(getDefaultColumnSpecifications());
	}

	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		ArrayList<ColumnSpecification> specifications = 
			new ArrayList<CheckListField.ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("name", "NAME_CAPTION"));
		specifications.add(new ColumnSpecification("type", "TYPE_CAPTION", RequirementType.class));
		
		return specifications;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Class<?> getType()
	{
		return Requirement.class;
	}

	@Override
	protected void addToParent(Requirement element)
	{
		if (registration == null) return;
		
		Session session = getContext().getSession();
		
		if (!session.isLoaded(element, "fullfillingRegistrations")) return;
		
		element.getFullfillingRegistrations().add(registration);
	}

	@Override
	protected void removeFromParent(Requirement element)
	{
		if (registration == null) return;
		
		Session session = getContext().getSession();
		
		if (!session.isLoaded(element, "fullfillingRegistrations")) return;

		element.getFullfillingRegistrations().remove(registration);
	}

	public Registration getRegistration()
	{
		return registration;
	}

	public void setRegistration(Registration registration)
	{
		this.registration = registration;
	}

}
