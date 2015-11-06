package softeng.coop.ui.presenters;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;

import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.util.*;

/**
 * A generic presenter that supports views of type
 * ICardFormView[BeanItem[Registration]]. Handles current coop and registration
 * change (because it inherits from StudentRegistrationPresenter), but it also
 * handles the Save and Cancel commands of the view. 
 * @param <V> The type of the view. Must be an ICardFormView[BeanItem[Registration]].
 */
public class StudentRegistrationCardPresenter<V extends ICardFormView<BeanItem<Registration>>>
extends StudentRegistrationPresenter<V>
{

	public StudentRegistrationCardPresenter(V view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		super.attachToView();
		
		getView().getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				save(event);
			}
		});
		
		getView().getOkCancelView().addCancelListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				cancel();
			}
		});
		
	}

	private void cancel()
	{
		getView().discardChanges();
		
		if (getContext().getSession() != null)
		{
			getContext().getSession().refreshEntity(getView().getModel().getBean());
			
			getView().dataBind();
		}
	}

	private void save(CommandExecutionVote vote)
	{
		if (manager == null) return;
		
		if (!getView().isDataValid())
		{
			vote.markAsFailed();
			
			getView().getContext().showInvalidDataNotification();

			return;
		}

		getView().commitChangesToModel();
		
		BeanItem<Registration> registrationItem =
			getView().getModel();
		
		if (registrationItem != null)
		{
			persistModel(registrationItem.getBean());
			
			getView().getContext().showDataSavedNotification();
		}
	}
	
}
