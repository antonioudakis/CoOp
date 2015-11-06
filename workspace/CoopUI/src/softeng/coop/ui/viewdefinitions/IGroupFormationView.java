package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.BeanItem;

import softeng.coop.dataaccess.CoOp;
import softeng.coop.dataaccess.Group;

public interface IGroupFormationView extends IFormView<BeanItem<Group>> 
{
	public void setCoopModel(BeanItem<CoOp> coop);
}
