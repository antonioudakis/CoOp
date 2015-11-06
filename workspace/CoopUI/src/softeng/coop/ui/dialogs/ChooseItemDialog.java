package softeng.coop.ui.dialogs;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.ui.*;
import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

@SuppressWarnings("serial")
public class ChooseItemDialog<M>
extends CoopDialog<BeanItem<M>>
implements ICollectionModalView<M>
{
	private TableComponent<Object, M> tableComponent;
	
	private ICollectionModalView.ICollectionProvider<M> collectionProvider;
	
	public ChooseItemDialog(
			final Class<M> type,
			List<TableComponent.ColumnSpecification> columnSpecifications, 
			String caption)
	{
		if (columnSpecifications == null) 
			throw new IllegalArgumentException("Argument 'columnSpecifications' must not be null.");
		
		final ChooseItemDialog<M> thisDialog = this;
		
		this.tableComponent = new TableComponent<Object, M>(columnSpecifications)
		{
			@Override
			public Class<?> getType()
			{
				return type;
			}

			@Override
			protected IModalView<BeanItem<M>> showAddForm(BeanItem<M> item)
			{
				return null;
			}

			@Override
			protected IModalView<BeanItem<M>> showEditForm(BeanItem<M> item)
			{
				return null;
			}

			@Override
			protected M createNewElement()
			{
				return null;
			}

			@Override
			protected Presenter<Collection<M>, ICoopContext, ? extends IView<Collection<M>, ICoopContext>> supplyPresenter()
			{
				return null;
			}

			@Override
			public String getLocalizedString(String key)
			{
				return thisDialog.getLocalizedString(key);
			}
		};

		tableComponent.setReadOnly(true);
		tableComponent.setAddVisible(false);
		tableComponent.setEditVisible(false);
		tableComponent.setDeleteVisible(false);
		tableComponent.setSizeFull();

		this.addComponent(tableComponent);
		
		this.setCaption(caption);
		
		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				setModel(tableComponent.getSelectedItem());
			}
		});
		
	}

	public ChooseItemDialog(
			final Class<M> type,
			List<TableComponent.ColumnSpecification> columnSpecifications)
	{
		this(type, columnSpecifications, null);
	}
	
	@Override
	public void dataBind()
	{
		if (collectionProvider == null) return;
		
		tableComponent.setModel(collectionProvider.getContainer());
		tableComponent.dataBind();
		
		if (getModel() == null)
			tableComponent.setSelectedValue(collectionProvider.getDefault());
		else
			tableComponent.setSelectedValue(getModel().getBean());
	}
	
	@Override
	public void setCollectionProvider(ICollectionModalView.ICollectionProvider<M> collectionProvider)
	{
		if (collectionProvider == null) 
			throw new IllegalArgumentException("Argument 'collectionProvider' must not be null.");
		
		this.collectionProvider = collectionProvider;
	}

	@Override
	public ICollectionModalView.ICollectionProvider<M> getCollectionProvider()
	{
		return collectionProvider;
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		tableComponent.setReadOnly(readOnly);
		tableComponent.setUserSelectable(!readOnly);
	}
}
