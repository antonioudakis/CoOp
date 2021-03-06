package softeng.coop.ui.composites;

import java.util.Collection;
import java.util.Locale;

import softeng.coop.dataaccess.Group;
import softeng.coop.dataaccess.Registration;
import softeng.coop.dataaccess.Student;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.composites.PersonDataComponent.ViewMode;
import softeng.coop.ui.presenters.StudentRegistrationPresenter;
import softeng.coop.ui.viewdefinitions.IStudentGroupCardView;
import softeng.ui.vaadin.mvp.IListener;
import softeng.ui.vaadin.mvp.IView;
import softeng.ui.vaadin.mvp.ModelEvent;
import softeng.ui.vaadin.mvp.Presenter;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class StudentGroupCardComponent 
extends CoopComponent<BeanItem<Registration>>
implements IStudentGroupCardView
{

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private HorizontalLayout groupHorizontalLayout;
	@AutoGenerated
	private PersonDataComponent personDataComponent;
	@AutoGenerated
	private GroupMembersTableComponent groupMembersTableComponent;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public StudentGroupCardComponent()
	{
		buildMainLayout();
		setCompositionRoot(mainLayout);

		groupMembersTableComponent.setAddVisible(false);
		groupMembersTableComponent.setEditable(false);
		groupMembersTableComponent.setDeleteVisible(false);
		groupMembersTableComponent.setReadOnly(true);
		groupMembersTableComponent.setImmediate(true);
		
		groupMembersTableComponent.addSelectedChangeListener(new IListener<ModelEvent<Registration>>()
		{
			@Override
			public void onEvent(ModelEvent<Registration> event)
			{
				onSelectedGroupMemberChanged();
			}
		});
		
		personDataComponent.setMode(ViewMode.Compact);
		personDataComponent.setReadOnly(true);
	}

	protected void onSelectedGroupMemberChanged()
	{
		Registration memberRegistration = groupMembersTableComponent.getSelectedValue();
		
		BeanItem<Student> studentItem = null;
		
		if (memberRegistration != null)
		{
			studentItem = new BeanItem<Student>(memberRegistration.getStudent());
		}
		
		personDataComponent.setModel(studentItem);
		personDataComponent.dataBind();
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout()
	{
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// groupHorizontalLayout
		groupHorizontalLayout = buildGroupHorizontalLayout();
		mainLayout.addComponent(groupHorizontalLayout);
		
		return mainLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildGroupHorizontalLayout()
	{
		// common part: create layout
		groupHorizontalLayout = new HorizontalLayout();
		groupHorizontalLayout.setImmediate(false);
		groupHorizontalLayout.setWidth("100.0%");
		groupHorizontalLayout.setHeight("-1px");
		groupHorizontalLayout.setMargin(false);
		groupHorizontalLayout.setSpacing(true);
		
		// groupMembersTableComponent
		groupMembersTableComponent = new GroupMembersTableComponent();
		groupMembersTableComponent.setImmediate(false);
		groupMembersTableComponent.setWidth("100.0%");
		groupMembersTableComponent.setHeight("-1px");
		groupHorizontalLayout.addComponent(groupMembersTableComponent);
		groupHorizontalLayout.setExpandRatio(groupMembersTableComponent, 0.3f);
		
		// personDataComponent
		personDataComponent = new PersonDataComponent();
		personDataComponent.setImmediate(false);
		personDataComponent.setWidth("100.0%");
		personDataComponent.setHeight("-1px");
		groupHorizontalLayout.addComponent(personDataComponent);
		groupHorizontalLayout.setExpandRatio(personDataComponent, 0.7f);
		
		return groupHorizontalLayout;
	}

	@Override
	protected Presenter<BeanItem<Registration>, ICoopContext, ? extends IView<BeanItem<Registration>, ICoopContext>> supplyPresenter()
	{
		return new StudentRegistrationPresenter<IStudentGroupCardView>(this);
	}

	@Override
	public void dataBind()
	{
		BeanItem<Registration> registrationItem = getModel();
		
		Collection<Registration> registrations = null;
		Group group = null;
		
		if (registrationItem != null)
		{
			group = registrationItem.getBean().getGroup();
			
			if (group != null)
			{
				registrations = group.getRegistrations();
			}
		}
		
		groupMembersTableComponent.setParentModel(group);
		groupMembersTableComponent.setModel(registrations);
		groupMembersTableComponent.dataBind();
		
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);

		groupMembersTableComponent.setCaption(getLocalizedString("GROUP_MEMBERS_CAPTION"));
	}

}
