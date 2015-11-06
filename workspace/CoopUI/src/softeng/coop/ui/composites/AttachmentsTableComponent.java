package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;
import com.vaadin.ui.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.presenters.*;

import softeng.ui.vaadin.data.*;
import softeng.ui.vaadin.mvp.*;

public abstract class AttachmentsTableComponent<P>
extends TableComponent<P, Attachment>
{
	private static final long serialVersionUID = 1L;

	public AttachmentsTableComponent()
	{
		super(getDefaultColumnSpecifications());
		
		setEditVisible(false);
		
		setEditable(true);
	}

	public AttachmentsTableComponent(String caption)
	{
		super(caption, getDefaultColumnSpecifications());
	}

	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		ArrayList<ColumnSpecification> columnSpecifications = 
			new ArrayList<ColumnSpecification>();
		
		columnSpecifications.add(new ColumnSpecification("name", "NAME_CAPTION"));
		columnSpecifications.add(new ColumnSpecification("tracking.created", "CREATED_DATE_CAPTION"));
		columnSpecifications.add(new ColumnSpecification("contentType", "CONTENT_TYPE_CAPTION"));
		
		return columnSpecifications;
	}

	@Override
	public Class<?> getType()
	{
		return Attachment.class;
	}

	@Override
	protected IModalView<BeanItem<Attachment>> showAddForm(BeanItem<Attachment> item)
	{
		return showForm(item);
	}

	@Override
	protected IModalView<BeanItem<Attachment>> showEditForm(BeanItem<Attachment> item)
	{
		return showForm(item);
	}
	
	private IModalView<BeanItem<Attachment>> showForm(BeanItem<Attachment> item)
	{
		AttachmentDialog dialog = new AttachmentDialog();
		
		dialog.setModal(true);
		dialog.setModel(item);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected Attachment createNewElement()
	{
		Attachment attachment = new Attachment();
		
		return attachment;
	}

	@Override
	protected Presenter<Collection<Attachment>, ICoopContext, ? extends IView<Collection<Attachment>, ICoopContext>> supplyPresenter()
	{
		return new AttachmentsTablePresenter<P>(this);
	}

	@SuppressWarnings("serial")
	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		setFieldFactory(new DefaultFieldFactory()
		{

			@Override
			public Field createField(Container container, Object itemId, Object propertyId, Component uiContext)
			{
				final Attachment attachment = (Attachment)itemId;
				
				if (/*propertyId.equals("name") || */propertyId.equals("contentType"))
				{
					LinkField linkField = LinkField.forAttachment(attachment, getApplication());
					
					linkField.setTargetName("_blank");
					
					return linkField;
				}
				else if (propertyId.equals("name"))
				{
					TextField textField = new TextField();
					
					Table table = (Table)uiContext;
					
					table.setColumnWidth(propertyId, 400);
					
					textField.setReadOnly(true);
					textField.setSizeFull();
					
					return textField;
				}
				else if (propertyId.equals("tracking.created"))
				{
					TextField textField = new TextField();
					
					Table table = (Table)uiContext;
					
					table.setColumnWidth(propertyId, 120);
					
					textField.setReadOnly(true);
					textField.setSizeFull();
					
					return textField;
				}
				
				return super.createField(container, itemId, propertyId, uiContext);
			}
			
		});

	}

	@Override
	protected BeanItemContainer<Attachment> createBeanItemContainer(Collection<Attachment> collection)
	{
		RestrictedBeanItemContainer<Attachment> container = new RestrictedBeanItemContainer<Attachment>(Attachment.class, collection);
		
		container.addNullableNestedContainerProperty("tracking.created");
		
		container.setContainerPropertyIds(getSpecifiedPropertyIds());
		
		return container;
	}

	@Override
	protected String getResourceBaseName()
	{
		return AttachmentsTableComponent.class.getCanonicalName();
	}

}
