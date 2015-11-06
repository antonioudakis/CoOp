package softeng.coop.ui.dialogs;

import java.util.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;
import com.vaadin.ui.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.composites.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;
import softeng.ui.vaadin.mvp.*;

@SuppressWarnings("serial")
public class BranchDialog
extends CoopDialog<BeanItem<Branch>>
{
	private static final long serialVersionUID = 1L;
	
	private static class BranchForm
	extends MultilingualValidationForm<Branch>
	{
		private static final long serialVersionUID = 1L;

		public BranchForm()
		{
			super(Branch.class);
		}
	}
	
	private BranchForm form;
	
	private TextField nameTextField;
	private TextField telephoneTextField;
	private TextField faxTextField;
	private BranchPersonsTableComponent branchPersonsTableComponent;
	
	private AddressComponent addressComponent;
	
	private static ArrayList<String> branchPropertyIds =
		getBranchPropertyIds();
	
	public BranchDialog()
	{
		setHeight("560px");
		
		VerticalLayout layout = new VerticalLayout();
		
		layout.setSizeFull();
		layout.setSpacing(true);
		
		layout.addComponent(new UserLanguageComboBox());
		
		Panel panel = new Panel();
		panel.setSizeFull();
		
		layout.addComponent(panel);
		layout.setExpandRatio(panel, 1.0f);
		
		form = new BranchForm();
		
		form.setWriteThrough(false);
		form.setImmediate(true);
		form.setWidth("100%");
		
		panel.addComponent(form);
		
		addressComponent = new AddressComponent();
		addressComponent.setWidth("100%");
		panel.addComponent(addressComponent);
		
		form.setFormFieldFactory(new FormFieldFactory()
		{
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext)
			{
				if (propertyId.equals("name"))
				{
					nameTextField = new TextField();
					nameTextField.setCaption(getLocalizedString("NAME_CAPTION"));
					nameTextField.setWidth("100%");
					nameTextField.setNullRepresentation("");
					
					return nameTextField;
				}
				else if (propertyId.equals("telephone"))
				{
					telephoneTextField = new TextField();
					telephoneTextField.setCaption(getLocalizedString("TELEPHONE_CAPTION"));
					telephoneTextField.setWidth("100%");
					telephoneTextField.setNullRepresentation("");
					
					return telephoneTextField;
				}
				else if (propertyId.equals("fax"))
				{
					faxTextField = new TextField();
					faxTextField.setCaption(getLocalizedString("FAX_CAPTION"));
					faxTextField.setWidth("100%");
					faxTextField.setNullRepresentation("");
					
					return faxTextField;
				}
				else if (propertyId.equals("persons") && !isUserStudent())
				{
					branchPersonsTableComponent = new BranchPersonsTableComponent();
					branchPersonsTableComponent.setCaption(getLocalizedString("PERSONS_CAPTION"));
					branchPersonsTableComponent.setWidth("100%");
					branchPersonsTableComponent.setParentModel(getModel().getBean());
					
					if (isUserStudent()) branchPersonsTableComponent.setEditVisible(false);
					
					return branchPersonsTableComponent;
				}

				return null;
			}
		});
		
		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				onAccept(event);
			}
		});
		
		setLayout(layout);
	}

	@Override
	public void dataBind()
	{
		form.setItemDataSource(getModel(), branchPropertyIds);
		
		addressComponent.setModel(
				new BeanItem<EmbeddableAddress>(getModel().getBean().getAddress()));
		
		addressComponent.dataBind();
	}

	private static ArrayList<String> getBranchPropertyIds()
	{
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("name");
		list.add("telephone");
		list.add("fax");
		list.add("persons");
		
		return list;
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		getOkCancelView().setModel(OkCancelViewModel.ApplyOrSelect);
	}

	protected void onAccept(CommandExecutionVote event)
	{
		if (this.isDataValid())
		{
			form.commit();
			addressComponent.commitChangesToModel();
		}
		else
		{
			getContext().showInvalidDataNotification();
			event.markAsFailed();
		}
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		
		form.setCaption(getLocalizedString("FORM_CAPTION"));
		
		if (nameTextField != null)
			nameTextField.setCaption(getLocalizedString("NAME_CAPTION"));
		
		if (telephoneTextField != null)
			telephoneTextField.setCaption(getLocalizedString("TELEPHONE_CAPTION"));
		
		if (faxTextField != null)
			faxTextField.setCaption(getLocalizedString("FAX_CAPTION"));
		
		if (branchPersonsTableComponent != null)
			branchPersonsTableComponent.setCaption(getLocalizedString("PERSONS_CAPTION"));

	}

	@Override
	public boolean isDataValid()
	{
		return form.isValid() && addressComponent.isDataValid();
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		form.setReadOnly(readOnly);
		addressComponent.setReadOnly(readOnly);
	}
	
}
