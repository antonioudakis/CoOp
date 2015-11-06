package softeng.coop.ui.composites;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.viewdefinitions.*;

import com.vaadin.data.util.*;

public class CompanyPersonPickerField
extends PickerField<CompanyPerson>
{
	private static final long serialVersionUID = 1L;
	
	private Company company;
	
	public CompanyPersonPickerField()
	{
		super("surname");
	}
	
	public CompanyPersonPickerField(String caption)
	{
		this();
		setCaption(caption);
	}

	@Override
	protected IModalView<BeanItem<CompanyPerson>> showBrowseForm(BeanItem<CompanyPerson> item)
	{
		if (!isReadOnly())
		{
			if (company == null)
			{
				throw new CoopUISystemException("'company' field has not been set.");
			}
			
			ChooseCompanyPersonDialog dialog = new ChooseCompanyPersonDialog(company);
			
			dialog.setModal(true);
			
			if (getModel() != null)
				dialog.setModel(new BeanItem<CompanyPerson>(getModel()));
			else
				dialog.setModel(null);
			
			this.getApplication().getMainWindow().addWindow(dialog);
			
			dialog.dataBind();
			
			return dialog;
		}
		else
		{
			if (item == null) return null;
			
			PersonDialog<CompanyPerson> dialog = new PersonDialog<CompanyPerson>();
			
			dialog.setModel(item);
			dialog.setModal(true);
			
			this.getApplication().getMainWindow().addWindow(dialog);
			
			dialog.dataBind();
			
			return dialog;
		}
	}

	public void setCompany(Company company)
	{
		if (company == null) 
			throw new IllegalArgumentException("Argument 'company' must not be null.");
		
		this.company = company;
	}

	public Company getCompany()
	{
		return company;
	}

	@Override
	public Class<?> getType()
	{
		return CompanyPerson.class;
	}

}
