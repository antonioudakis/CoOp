package softeng.coop.ui.presenters;

import softeng.ui.vaadin.mvp.*;

import java.util.*;

import com.vaadin.data.util.*;
import com.vaadin.ui.Window.*;

import softeng.coop.business.*;
import softeng.coop.business.faculties.FacultyUsersManager;
import softeng.coop.business.students.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

public class HeaderPresenter 
	extends Presenter<HeaderViewModel, ICoopContext, IHeaderView>
{
	public HeaderPresenter(IHeaderView view)
	{
		super(view);
	}

	@Override
	protected void setupView()
	{
		List<LanguageInfo> languageInfos = new ArrayList<LanguageInfo>(2);
		
		languageInfos.add(new LanguageInfo(new Locale("el"), "Ελληνικά"));
		languageInfos.add(new LanguageInfo(new Locale("en"), "English"));
		
		LanguageInfo selectedLanguageInfo = null;
		
		Locale currentLocale = getView().getContext().getLocale();
		
		for (LanguageInfo languageInfo : languageInfos)
		{
			if (currentLocale.getLanguage().equals(languageInfo.getLocale().getLanguage()))
			{
				selectedLanguageInfo = languageInfo;
				break;
			}
		}
		
		if (selectedLanguageInfo == null)
		{
			selectedLanguageInfo = languageInfos.get(1); // English.
		}
		
		HeaderViewModel model = new HeaderViewModel();
		
		model.setLanguageInfos(languageInfos);
		
		if (getContext().getSession() != null)
		{
			model.setAuthenticatedUser(
					new DataItem<AuthenticatedUser>(
							getContext().getSession().getAuthenticatedUser(), 
							getManagerForMultilingual() 
					)
			);
		}
		
		getView().setModel(model);
		
		getView().dataBind();
		
		getView().setSelectedLanguageInfo(selectedLanguageInfo);
	}

	private ManagerBase getManagerForMultilingual()
	{
		AuthenticatedUser user = getContext().getSession().getAuthenticatedUser();
		
		if (user instanceof Student)
			return getContext().getSession().getStudentsManager();
		else if (user instanceof FacultyUsersManager)
			return getContext().getSession().getFacultyUsersManager();
		
		return null;
	}

	protected void onLogin()
	{
		AuthenticatedUser user = this.getContext().getSession().getAuthenticatedUser();
		
		getView().getModel().setAuthenticatedUser(
				new BeanItem<AuthenticatedUser>(user)
		);
		
		if (user instanceof Student)
		{
			Student student = (Student)user;
			
			if (isStudentValid(student))
			{
				getView().fireSuggestRegistration();
			}
			else
			{
				getContext().showNotification(
						getLocalizedString("PROMPT_REQUIRED_FIELDS_CAPTION"), 
						getLocalizedString("PROMPT_REQUIRED_FIELDS_DESCRIPTION"), 
						Notification.TYPE_HUMANIZED_MESSAGE,
						1000 * 12);
			}
		}
		
		presentLoggedOnInfo();
	}

	protected void onSelectedCoopChange(CoOp selectedCoOp)
	{
		if (getView().getModel().getAuthenticatedUser() == null) return;
		if (getContext().getSession() == null) return;
		
		Session session = getContext().getSession();
		
		if (selectedCoOp != session.getDefaultCoop())
		{
			AuthenticatedUser user = session.getAuthenticatedUser();
			
			if (user instanceof Student)
			{
				Student student = (Student)user;
				
				Registration registration = null;
				
				StudentsManager studentsManager = session.getStudentsManager();
				
				// First try efficiently to get the registration for the coop.
				if (studentsManager != null)
				{
					registration = studentsManager.getRegistration(student.getSerialNumber(), selectedCoOp);
				}
				else
				{
					for (Registration candidateRegistration : student.getRegistrations())
					{
						if (candidateRegistration.getCoop() == selectedCoOp)
						{
							registration = candidateRegistration;
							break;
						}
					}
				}

				student.setActiveRegistration(registration);
			}
			
			session.setDefaultCoop(selectedCoOp);
		}
	}

	protected void presentLoggedOnInfo()
	{
		getView().dataBind();

		Locale currentLocale = getContext().getLocale();
		
		for (LanguageInfo languageInfo : getView().getModel().getLanguageInfos())
		{
			if (currentLocale.getLanguage().equals(languageInfo.getLocale().getLanguage()))
			{
				getView().setSelectedLanguageInfo(languageInfo);
				break;
			}
		}
		
	}

	@Override
	protected void attachToView()
	{
		getContext().addLoginListener(new IListener<AuthenticatedUser>()
		{
			@Override
			public void onEvent(AuthenticatedUser event)
			{
				onLogin();
			}
		});
		
		getView().addSelectedCoopChangedListener(new IViewListener<HeaderViewModel, ICoopContext, IHeaderView>()
		{
			@Override
			public void onEvent(ViewEvent<HeaderViewModel, ICoopContext, IHeaderView> event)
			{
				onSelectedCoopChange(getView().getSelectedCoop());
			}
		});
		
		
		getView().addLanguageChangedListener(new IViewListener<HeaderViewModel, ICoopContext, IHeaderView>()
		{
			@Override
			public void onEvent(ViewEvent<HeaderViewModel, ICoopContext, IHeaderView> event)
			{
				if (getView().getSelectedLanguageInfo() == null) return;
				Locale selectedLocale = getView().getSelectedLanguageInfo().getLocale();
				getContext().setLocale(selectedLocale);
			}
		});
		
		getView().addSuggestRegistrationListener(new IViewListener<HeaderViewModel, ICoopContext, IHeaderView>()
		{
			@Override
			public void onEvent(ViewEvent<HeaderViewModel, ICoopContext, IHeaderView> event)
			{
				onSuggestRegistration(event);
			}
		});
		
		getView().addRegisterListener(new IViewListener<HeaderViewModel, ICoopContext, IHeaderView>()
		{
			@Override
			public void onEvent(ViewEvent<HeaderViewModel, ICoopContext, IHeaderView> event)
			{
				onRegister(event);
			}
		});
	}

	@SuppressWarnings("unchecked")
	protected void onRegister(ViewEvent<HeaderViewModel, ICoopContext, IHeaderView> event)
	{
		HeaderViewModel model = getView().getModel();
		
		if (model == null) return;
		if (model.getAuthenticatedUser() == null) return;
		if (!(model.getAuthenticatedUser().getBean() instanceof Student)) return;
		
		BeanItem<Student> studentItem = (BeanItem<Student>)model.getAuthenticatedUser();
		
		if (!isStudentValid(studentItem.getBean()))
		{
			getContext().showNotification(
					getLocalizedString("INVALID_STUDENT_DATA_CAPTION"), 
					getLocalizedString("INVALID_STUDENT_DATA_DESCRIPTION"), 
					Notification.TYPE_WARNING_MESSAGE,
					1000 * 12);
			
			return;
		}
		
		final RegistrationDialog dialog = new RegistrationDialog();
		
		dialog.setModel(studentItem);
		dialog.setModal(true);
		
		this.getContext().getMainWindow().addWindow(dialog);

	}

	@SuppressWarnings("unchecked")
	protected void onSuggestRegistration(ViewEvent<HeaderViewModel, ICoopContext, IHeaderView> event)
	{
		if (event.getView().getModel() == null) return;
		
		BeanItem<? extends AuthenticatedUser> userItem = 
			event.getView().getModel().getAuthenticatedUser();
		
		if (userItem == null) return;
		
		if (userItem.getBean() instanceof Student)
		{
			Student student = (Student)userItem.getBean();
			
			if (!isStudentValid(student)) return;
			
			StudentsManager manager = getContext().getSession().getStudentsManager();
			
			if (manager != null && manager.isWriteable())
			{
				StudentsWriterManager writerManager = manager.getWriterManager();
				
				Set<CoOp> suggestedCoopsForRegistration = 
					writerManager.suggestCoopsForRegistration(student);
				
				if (suggestedCoopsForRegistration.size() > 0)
				{
					RegistrationDialog registrationDialog = 
						new RegistrationDialog();
					
					registrationDialog.setModal(true);
					
					registrationDialog.setModel((BeanItem<Student>)userItem);
					
					DataItemContainer<CoOp> coopsContainer = 
						new DataItemContainer<CoOp>(CoOp.class, suggestedCoopsForRegistration, manager);
					
					registrationDialog.setCoopsContainer(coopsContainer);
					
					getContext().getMainWindow().addWindow(registrationDialog);
				}
			}
		}
		
	}

	private boolean isStudentValid(Student student)
	{
		return DataUtilities.studentHasCompleteProvision(student);
	}

}
