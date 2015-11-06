package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

public interface IStudentCardView extends IFormView<BeanItem<Student>>
{
	IOkCancelView getOkCancelView();
}
