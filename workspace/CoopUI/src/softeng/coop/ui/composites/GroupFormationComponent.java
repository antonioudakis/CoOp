package softeng.coop.ui.composites;

import java.util.List;
import java.util.Locale;
import java.util.Vector;

import softeng.coop.dataaccess.CoOp;
import softeng.coop.dataaccess.Group;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.data.MultilingualValidationForm;
import softeng.coop.ui.viewdefinitions.IGroupFormationView;
import softeng.coop.ui.viewdefinitions.ITableView;
import softeng.ui.vaadin.mvp.IView;
import softeng.ui.vaadin.mvp.Presenter;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class GroupFormationComponent 
	extends CoopComponent<BeanItem<Group>> 
	implements IGroupFormationView
{
	
	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private GroupForm form;
	@AutoGenerated
	private HorizontalLayout coopInfoHorizontalLayout;
	@AutoGenerated
	private TextField currentGroupSizeField;
	@AutoGenerated
	private TextField maxGroupSizeTextField;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private BeanItem<CoOp> coOp = null;
	
	private TextArea groupCommentsField;
	private GroupMembersTableComponent groupMembersTableComponent;
	private TextField jobNameField;
	
	private ITableView<CoOp, Group> groupsTableView;
	
	private static List<String> propertyIds = createPropertyIds();
	
	public class GroupForm extends MultilingualValidationForm<Group>
	{
		public GroupForm() 
		{
			super(Group.class);
		}
	}
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public GroupFormationComponent() 
	{
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	private static List<String> createPropertyIds()
	{
		Vector<String> properties = new Vector<String>();
		
		properties.add("comments");
		properties.add("job.jobPosting.name");
		properties.add("registrations");
		
		return properties;
	}

	@Override
	protected Presenter<BeanItem<Group>, ICoopContext, ? extends IView<BeanItem<Group>, ICoopContext>> 
	supplyPresenter() 
	{
		return null;
	}

	@Override
	public void dataBind() 
	{	
		if (this.coOp == null)
			return;
		
		maxGroupSizeTextField.setPropertyDataSource(this.coOp.getItemProperty("maxGroupSize"));
		
		resetCurrentGroupSizeField();
		
		if (this.getModel() != null)
		{
//			if (getModel().getBean().getJob() != null)
//			{
//				DataItem<Group> item = new DataItem<Group>(this.getModel().getBean(), 
//						getContext().getSession().getStudentsManager());
//				
//				item.addItemProperty("job.jobPosting.name", 
//						new NestedMethodProperty(this.getModel().getBean(), "job.jobPosting.name")
//				);
//				
//				form.setItemDataSource(item, propertyIds);
//			}
//			else
//			{
//				form.setItemDataSource(getModel(), propertyIds);
//			}		
			form.setItemDataSource(getModel(), propertyIds);
		}	
		else
		{
			form.setItemDataSource(null);
		}
		
		
		updateAddMemberButtonVisibility();
		
		groupMembersTableComponent.dataBind();
	}

	private void updateAddMemberButtonVisibility()
	{
		/* Don't show or hide any more. Make this NOP. */

//		if (groupMembersTableComponent != null && getModel() != null)
//		{
//			
//			if (groupMembersTableComponent.getParentModel().getRegistrations().size()
//					> getModel().getBean().getCoOp().getMaxGroupSize()-1)
//			{
//				groupMembersTableComponent.setAddVisible(false);
//			}
//			else
//			{
//				groupMembersTableComponent.setAddVisible(true);
//			}
//		}
	}

	protected void resetCurrentGroupSizeField()
	{
		if (getModel()!= null)
		{	
			currentGroupSizeField.setValue(getModel().getBean().getRegistrations().size());
		}
		else
		{
			currentGroupSizeField.setValue("");
		}
	}

	@Override
	protected void setupUI() 
	{
		super.setupUI();
		
		this.maxGroupSizeTextField.setCaption(getLocalizedString("GROUP_MAX_SIZE_CAPTION"));
		this.maxGroupSizeTextField.setReadOnly(true);
		
		this.currentGroupSizeField.setCaption(getLocalizedString("GROUP_CURRENT_SIZE_CAPTION"));
		currentGroupSizeField.setEnabled(false);
		this.currentGroupSizeField.setImmediate(true);
		
		form.setImmediate(true);
		form.setWriteThrough(false);
		
		form.setFormFieldFactory(new FormFieldFactory() 
		{
			
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext) 
			{
				if (propertyId.equals("comments"))
				{
					groupCommentsField = new TextArea(getLocalizedString("GROUP_COMMENTS_CAPTION"));
					groupCommentsField.setWidth("100%");
					groupCommentsField.setNullRepresentation("");
					
					return groupCommentsField;
				}
				else if (propertyId.equals("job.jobPosting.name"))
				{
					jobNameField = new TextField(getLocalizedString("JOB_NAME_CAPTION"));
					jobNameField.setWidth("100%");
					jobNameField.setNullRepresentation(getLocalizedString("JOB_NAME_NOT_ASSIGNED_VALUE"));
					jobNameField.setReadOnly(true);
					
					return jobNameField;
				}
				else if (propertyId.equals("registrations"))
				{
					groupMembersTableComponent = new GroupMembersTableComponent();
					groupMembersTableComponent.setCaption(getLocalizedString("GROUP_MEMBER_TABLE_CAPTION"));
					groupMembersTableComponent.setWidth("100%");
					groupMembersTableComponent.setParentModel(getModel().getBean());
					groupMembersTableComponent.setEditVisible(false);
					groupMembersTableComponent.setDeleteVisible(false);
					groupMembersTableComponent.setRemovingOrphanGroups(true);
					groupMembersTableComponent.setGroupsTableView(groupsTableView);
					
					return groupMembersTableComponent;
				}
				return null;
			}
		});
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale) 
	{
		super.setupLocalizedCaptions(locale);
		
		if (groupCommentsField != null)
			groupCommentsField.setCaption(getLocalizedString("GROUP_COMMENTS_CAPTION"));
		
		if(maxGroupSizeTextField != null)
			maxGroupSizeTextField.setCaption(getLocalizedString("GROUP_MAX_SIZE_CAPTION"));
		
		if (groupMembersTableComponent != null)
			groupMembersTableComponent.setCaption(getLocalizedString("GROUP_MEMBER_TABLE_CAPTION"));
		
		if (currentGroupSizeField != null)
			currentGroupSizeField.setCaption(getLocalizedString("GROUP_CURRENT_SIZE_CAPTION"));

		if (jobNameField != null)
		{
			jobNameField.setCaption(getLocalizedString("JOB_NAME_CAPTION"));
			jobNameField.setNullRepresentation(getLocalizedString("JOB_NAME_NOT_ASSIGNED_VALUE"));
		}

	}

	@Override
	public void setCoopModel(BeanItem<CoOp> coop) 
	{
		this.coOp = coop;
	}

	@Override
	public boolean isDataValid() 
	{
		return this.form.isValid();
	}

	@Override
	public void discardChanges() 
	{
		this.form.discard();
		
		resetCurrentGroupSizeField();
		
		updateAddMemberButtonVisibility();
	}

	@Override
	public void commitChangesToModel() 
	{
		this.form.commit();
		
	}
	
	public void setGroupsTableView(ITableView<CoOp, Group> groupsTableView)
	{
		this.groupsTableView = groupsTableView;
	}
	
	public ITableView<CoOp, Group> getGroupsTableView()
	{
		return groupsTableView;
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("-1px");
		
		// coopInfoHorizontalLayout
		coopInfoHorizontalLayout = buildCoopInfoHorizontalLayout();
		mainLayout.addComponent(coopInfoHorizontalLayout);
		
		// form
		form = new GroupForm();
		form.setCaption("�������� ������");
		form.setImmediate(false);
		form.setWidth("100.0%");
		form.setHeight("-1px");
		mainLayout.addComponent(form);
		
		return mainLayout;
	}

	@SuppressWarnings("deprecation")
	@AutoGenerated
	private HorizontalLayout buildCoopInfoHorizontalLayout() {
		// common part: create layout
		coopInfoHorizontalLayout = new HorizontalLayout();
		coopInfoHorizontalLayout.setImmediate(false);
		coopInfoHorizontalLayout.setWidth("100.0%");
		coopInfoHorizontalLayout.setHeight("-1px");
		coopInfoHorizontalLayout.setMargin(false);
		coopInfoHorizontalLayout.setSpacing(true);
		
		// maxGroupSizeTextField
		maxGroupSizeTextField = new TextField();
		maxGroupSizeTextField.setCaption("������� ������� ������");
		maxGroupSizeTextField.setImmediate(false);
		maxGroupSizeTextField.setWidth("100.0%");
		maxGroupSizeTextField.setHeight("-1px");
		maxGroupSizeTextField.setSecret(false);
		coopInfoHorizontalLayout.addComponent(maxGroupSizeTextField);
		
		// currentGroupSizeField
		currentGroupSizeField = new TextField();
		currentGroupSizeField.setCaption("������ ������� ������");
		currentGroupSizeField.setImmediate(false);
		currentGroupSizeField.setWidth("100.0%");
		currentGroupSizeField.setHeight("-1px");
		currentGroupSizeField.setSecret(false);
		coopInfoHorizontalLayout.addComponent(currentGroupSizeField);
		
		return coopInfoHorizontalLayout;
	}

}
