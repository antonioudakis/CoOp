package softeng.coop.ui.forms;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.data.*;

@SuppressWarnings("serial")
public class StudentForm
extends MultilingualValidationForm<Student>
{
	public StudentForm()
	{
		super(Student.class);
	}
}
