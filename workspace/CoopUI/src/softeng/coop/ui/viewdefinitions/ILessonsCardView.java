package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

public interface ILessonsCardView extends IFormView<BeanItem<Department>>
{
	IOkCancelView getOkCancelView();
	
	Lesson getSelectedLesson();
}
