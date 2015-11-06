package softeng.coop.ui.presenters;

import java.util.*;

import com.vaadin.data.util.*;
import com.vaadin.ui.Window.*;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.data.DataItemContainer;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.coop.business.TransactionScope;
import softeng.coop.business.students.*;

public class RegistrationPresenter 
	extends Presenter<BeanItem<Student>, ICoopContext, IRegistrationView>
{
	private StudentsWriterManager manager;
	
	public RegistrationPresenter(IRegistrationView view)
	{
		super(view);
		
	}

	@Override
	protected void setupView()
	{
		StudentsManager studentsManager = getContext().getSession().getStudentsManager();
		
		if (studentsManager == null || !studentsManager.isWriteable())
		{
			throw new CoopUIUserException(
					getContext().getLocalizedString("ACCESS_DENIED_DESCRIPTION"));
		}
		
		this.manager = studentsManager.getWriterManager();
		
		BeanItem<Student> studentItem = getView().getModel();
		
		if (studentItem == null) return;
		
		if (getView().getCoopsContainer() == null)
		{
			Student student = studentItem.getBean();
			
			Set<CoOp> coops = this.manager.suggestCoopsForRegistration(student);
			
			DataItemContainer<CoOp> coopsContainer = 
				new DataItemContainer<CoOp>(CoOp.class, coops, this.manager);
			
			getView().setCoopsContainer(coopsContainer);
		}
		
		getView().dataBind();
	}

	private void onRegister()
	{
		BeanItem<Student> studentItem = getView().getModel();
		
		if (studentItem == null) return;
		
		CoOp selectedCoop = getView().getSelectedCoOp();
		
		if (selectedCoop == null)
		{
			onNoRegistration();
			
			return;
		}
		
		TransactionScope transactionScope = this.manager.beginTransaction();
		
		try
		{
			Registration registration = this.manager.register(selectedCoop, studentItem.getBean());
			
			studentItem.getItemProperty("defaultCoOp").setValue(selectedCoop);
			studentItem.getItemProperty("activeRegistration").setValue(registration);
			
			this.manager.persistStudent(studentItem.getBean());
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	private void onNoRegistration()
	{
		String caption = 
			getContext().getLocalizedString("REGISTRATION_CANCELLED_CAPTION");
		
		String description = 
			getContext().getLocalizedString("REGISTRATION_CANCELLED_DESCRIPTION");
		
		this.getContext().showNotification(
				caption, 
				description, 
				Notification.TYPE_WARNING_MESSAGE);
	}

	@Override
	protected void attachToView()
	{
		getView().getOkCancelView().addOkListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				onRegister();
			}
		});
		
		getView().getOkCancelView().addCancelListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				onNoRegistration();
			}
		});

	}
}
