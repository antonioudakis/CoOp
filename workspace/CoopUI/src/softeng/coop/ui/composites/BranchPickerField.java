package softeng.coop.ui.composites;

import com.vaadin.data.util.BeanItem;

import softeng.coop.dataaccess.Branch;
import softeng.coop.dataaccess.Company;
import softeng.coop.ui.data.DataItem;
import softeng.coop.ui.dialogs.BranchDialog;
import softeng.coop.ui.dialogs.ChooseBranchDialog;
import softeng.coop.ui.viewdefinitions.IBranchPickerView;
import softeng.coop.ui.viewdefinitions.IModalView;

public class BranchPickerField 
	extends PickerField<Branch> 
	implements IBranchPickerView
{
	private static final long serialVersionUID = 1L;

	private Company company;
	
	boolean companyBrowsingAllowed;
	
	public BranchPickerField()
	{
		super("name");
	}
	
	public BranchPickerField(String caption)
	{
		this();
		setCaption(caption);
	}

	@Override
	public void setCompany(Company company) 
	{
		this.company = company;
	}

	@Override
	public Company getCompany() 
	{
		return this.company;
	}

	@Override
	protected IModalView<BeanItem<Branch>> 
		showBrowseForm(BeanItem<Branch> item) 
	{
		if (!isReadOnly())
		{
			if (this.company == null)
				return null;
			
			ChooseBranchDialog dialog = null;
			
			if (companyBrowsingAllowed)
				dialog = new ChooseBranchDialog(company, getContext().getSelectedCoop());
			else
				dialog = new ChooseBranchDialog(company);
			
			dialog.setModal(true);
			
			if (getModel() != null)
				dialog.setModel(new BeanItem<Branch>(getModel()));
			else 
				dialog.setModel(null);
			
			this.getApplication().getMainWindow().addWindow(dialog);
			
			dialog.dataBind();
			
			return dialog;
		}
		else
		{
			if (item == null) return null;
			
			BranchDialog dialog = new BranchDialog();
			
			dialog.setModel(item);
			dialog.setModal(true);
			
			this.getApplication().getMainWindow().addWindow(dialog);
			
			dialog.dataBind();
			
			return dialog;
		}
	}

	@Override
	public Class<?> getType() 
	{
		return Branch.class;
	}

	@Override
	protected BeanItem<Branch> createBeanItem(Branch value) 
	{
		if (value == null)
			return null;
		else
			return new DataItem<Branch>(value, this.getContext().getSession().getBaseManager());
	}

	@Override
	public void setCompanyBrowsingAllowed(boolean companyBrowsingAllowed)
	{
		this.companyBrowsingAllowed = companyBrowsingAllowed;
	}

	@Override
	public boolean isCompanyBrowsingAllowed()
	{
		return this.companyBrowsingAllowed;
	}

}
