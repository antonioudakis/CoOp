package softeng.coop.ui.presenters;

import softeng.coop.business.*;
import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.composites.*;
import softeng.coop.ui.data.DataUtilities;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

import java.util.*;

import com.vaadin.ui.*;

public class TabPresenter 
	extends Presenter<List<TabSpecification>, ICoopContext, ITabView>
{
	private static class GroupCoopVisibility
	implements IComponentVisibility
	{
		@Override
		public boolean isVisible(ICoopContext context)
		{
			CoOp coop = context.getSelectedCoop();
			
			if (coop == null) return false;
			
			return coop.isGroupCoOp();
		}
	}
	
	private static class SelectedCoopVisibility
	implements IComponentVisibility
	{
		@Override
		public boolean isVisible(ICoopContext context)
		{
			return context.getSelectedCoop() != null;
		}
	}
	
	private static IComponentVisibility groupCoopVisibility =
		new GroupCoopVisibility();
	
	private static IComponentVisibility selectedCoopVisibility =
		new SelectedCoopVisibility();

	public TabPresenter(ITabView view)
	{
		super(view);
	}

	@Override
	protected void setupView()
	{
		Session session = getContext().getSession();
		
		if (session != null)
		{
			AuthenticatedUser user = getContext().getSession().getAuthenticatedUser();
			
			if (user instanceof Student)
			{
				specifyStudentTabs();
			}
			else if (user instanceof FacultyUser)
			{
				specifyFacultyUserTabs();
			}
		}
		
		this.getView().dataBind();
	}

	private void specifyFacultyUserTabs()
	{
		Session session = getContext().getSession();
		
		if (session == null) return;
		
		if (session.getCompaniesManager() != null
				&& session.getCompaniesManager().isWriteable())
		{
			getView().getModel().add(
				new TabSpecification("COMPANIES_CAPTION", "COMPANIES_DESCRIPTION", new IComponentFactory() 
				{
					@Override
					public Component createComponent() 
					{
						return new CompaniesCardComponent();
					}
				})
			);
		}
		
		if (session.getPaymentsManager() != null && 
				session.getPaymentsManager().isWriteable())
		{
			getView().getModel().add(
				new TabSpecification("FINANCIAL_SOURCES_CAPTION", "FINANCIAL_SOURCES_DESCRIPTION", new IComponentFactory()
				{
					@Override
					public Component createComponent()
					{
						return new FinancialSourcesCardComponent();
					}
				}));
		}
		
		if (session.getUniversitiesManager() != null
				&& session.getUniversitiesManager().isWriteable())
		{
			getView().getModel().add(
				new TabSpecification("LESSONS_CAPTION", "LESSONS_DESCRIPTION", new IComponentFactory()
				{
					@Override
					public Component createComponent()
					{
						return new LessonsCardComponent();
					}
				})
			);
		}
		
		if (session.getFacultyUsersManager() != null
				&& session.getFacultyUsersManager().isWriteable()
				&& session.getStudentsManager() != null
				&& session.getStudentsManager().isWriteable())
		{
			getView().getModel().add(new TabSpecification("USERS_CAPTION", "USERS_DESCRIPTION", new IComponentFactory() {
				
				@Override
				public Component createComponent() 
				{
					return new UserManagementCardComponent();
				}
			}));
		}
		
		if (session.getCoOpsManager() != null
				&& session.getCoOpsManager().isWriteable())
		{
			getView().getModel().add(
				new TabSpecification("COOPS_CAPTION", "COOPS_DESCRIPTION", new IComponentFactory()
				{
					@Override
					public Component createComponent()
					{
						return new CoopSettingsCardComponent();
					}
				})
			);
		}
		
		if (session.getCompaniesManager() != null && 
				session.getCoOpsManager() != null && 
				session.getCoOpsManager().isWriteable())
		{
			getView().getModel().add(
				new TabSpecification("COMPANIES_SELECT_CAPTION", "COMPANIES_SELECT_DESCRIPTION", new IComponentFactory() 
				{
					@Override
					public Component createComponent() 
					{
						return new CompaniesSelectCardComponent();
					}
				},
				selectedCoopVisibility)
			);
		}
		
		if (session.getStudentsManager() != null 
				&& session.getStudentsManager().isWriteable())
		{
			getView().getModel().add(
					new TabSpecification("REGISTRATIONS_MANAGEMENT_CAPTION", "REGISTRATIONS_MANAGEMENT_DESCRIPTION", new IComponentFactory() {
						
						@Override
						public Component createComponent() 
						{
							return new RegistrationsCardComponent();
						}
					},
					selectedCoopVisibility)
			);
		}
		
		if(session.getStudentsManager() != null 
				&& session.getStudentsManager().isWriteable())
		{
			getView().getModel().add(
					new TabSpecification(
							"GROUP_CAPTION", 
							"GROUP_DESCRIPTION", 
							new IComponentFactory() 
							{
								@Override
								public Component createComponent() 
								{
									return new GroupManagementCardComponent();
								}
							},
							groupCoopVisibility)
			);
		}
		
		if (session.getJobPostingsManager() != null 
				&& session.getJobPostingsManager().isWriteable())
		{
			getView().getModel().add(
					new TabSpecification("JOB_POSTINGS_CAPTION", "JOB_POSTINGS_DESCRIPTION", new IComponentFactory() {
						
						@Override
						public Component createComponent() 
						{
							return new JobPostingsCardComponent();
						}
					},
					selectedCoopVisibility)
			);
		}
		
		if (session.getJobsManager() != null
				&& session.getJobsManager().isWriteable())
		{
			getView().getModel().add(
				new TabSpecification("JOB_ASSIGNMENTS_CAPTION", "JOB_ASSIGNMENTS_DESCRIPTION", new IComponentFactory()
				{
					@Override
					public Component createComponent() 
					{
						return new TwinSelectTableComponent();
					}
				},
				selectedCoopVisibility)
			);
		}
		
		if (session.getStudentsManager() != null &&
				session.getStudentsManager().isWriteable())
		{
			getView().getModel().add(
				new TabSpecification("FINANCIAL_MANAGEMENT_CAPTION", "FINANCIAL_MANAGEMENT_DESCRIPTION", new IComponentFactory()
				{
					@Override
					public Component createComponent() 
					{
						return new FinancialCardComponent();
					}
				},
				selectedCoopVisibility)
			);
		}
		
	}

	private void specifyStudentTabs()
	{
		getView().getModel().add(
			new TabSpecification("MY_RECORD_CAPTION", "MY_RECORD_DESCRIPTION", new IComponentFactory()
			{
				@Override
				public Component createComponent()
				{
					return new StudentCardComponent();
				}
			})
		);
		
		getView().getModel().add(
			new TabSpecification(
					"MY_PREFERENCES_CAPTION", 
					"MY_PREFERENCES_DESCRIPTION", 
					new IComponentFactory() 
					{
						@Override
						public Component createComponent() 
						{
							return new StudentPreferencesCardComponent();
						}
					},
					new IComponentVisibility()
					{
						@Override
						public boolean isVisible(ICoopContext context)
						{
							CoOp selectedCoop = context.getSelectedCoop();
							
							if (selectedCoop == null) return false;
							
							return selectedCoop.isSetup() &&
								DataUtilities.studentHasCompleteProvision((Student)context.getSession().getAuthenticatedUser()) &&
								(selectedCoop.isAllowCategoryPreferences() || 
								selectedCoop.isAllowJobPostingsPreferences() ||
								selectedCoop.isAllowLocationPreferences());
						}
					})
		);
		
		getView().getModel().add(
				new TabSpecification(
						"MY_GROUP_CAPTION", 
						"MY_GROUP_DESCRIPTION", 
						new IComponentFactory()
						{
							@Override
							public Component createComponent()
							{
								return new StudentGroupCardComponent();
							}
						}, 
						new IComponentVisibility()
						{
							
							@Override
							public boolean isVisible(ICoopContext context)
							{
								CoOp coop = context.getSelectedCoop();
								
								if (coop == null) return false;
								
								return coop.isGroupCoOp();
							}
						}));

		getView().getModel().add(
			new TabSpecification(
					"MY_REPORTS_CAPTION", 
					"MY_REPORTS_DESCRIPTION", 
					new IComponentFactory()
					{
						@Override
						public Component createComponent()
						{
							return new StudentJobCardComponent();
						}
					},
					new IComponentVisibility()
					{
						@Override
						public boolean isVisible(ICoopContext context)
						{
							CoOp coop = context.getSelectedCoop();
							
							if (coop == null) return false;
							
							AuthenticatedUser user = context.getSession().getAuthenticatedUser();
							
							if (user instanceof Student)
							{
								Student student = (Student)user;
								
								Registration registration = 
									student.getActiveRegistration();
								
								if (registration == null) return false;
								
								if (!DataUtilities.studentHasCompleteProvision(student)) return false;
								
								Group group = registration.getGroup();
								
								if (group != null)
								{
									return group.getJob() != null;
								}
							}
							
							return false;
						}
					})
		);
		
		getView().getModel().add(
				new TabSpecification(
					"SENT_INVITATIONS_CAPTION", 
					"SENT_INVITATIONS_DESCRIPTION", 
					new IComponentFactory()
					{
						@Override
						public Component createComponent()
						{
							InvitationsBoxComponent component = new InvitationsBoxComponent();
							
							component.setInvitationType(InvitationType.SentInvitations);
							
							return component;
						}
					}, 
					new IComponentVisibility()
					{
						@Override
						public boolean isVisible(ICoopContext context)
						{
							CoOp coop = context.getSelectedCoop();
							
							if (coop == null) return false;
							
							return 
								coop.isGroupCoOp() && 
								coop.isSupportingInvitations() && 
								coop.isInRegistration() &&
								DataUtilities.studentHasCompleteProvision((Student)context.getSession().getAuthenticatedUser());
						}
					}));
		
		getView().getModel().add(
				new TabSpecification(
					"RECEIVED_INVITATIONS_CAPTION", 
					"RECEIVED_INVITATIONS_DESCRIPTION", 
					new IComponentFactory()
					{
						@Override
						public Component createComponent()
						{
							InvitationsBoxComponent component = new InvitationsBoxComponent();
							
							component.setInvitationType(InvitationType.ReceivedInvitations);
							
							return component;
						}
					}, 
					new IComponentVisibility()
					{
						@Override
						public boolean isVisible(ICoopContext context)
						{
							CoOp coop = context.getSelectedCoop();
							
							if (coop == null) return false;
							
							return 
								coop.isGroupCoOp() && 
								coop.isSupportingInvitations() && 
								coop.isInRegistration() &&
								DataUtilities.studentHasCompleteProvision((Student)context.getSession().getAuthenticatedUser());
						}
					}));
	}

	@Override
	protected void attachToView()
	{
		getContext().addSelectedCoopChangedListener(new IListener<CoOp>()
		{
			@Override
			public void onEvent(CoOp event)
			{
				getView().refreshTabsVisibility();
			}
		});
	}
}
