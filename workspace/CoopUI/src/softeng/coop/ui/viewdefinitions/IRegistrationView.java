package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.data.*;

public interface IRegistrationView extends IModalView<BeanItem<Student>>
{
	DataItemContainer<CoOp> getCoopsContainer();
	void setCoopsContainer(DataItemContainer<CoOp> container);
	
	CoOp getSelectedCoOp();
}
