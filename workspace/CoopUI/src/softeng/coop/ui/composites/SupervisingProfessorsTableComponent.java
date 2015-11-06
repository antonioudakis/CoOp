package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;
import com.vaadin.ui.Window.Notification;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;
import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.ChooseProfessorDialog;

import softeng.ui.vaadin.mvp.*;

public class SupervisingProfessorsTableComponent
extends TableComponent<CoOp, Professor>
{
	private static final long serialVersionUID = 1L;

	public SupervisingProfessorsTableComponent(
			String caption, 
			List<ColumnSpecification> columnSpecifications)
	{
		super(caption, columnSpecifications);
	}

	public SupervisingProfessorsTableComponent(
			List<ColumnSpecification> columnSpecifications)
	{
		super(columnSpecifications);
	}

	public SupervisingProfessorsTableComponent(
			String caption)
	{
		this(caption, getDefaultColumnSpecifications());
	}

	public SupervisingProfessorsTableComponent()
	{
		this(getDefaultColumnSpecifications());
	}
	
	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		ArrayList<ColumnSpecification> columnSpecifications = new ArrayList<ColumnSpecification>();
		
		columnSpecifications.add(new ColumnSpecification("surname", "SURNAME_CAPTION"));
		columnSpecifications.add(new ColumnSpecification("name", "NAME_CAPTION"));
		
		return columnSpecifications;
	}

	@Override
	public Class<?> getType()
	{
		return Professor.class;
	}

	@Override
	protected IModalView<BeanItem<Professor>> showAddForm(BeanItem<Professor> item)
	{
		return showForm(item);
	}

	@Override
	protected IModalView<BeanItem<Professor>> showEditForm(BeanItem<Professor> item)
	{
		return showForm(item);
	}
	
	private IModalView<BeanItem<Professor>> showForm(BeanItem<Professor> item)
	{
		final ChooseProfessorDialog dialog = new ChooseProfessorDialog();
		
		dialog.setModel(item);
		
		dialog.setModal(true);
		
		dialog.getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				if (dialog.getModel() == null)
				{
					getContext().showNotification(
							getLocalizedString("NO_SELECTED_PROFESSOR_CAPTION"), 
							getLocalizedString("NO_SELECTED_PROFESSOR_DESCRIPTION"), 
							Notification.TYPE_WARNING_MESSAGE);
					
					event.markAsFailed();
				}
			}
		});
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();

		return dialog;
	}

	@Override
	protected Professor createNewElement()
	{
		return null;
	}

	@Override
	protected Presenter<Collection<Professor>, ICoopContext, ? extends IView<Collection<Professor>, ICoopContext>> supplyPresenter()
	{
		return null;
	}

	@Override
	protected BeanItemContainer<Professor> createBeanItemContainer(Collection<Professor> collection)
	{
		if (collection != null && getContext() != null && getContext().getSession() != null)
		{
			Session session = getContext().getSession();
			
			return new DataItemContainer<Professor>(
					Professor.class, 
					collection, 
					session.getBaseManager(), 
					getSpecifiedPropertyIds());
		}
		
		return super.createBeanItemContainer(collection);
	}

	@Override
	protected BeanItem<Professor> createItem(Professor obj)
	{
		if (obj == null) return null;
		
		if (getContext() != null && getContext().getSession() != null)
		{
			Session session = getContext().getSession();
			
			return new DataItem<Professor>(obj, session.getBaseManager());
		}
		
		return super.createItem(obj);
	}

	@Override
	protected void addToParent(Professor item)
	{
		CoOp parent = getParentModel();
		
		if (parent == null) return;
		
		Session session = getContext().getSession();
		
		if (session.isLoaded(item, "supervisedCoOps")) 
			item.getSupervisedCoOps().add(parent);
	}
	
	@Override
	protected void removeFromParent(Professor item)
	{
		CoOp parent = getParentModel();
		
		if (parent == null) return;
		
		Session session = getContext().getSession();
		
		if (session.isLoaded(item, "supervisedCoOps")) 
			item.getSupervisedCoOps().remove(parent);
	}
	
}
