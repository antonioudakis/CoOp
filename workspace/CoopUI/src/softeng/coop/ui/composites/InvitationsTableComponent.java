package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.InvitationType;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.data.*;

import softeng.ui.vaadin.mvp.*;

@SuppressWarnings("serial")
public class InvitationsTableComponent
extends TableComponent<Registration, Invitation>
implements IInvitationsTableView
{
	private InvitationType contentType;
	
	public InvitationsTableComponent()
	{
		super(getColumnSpecifications());
		
		setDeleteVisible(false);
		setEditVisible(false);
		
		contentType = InvitationType.SentInvitations;
	}

	private static List<ColumnSpecification> getColumnSpecifications()
	{
		List<ColumnSpecification> specifications = new ArrayList<ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("date", "DATE_CAPTION"));
		specifications.add(new ColumnSpecification("text", "TEXT_CAPTION"));

		return specifications;
	}

	@Override
	public Class<?> getType()
	{
		return Registration.class;
	}

	@Override
	protected IModalView<BeanItem<Invitation>> showAddForm(BeanItem<Invitation> item)
	{
		return showForm(item);
	}

	@Override
	protected IModalView<BeanItem<Invitation>> showEditForm(BeanItem<Invitation> item)
	{
		return showForm(item);
	}
	
	private IModalView<BeanItem<Invitation>> showForm(BeanItem<Invitation> item)
	{
		InvitationDialog dialog = new InvitationDialog();
		
		dialog.setModal(true);
		dialog.setModel(item);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected Invitation createNewElement()
	{
		if (getParentModel() == null) return null;
		
		Invitation invitation = Constructor.createInvitation();
		
		invitation.setGroup(getParentModel().getGroup());
		invitation.setSender(getParentModel());
		
		return invitation;
	}
	
	private ManagerBase getManager()
	{
		Session session = getContext().getSession();
		
		ManagerBase manager = session.getStudentsManager();
		
		if (manager == null)
		{
			manager = session.getBaseManager();
		}
		
		return manager;
	}

	@Override
	protected BeanItemContainer<Invitation> createBeanItemContainer(Collection<Invitation> collection)
	{
		DataItemContainer<Invitation> container = 
			new DataItemContainer<Invitation>(
					Invitation.class, 
					collection, 
					getManager());
		
		container.addNullableNestedContainerProperty("sender.student.surname");
		container.addNullableNestedContainerProperty("sender.student.name");
		container.addNullableNestedContainerProperty("sender.student.email");
		container.addNullableNestedContainerProperty("group.registrations");
		
		container.setContainerPropertyIds(getSpecifiedPropertyIds());
		
		return container;
	}

	@Override
	protected BeanItem<Invitation> createItem(Invitation obj)
	{
		if (obj == null) return null;
		
		DataItem<Invitation> item = 
			new DataItem<Invitation>(
					obj, 
					getManager());
		
		item.addNullableNestedProperty("sender.student.surname");
		item.addNullableNestedProperty("sender.student.name");
		item.addNullableNestedProperty("sender.student.email");
		item.addNullableNestedProperty("group.registrations");

		return item;
	}

	@Override
	protected String getResourceBaseName()
	{
		return InvitationsTableComponent.class.getCanonicalName();
	}

	@Override
	public InvitationType getInvitationType()
	{
		return contentType;
	}

	@Override
	public void setInvitationType(InvitationType contentType)
	{
		this.contentType = contentType;
		
		setAddVisible(contentType == InvitationType.SentInvitations);
	}

	@Override
	protected Presenter<Collection<Invitation>, ICoopContext, ? extends IView<Collection<Invitation>, ICoopContext>> supplyPresenter()
	{
		return new InvitationsTablePresenter(this);
	}

	@Override
	protected void addToParent(Invitation item)
	{
		Registration parent = getParentModel();
		
		switch (getInvitationType())
		{
			case SentInvitations:
				item.setSender(parent);
				break;
				
			case ReceivedInvitations:
				item.getRecepients().add(parent);
				break;
		}
	}

	@Override
	protected void removeFromParent(Invitation item)
	{
		Registration parent = getParentModel();
		
		switch (getInvitationType())
		{
			case SentInvitations:
				item.setSender(null);
				break;
				
			case ReceivedInvitations:
				item.getRecepients().remove(parent);
				break;
		}
	}

}
