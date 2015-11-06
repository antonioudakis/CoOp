package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.BeanItem;

import softeng.coop.dataaccess.*;

public interface IAdditionalStudentDataView extends IFormView<BeanItem<Student>>
{
	public void setViewFullyWritable(Boolean isWritable);
	public Boolean isViewFullyWritable();
}
