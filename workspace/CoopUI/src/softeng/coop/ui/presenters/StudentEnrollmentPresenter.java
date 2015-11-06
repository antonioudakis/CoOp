package softeng.coop.ui.presenters;

import com.vaadin.ui.Window.Notification;

import softeng.coop.business.authentication.IUser;
import softeng.coop.business.authentication.RegistrationResult;
import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.ui.vaadin.mvp.*;

import softeng.coop.business.*;
import softeng.coop.business.authentication.*;

public class StudentEnrollmentPresenter 
	extends Presenter<IUser, ICoopContext, IEnrollmentView>
{

	public StudentEnrollmentPresenter(IEnrollmentView view)
	{
		super(view);
	}

	@Override
	protected void setupView()
	{
		IUser user = null;
		
		IAuthenticationProvider authenticationProvider = 
			AuthenticationFactory.getAuthenticationProvider();
		
		if (authenticationProvider != null)
		{
			user = authenticationProvider.getAthenticatedUser();
		}
		
		this.getView().setModel(user);
		this.getView().dataBind();
		
	}

	private String formatFailureReason(RegistrationResult registrationResult)
	{
		StringBuilder messageBuilder = new StringBuilder();
		
		for (InformationMessage message : registrationResult.getInformationMessages())
		{
			messageBuilder.append("<br />");
			messageBuilder.append(message.getText());
		}
		
		return messageBuilder.toString();
	}

	private void enrollUser()
	{
		SessionFactory sessionFactory = SessionRepository.getSessionFactory();
		
		RegistrationResult registrationResult = 
			sessionFactory.register(getContext().getLocale());
		
		if (registrationResult.getStatus() != RegistrationStatus.RegistrationSucceeded)
		{
			String failureReason = formatFailureReason(registrationResult);
			
			getContext().getMainWindow().showNotification(
					getView().getLocalizedString("REGISTRATION_FAILED_CAPTION"), 
					failureReason, 
					Notification.TYPE_ERROR_MESSAGE);
		}
		else
		{
			getView().fireRegistrationSucceeded();
		}
	}

	@Override
	protected void attachToView()
	{
		this.getView().addAcceptListener(new IViewListener<IUser, ICoopContext, IEnrollmentView>()
		{
			
			@Override
			public void onEvent(ViewEvent<IUser, ICoopContext, IEnrollmentView> event)
			{
				enrollUser();
			}
		});
	}

}
