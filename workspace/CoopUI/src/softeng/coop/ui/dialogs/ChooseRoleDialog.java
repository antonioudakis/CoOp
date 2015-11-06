package softeng.coop.ui.dialogs;

import java.util.Locale;
import java.util.Set;

import com.vaadin.data.util.BeanItem;

import softeng.coop.dataaccess.Role;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.composites.UserRoleTableComponent;
import softeng.coop.ui.viewdefinitions.IOkCancelView;
import softeng.coop.ui.viewdefinitions.viewmodels.OkCancelViewModel;
import softeng.ui.vaadin.mvp.IViewListener;
import softeng.ui.vaadin.mvp.ViewEvent;

@SuppressWarnings("serial")
public class ChooseRoleDialog 
	extends CoopDialog<BeanItem<Role>> 
{

	UserRoleTableComponent chooseRoleComponent;

	public ChooseRoleDialog() 
	{
		this.setHeight("330px");
		
		this.getOkCancelView().addOkListener(
				new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>() 
		{
			
			@Override
			public void onEvent(
					ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event) 
			{
				setModel(chooseRoleComponent.getSelectedItem());
			}
		});
	}

	@Override
	public void dataBind() 
	{
		//Fill chooseRoleComponent with all available roles
		Set<Role> roles = this.getContext().getSession().getRoles();
		
		chooseRoleComponent.setModel(roles);
		chooseRoleComponent.setParentModel(null);
		
		chooseRoleComponent.dataBind();	
	}

	@Override
	protected void setupUI() 
	{
		super.setupUI();
		
		chooseRoleComponent = new UserRoleTableComponent();
		
		chooseRoleComponent.setAddVisible(false);
		chooseRoleComponent.setDeleteVisible(false);
		chooseRoleComponent.setEditVisible(false);
		chooseRoleComponent.setSizeFull();
		
		this.addComponent(chooseRoleComponent);
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		chooseRoleComponent.setCaption(getLocalizedString("TABLE_CAPTION"));
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		chooseRoleComponent.setReadOnly(readOnly);
	}
}
