package softeng.coop.ui.data;

import java.util.*;

import softeng.coop.dataaccess.*;

import com.vaadin.ui.*;

/**
 * Generated column for Group items displaying their members
 * as a comma separated list.
 */
@SuppressWarnings("serial")
public class GroupMembersColumnGenerator 
implements Table.ColumnGenerator
{
	private static class RegistrationsSorter
	implements Comparator<Registration>
	{
		@Override
		public int compare(Registration object1, Registration object2)
		{
			if (object1 == null) 
				throw new IllegalArgumentException("Argument 'object1' must not be null.");
			
			if (object2 == null) 
				throw new IllegalArgumentException("Argument 'object2' must not be null.");
			
			return object1.getStudent().getSurname().compareTo(object2.getStudent().getSurname());
		}
	}
	
	private static RegistrationsSorter registrationsSorter = new RegistrationsSorter();

	@Override
	public Object generateCell(Table source, Object itemId, Object columnId)
	{
		Group group = null;
		
		if (itemId instanceof Group)
			group = (Group)itemId;
		else if (itemId instanceof Job)
			group = ((Job)itemId).getGroup();
		
		if (group == null) return "";
		
		if (group.getRegistrations().size() == 0) return "-";
		
		ArrayList<Registration> registrations = new ArrayList<Registration>(group.getRegistrations());
		
		Collections.sort(registrations, registrationsSorter);
		
		StringBuilder stringBuilder = new StringBuilder();
		
		for (Registration registration : registrations)
		{
			if (stringBuilder.length() > 0)
				stringBuilder.append(", ");
			
			Student student = registration.getStudent();
			
			stringBuilder.append(
					String.format(
							"%s %s [%s]", 
							student.getSurname(), 
							student.getName(), 
							student.getSerialNumber()));
		}
		
		return stringBuilder.toString();
	}

}
