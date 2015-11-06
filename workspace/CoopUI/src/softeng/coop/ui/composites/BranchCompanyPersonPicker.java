package softeng.coop.ui.composites;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Window;

import softeng.coop.dataaccess.Branch;
import softeng.coop.dataaccess.CompanyPerson;
import softeng.coop.ui.dialogs.ChooseBranchPersonDialog;
import softeng.coop.ui.viewdefinitions.IBranchCompanyPersonPickerView;
import softeng.coop.ui.viewdefinitions.IModalView;

public class BranchCompanyPersonPicker
	extends PickerField<CompanyPerson>
	implements IBranchCompanyPersonPickerView
{
	private static final long serialVersionUID = 1L;

	private Branch branch;
	
	public BranchCompanyPersonPicker()
	{
		super("surname");
	}
	
	public BranchCompanyPersonPicker(String caption)
	{
		this();
		this.setCaption(caption);
	}
	
	@Override
	public void setBranch(Branch branch) 
	{
		this.branch = branch;
		
	}

	@Override
	public Branch getBranch() 
	{
		return this.branch;
	}

	@Override
	protected IModalView<BeanItem<CompanyPerson>> showBrowseForm(
			BeanItem<CompanyPerson> item) 
	{
		if (this.branch == null)
		{
			getContext().showNotification(
					getLocalizedString("NO_BRANCH_SELECTED_CAPTION"), 
					getLocalizedString("NO_BRANCH_SELECTED_DESCRIPTION"), 
					Window.Notification.TYPE_ERROR_MESSAGE);
			
			return null;
		}
		
		ChooseBranchPersonDialog dialog = new ChooseBranchPersonDialog(this.branch);
		
		dialog.setModal(true);
		
		if (this.getModel() != null)
			dialog.setModel(new BeanItem<CompanyPerson>(this.getModel()));
		else 
			dialog.setModel(null);
		
		this.getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	public Class<?> getType() 
	{
		return CompanyPerson.class;
	}

}
