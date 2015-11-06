package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.BeanItem;

import softeng.coop.dataaccess.*;

public interface IPersonDataView extends IFormView<BeanItem<? extends Person>>
{
	public void setViewFullyWritable(boolean isWritable);
	public boolean isViewFullyWritable();
}
