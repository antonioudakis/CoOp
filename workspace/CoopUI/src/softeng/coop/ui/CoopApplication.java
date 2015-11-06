package softeng.coop.ui;

import java.util.*;
import java.util.logging.*;

import com.vaadin.*;
import com.vaadin.terminal.*;
import com.vaadin.ui.*;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.Window.*;
import softeng.coop.business.*;
import softeng.coop.dataaccess.*;
import softeng.coop.ui.composites.*;
import softeng.coop.ui.data.DataUtilities;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;
import softeng.coop.ui.windows.*;
import softeng.ui.vaadin.mvp.*;

public class CoopApplication 
	extends Application
	implements ICoopContext
{
	private static final long serialVersionUID = 1L;
	
	private Session session;
	
	private ResourceBundle resources;
	
	private HeaderComponent headerComponent;
	
	private TabComponent tabComponent;
	
	private EventSubscription<AuthenticatedUser, IListener<AuthenticatedUser>> loginEventSubscription = 
		new EventSubscription<AuthenticatedUser, IListener<AuthenticatedUser>>();
	
	private EventSubscription<CoOp, IListener<CoOp>> selectedCoopChangedSubscription =
		new EventSubscription<CoOp, IListener<CoOp>>();
	
	{
		staticInitialize();
	}
	
	private static Logger logger = createLogger(); 
	
	@SuppressWarnings("serial")
	@Override
	public void init() 
	{
		setTheme("coopTheme");
		
		final Window mainWindow = new Window(resources.getString("WINDOW_TITLE"));
		
		VerticalLayout mainLayout = new VerticalLayout();
		
		mainLayout.setWidth("100%");
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		
		mainWindow.setContent(mainLayout);
		
		setMainWindow(mainWindow);
		
		buildMainWindowHeader(mainWindow);
		
		SessionFactory sessionFactory = SessionRepository.getSessionFactory();
		
		if (!sessionFactory.isUserAuthenticated())
		{
			mainWindow.showNotification(
					resources.getString("USER_NOT_AUTHENTICATED_CAPTION"), 
					"<br />" + resources.getString("USER_NOT_AUTHENTICATED_DESCRIPTION"), 
					Notification.TYPE_ERROR_MESSAGE);
			
			return;
		}
		else
		{
			if (!sessionFactory.isUserRegistered())
			{
				final Window enrollmentWindow = new EnrollmentWindow();
				
				mainWindow.addWindow(enrollmentWindow);
				
				enrollmentWindow.center();
				
				enrollmentWindow.addListener(new Window.CloseListener()
				{
					@Override
					public void windowClose(CloseEvent e)
					{
						buildMainUI();
					}
				});
				
				return;
			}
		}

		buildMainUI();
	}
	
	private static Logger createLogger()
	{
		String name = CoopApplication.class.getName();
		
		logger = Logger.getLogger(name);
		
		try
		{
			ResourceBundle logSettingsBundle = ResourceBundle.getBundle("Logging");
			
			String pattern = logSettingsBundle.getString("PATTERN");
			int maxFileSize = Integer.parseInt(logSettingsBundle.getString("FILE_MAX_SIZE"));
			int fileCycleCount = Integer.parseInt(logSettingsBundle.getString("FILE_CYCLE_COUNT"));
			
			// Set maximum file size 16MB, cycle through 16 files, appending.
			// The files are to be found in system TEMP folder.
			FileHandler handler = new FileHandler(pattern, maxFileSize, fileCycleCount);
			
			handler.setEncoding("utf-8");
			
			logger.addHandler(handler);

			String levelLiteral = logSettingsBundle.getString("LEVEL");
			
			Level level = Level.parse(levelLiteral);
			
			logger.setLevel(level);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return logger;
	}

	private static int staticInitialize()
	{
		Locale.setDefault(Locale.ROOT);
		
		return 1;
	}

	protected void buildMainUI()
	{
		Window mainWindow = getMainWindow();
		
		SessionFactory sessionFactory = SessionRepository.getSessionFactory();
		
		this.session = sessionFactory.login();
		
		if (this.session == null)
		{
			mainWindow.showNotification(
					resources.getString("USER_NOT_AUTHENTICATED_CAPTION"), 
					"<br />" + resources.getString("USER_NOT_AUTHENTICATED_DESCRIPTION"), 
					Notification.TYPE_ERROR_MESSAGE);
			
			return;
		}
		
		this.setLocale(this.session.getUserLocale());
		
		this.loginEventSubscription.fire(this.session.getAuthenticatedUser());
		
		tabComponent = new TabComponent();
		
		mainWindow.addComponent(tabComponent);
	}

	private void buildMainWindowHeader(final Window mainWindow)
	{
		this.headerComponent = new HeaderComponent();
		mainWindow.addComponent(this.headerComponent);
		
		this.headerComponent.addSelectedCoopChangedListener(new IViewListener<HeaderViewModel, ICoopContext, IHeaderView>()
		{
			@Override
			public void onEvent(ViewEvent<HeaderViewModel, ICoopContext, IHeaderView> event)
			{
				fireSelectedCoopChanged();
			}
		});
	}

	@Override
	public Session getSession()
	{
		return this.session;
	}

	@Override
	public void terminalError(Terminal.ErrorEvent event)
	{
		boolean messageDelivered = false;
		
		for (Throwable exception = event.getThrowable(); exception != null; exception = exception.getCause())
		{
			if (exception instanceof CoopUIAccessDeniedException)
			{
				showAccessDeniedNotification();
				messageDelivered = true;
				
				break;
			}
			else if (exception instanceof CoopUIUserException)
			{
				CoopUIUserException userException = 
					(CoopUIUserException)exception;
				
				int notificationType;
				
				switch (userException.getSeverity())
				{
					case Error:
						notificationType = Window.Notification.TYPE_ERROR_MESSAGE;
						break;
						
					case Warning:
						notificationType = Window.Notification.TYPE_WARNING_MESSAGE;
						break;
						
					case Information:
						notificationType = Window.Notification.TYPE_HUMANIZED_MESSAGE;
						break;
						
					default:
						notificationType = Window.Notification.TYPE_TRAY_NOTIFICATION;
						break;
				}
				
				showNotification(
						getLocalizedString("USER_ERROR_CAPTION"), 
						userException.getLocalizedMessage(), 
						notificationType);
				
				messageDelivered = true;

				break;
			}
			else if (exception instanceof AccessDeniedException)
			{
				showNotification(
						getLocalizedString("ACCESS_DENIED_CAPTION"), 
						getLocalizedString("ACCESS_DENIED_DESCRIPTION"), 
						Notification.TYPE_ERROR_MESSAGE);
				
				log(Level.WARNING, exception);
				
				messageDelivered = true;

				break;
			}
			else if (exception instanceof UniqueViolationException)
			{
				showNotification(
						getLocalizedString("UNIQUE_VIOLATION_CAPTION"), 
						getLocalizedString("UNIQUE_VIOLATION_DESCRIPTION")
						+ getDatabaseExceptionDescription((UniqueViolationException)exception), 
						Window.Notification.TYPE_ERROR_MESSAGE);
				
				log(Level.INFO, exception);
				
				messageDelivered = true;

				break;
			}
			else if (exception instanceof RelationshipViolationException)
			{
				showNotification(
						getLocalizedString("RELATIONSHIP_VIOLATION_CAPTION"), 
						getLocalizedString("RELATIONSHIP_VIOLATION_DESCRIPTION") 
						+ getDatabaseExceptionDescription((RelationshipViolationException)exception), 
						Notification.TYPE_ERROR_MESSAGE);
				
				log(Level.INFO, exception);
				
				messageDelivered = true;

				break;
			}
			else if (exception instanceof NotNullViolationException)
			{
				showNotification(
						getLocalizedString("NOT_NULL_VIOLATION_CAPTION"), 
						getLocalizedString("NOT_NULL_VIOLATION_DESCRIPTION")
						+ getDatabaseExceptionDescription((NotNullViolationException)exception), 
						Notification.TYPE_ERROR_MESSAGE);
				
				log(Level.INFO, exception);
				
				messageDelivered = true;

				break;
			}
			else if (exception instanceof IntegrityConstraintViolationException)
			{
				showNotification(
						getLocalizedString("INTEGRITY_VIOLATION_CAPTION"), 
						getLocalizedString("INTEGRITY_VIOLATION_DESCRIPTION")
						+ getDatabaseExceptionDescription((IntegrityConstraintViolationException)exception), 
						Notification.TYPE_ERROR_MESSAGE);
				
				log(Level.INFO, exception);
				
				messageDelivered = true;

				break;
			}
			else if (exception instanceof javax.validation.ValidationException)
			{
				showNotification(
						getLocalizedString("VALIDATION_ERROR_CAPTION"), 
						getLocalizedString("VALIDATION_ERROR_DESCRIPTION"), 
						Notification.TYPE_ERROR_MESSAGE);
				
				log(Level.INFO, exception);
				
				messageDelivered = true;

				break;
			}
			else if (exception instanceof BusinessRuleViolationException)
			{
				showNotification(
						getLocalizedString("BUSINESS_RULE_VIOLATION_CAPTION"), 
						exception.getLocalizedMessage(), 
						Notification.TYPE_ERROR_MESSAGE);
				
				log(Level.INFO, exception);
				
				messageDelivered = true;

				break;
			}
			else if (exception instanceof OptimisticLockViolationException)
			{
				showNotification(
						getLocalizedString("OPTIMISTIC_LOCK_EXCEPTION_CAPTION"), 
						getLocalizedString("OPTIMISTIC_LOCK_EXCEPTION_DESCRIPTION"), 
						Notification.TYPE_ERROR_MESSAGE);
				
				log(Level.SEVERE, exception);
				
				messageDelivered = true;

				break;
			}
			else if (exception instanceof CoOpException)
			{
				showNotification(
						getLocalizedString("GENERAL_ERROR_CAPTION"), 
						getLocalizedString("GENERAL_ERROR_DESCRIPTION"), 
						Notification.TYPE_ERROR_MESSAGE);
				
				log(Level.SEVERE, exception);
				
				messageDelivered = true;

				break;
			}
		}
		
		if (!messageDelivered)
		{
			showNotification(
					getLocalizedString("GENERAL_ERROR_CAPTION"), 
					getLocalizedString("GENERAL_ERROR_DESCRIPTION"), 
					Notification.TYPE_ERROR_MESSAGE);
			
			log(Level.SEVERE, event.getThrowable());
		}

		//super.terminalError(event);
		
		event.getThrowable().printStackTrace();
		
	}
	
	private void log(Level level, Throwable throwable)
	{
		String userDescription = "*unknown user*";
		
		if (session != null)
		{
			AuthenticatedUser user = session.getAuthenticatedUser();
			
			userDescription = String.format("%s - %s %s", user.getUserName(), user.getSurname(), user.getName());
		}
		
		String exceptionMessage = throwable.getMessage();
		
		String logMessage;
		
		if (exceptionMessage != null)
		{
			logMessage = 
				String.format("User = [%s] - Message = [%s]", userDescription, throwable.getMessage());
		}
		else
		{
			logMessage = 
				String.format("User = [%s]", userDescription, throwable.getMessage());
		}
		
		LogRecord record = new LogRecord(level, logMessage);
		
		record.setThrown(throwable);
		
		logger.log(record);
	}

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);
		
		this.resources = ResourceBundle.getBundle("softeng.coop.ui.ApplicationResources", locale);
		
		this.setPreferredLanguageToCurrentLocale();
		
		Window mainWindow = getMainWindow();
		
		if (mainWindow != null)
		{
			mainWindow.setCaption(resources.getString("WINDOW_TITLE"));
			
			mainWindow.requestRepaintAll();
			
			for (Window childWindow  : mainWindow.getChildWindows())
			{
				childWindow.requestRepaintAll();
			}
		}
	}
	
	private void setPreferredLanguageToCurrentLocale()
	{
		if (this.session == null) return;
		
		if (!this.session.getUserLocale().equals(this.getLocale()))
			this.session.setUserLocale(getLocale());
	}

	@Override
	public String getLocalizedString(String key)
	{
		return resources.getString(key);
	}

	@Override
	public void addLoginListener(IListener<AuthenticatedUser> listener)
	{
		this.loginEventSubscription.add(listener);
	}

	@Override
	public void removeLoginListener(IListener<AuthenticatedUser> listener)
	{
		this.loginEventSubscription.remove(listener);
	}

	@Override
	public CoOp getSelectedCoop()
	{
		if (this.headerComponent == null) return null;
		
		return this.headerComponent.getSelectedCoop();
	}

	@Override
	public void addSelectedCoopChangedListener(IListener<CoOp> listener)
	{
		this.selectedCoopChangedSubscription.add(listener);
	}

	@Override
	public void removeSelectedCoopChangedListener(IListener<CoOp> listener)
	{
		this.selectedCoopChangedSubscription.remove(listener);
	}

	@Override
	public void showAccessDeniedNotification()
	{
		showNotification(
				getLocalizedString("ACCESS_DENIED_CAPTION"), 
				getLocalizedString("ACCESS_DENIED_DESCRIPTION"), 
				Notification.TYPE_ERROR_MESSAGE);
	}

	@Override
	public void showInvalidDataNotification()
	{
		showNotification(
				getLocalizedString("INVALID_DATA_CAPTION"), 
				getLocalizedString("INVALID_DATA_DESCRIPTION"), 
				Notification.TYPE_ERROR_MESSAGE);
	}

	@Override
	public void showDataSavedNotification()
	{
		showNotification(
				getLocalizedString("SAVED_CHANGES_CAPTION"), 
				getLocalizedString("SAVED_CHANGES_DESCRIPTION"), 
				Notification.TYPE_HUMANIZED_MESSAGE);
		
	}

	@Override
	public void showEntityInUseNotification(String caption)
	{
		if (caption == null) 
			throw new IllegalArgumentException("Argument 'caption' must not be null.");
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("<div style=\"background-color: #404040; padding: 0.2em; border: solid 1px #808080;\">");
		builder.append(caption);
		builder.append("</div>");
		
		showNotification(
				getLocalizedString("RELATIONSHIP_VIOLATION_CAPTION"), 
				getLocalizedString("RELATIONSHIP_VIOLATION_DESCRIPTION") + builder.toString(), 
				Notification.TYPE_ERROR_MESSAGE);
	}
	
	@Override
	public void showNotification(String caption, String description, int notificationType)
	{
		if (caption == null) 
			throw new IllegalArgumentException("Argument 'caption' must not be null.");
		
		if (description == null) 
			throw new IllegalArgumentException("Argument 'description' must not be null.");
		
		getMainWindow().showNotification(
				caption, 
				"<br />" + description, 
				notificationType);
	}

	@Override
	public void showNotification(String caption, String description, int notificationType, int mSecDelay)
	{
		Window.Notification notification = 
			new Window.Notification(
					caption, 
					"<br />" + description, 
					notificationType);
		
		notification.setDelayMsec(mSecDelay);
		
		getMainWindow().showNotification(notification);
	}

	@Override
	public IHeaderView getHeaderView()
	{
		return headerComponent;
	}

	@Override
	public void fireSelectedCoopChanged()
	{
		selectedCoopChangedSubscription.fire(headerComponent.getSelectedCoop());
		
		AuthenticatedUser user = getSession().getAuthenticatedUser();
		
		// If the coop is group-based and supports invitations, check if there are
		// any new invitations for groups other than the current user's group.
		if (user instanceof Student)
		{
			CoOp coop = getSelectedCoop();
			
			if (coop != null && coop.isGroupCoOp() && coop.isSupportingInvitations())
			{
				Student student = (Student)user;
				
				// The invitations in-box and out-box are shown only when the student 
				// is fully valid.
				// Consequently, only show invitations notifications when the student is fully valid.
				if (!DataUtilities.studentHasCompleteProvision(student)) return;
				
				Registration registration = student.getActiveRegistration();
				
				if (registration != null)
				{
					Group group = registration.getGroup();
					
					// The set of received invitation is sorted by date.
					List<Invitation> receivedInvitations = new ArrayList<Invitation>(registration.getReceivedInvitations());
					
					int firstReceived;
					int i;
					
					for (firstReceived = 0, i = receivedInvitations.size() - 1; i >= 0; i--)
					{
						Invitation receivedInvitation = receivedInvitations.get(i);
						
						// If an invitation for the current user's group is found, stop, because we assume that
						// all earlier invitations are dismissed.
						if (receivedInvitation.getGroup() == group)
						{
							firstReceived = i + 1;
							break;
						}
					}
					
					int newInvitationsCount = receivedInvitations.size() - firstReceived;
					
					if (newInvitationsCount > 0)
					{
						showNotification(
								getLocalizedString("INCOMING_INVITATIONS_EXIST_CAPTION"), 
								String.format(getLocalizedString("INCOMING_INVITATIONS_EXIST_DESCRIPTION"), newInvitationsCount), 
								Notification.TYPE_TRAY_NOTIFICATION);
					}
					
				}
			}
		}
	}

	@Override
	public void suggestRegistration()
	{
		if (headerComponent != null) headerComponent.fireSuggestRegistration();
	}
	
	private String getDatabaseExceptionDescription(DatabaseException ex)
	{
		StringBuilder builder = new StringBuilder();
		
		java.sql.SQLException sqlException = (java.sql.SQLException)ex.getCause();
		
		for (; sqlException != null; sqlException = sqlException.getNextException())
		{
			String message = ex.getLocalizedMessage();
			
			builder.append("<div style=\"background-color: #404040; padding: 0.2em; border: solid 1px #808080;\">");
			
			builder.append(message);
			
			builder.append("</div>");
		}
		
		return builder.toString();
	}

	@Override
	public void close()
	{
		super.close();
		
		if (session != null) session.dispose();
	}

}
