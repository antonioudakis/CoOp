package softeng.coop.ui.composites;

import java.util.*;
import java.io.*;

import com.vaadin.*;
import com.vaadin.data.*;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.terminal.*;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.ui.*;

import softeng.coop.dataaccess.*;

/**
 * A Vaadin link which also implements the Field interface.
 * The value of the field is the link's caption.
 */
public class LinkField
extends Link
implements Field
{
	private static final long serialVersionUID = 1L;

	private class DataSourceListener implements Property.ValueChangeListener
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event)
		{
			if (isReadThrough())
			{
				setValue(event.getProperty().getValue());
			}
		}
	}

	private Property propertyDataSource = null;

	private String requiredError;
	private boolean required, invalidCommitted, invalidAllowed;
	private boolean writeThrough = true, readThrough = true;
	private int tabIndex;
	
	private Set<Validator> validators = new HashSet<Validator>();
	
	private Set<ValueChangeListener> valueChangeListners =
		new HashSet<ValueChangeListener>();

	private DataSourceListener dataSourceListener =
		new DataSourceListener();
	
	public LinkField()
	{
		
	}
	
	public LinkField(String caption, Resource resource)
	{
		super(caption, resource);
	}
	
	/**
	 * Create a link field that provides the content of 
	 * the given the attachment.
	 * @param attachment The attachment to link to.
	 * @param application The current application.
	 * @return Returns the link.
	 */
	public static LinkField forAttachment(final Attachment attachment, Application application)
	{
		if (attachment == null) 
			throw new IllegalArgumentException("Argument 'attachment' must not be null.");
		if (application == null) 
			throw new IllegalArgumentException("Argument 'application' must not be null.");
		
		LinkField linkField = new LinkField();
		
		@SuppressWarnings("serial")
		StreamSource streamSource = new StreamSource()
		{
			@Override
			public InputStream getStream()
			{
				return new ByteArrayInputStream(attachment.getContent());
			}
		}; 
		
		StreamResource resource = null;
		try
		{
			resource = new StreamResource(
					streamSource, 
					java.net.URLEncoder.encode(attachment.getName(), "utf-8").replace('+', ' '), 
					application);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			
			return null;
		}

		resource.setMIMEType(attachment.getContentType());
		
		linkField.setResource(resource);
		
		return linkField;
	}
	
	@Override
	public boolean isInvalidCommitted()
	{
		return invalidCommitted;
	}

	@Override
	public void setInvalidCommitted(boolean isCommitted)
	{
		this.invalidCommitted = isCommitted;
	}

	@Override
	public void commit() throws SourceException, InvalidValueException
	{
		// NOP.
	}

	@Override
	public void discard() throws SourceException
	{
		// NOP.
	}

	@Override
	public boolean isWriteThrough()
	{
		return writeThrough;
	}

	@Override
	public void setWriteThrough(boolean writeThrough) throws SourceException, InvalidValueException
	{
		this.writeThrough = writeThrough;
	}

	@Override
	public boolean isReadThrough()
	{
		return this.readThrough;
	}

	@Override
	public void setReadThrough(boolean readThrough) throws SourceException
	{
		this.readThrough = readThrough;
	}

	@Override
	public boolean isModified()
	{
		return false;
	}

	@Override
	public void addValidator(Validator validator)
	{
		if (validator == null) 
			throw new IllegalArgumentException("Argument 'validator' must not be null.");
		
		validators.add(validator);
	}

	@Override
	public void removeValidator(Validator validator)
	{
		if (validator == null) 
			throw new IllegalArgumentException("Argument 'validator' must not be null.");
		
		validators.remove(validator);
	}

	@Override
	public Collection<Validator> getValidators()
	{
		return validators;
	}

	@Override
	public boolean isValid()
	{
		String value = getCaption();
		
		if (this.isRequired())
		{
			if (value == null || value.isEmpty()) return false;
		}
		
		for (Validator validator : getValidators())
		{
			if (!validator.isValid(value)) return false;
		}
		
		return true;
	}

	@Override
	public void validate() throws InvalidValueException
	{
		Object value = getValue();
		
		for (Validator validator : getValidators())
		{
			validator.validate(value);
		}
	}

	@Override
	public boolean isInvalidAllowed()
	{
		return this.invalidAllowed;
	}

	@Override
	public void setInvalidAllowed(boolean invalidValueAllowed) throws UnsupportedOperationException
	{
		this.invalidAllowed = invalidValueAllowed;
	}

	@Override
	public Object getValue()
	{
		return getCaption();
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException
	{
		if (newValue != null && !(newValue instanceof String)) 
			throw new IllegalArgumentException("Argument 'newValue' should be string.");
		
		setCaption((String)newValue);
	}

	@Override
	public Class<?> getType()
	{
		return String.class;
	}

	@Override
	public void addListener(ValueChangeListener listener)
	{
		if (listener == null) 
			throw new IllegalArgumentException("Argument 'listener' must not be null.");
		
		valueChangeListners.add(listener);
	}

	@Override
	public void removeListener(ValueChangeListener listener)
	{
		if (listener == null) 
			throw new IllegalArgumentException("Argument 'listener' must not be null.");
		
		valueChangeListners.remove(listener);
	}

	@Override
	public void valueChange(com.vaadin.data.Property.ValueChangeEvent event)
	{
		Object changedValue = event.getProperty().getValue();
		
		this.setValue(changedValue);
	}

	@Override
	public void setPropertyDataSource(Property newDataSource)
	{
		if (this.propertyDataSource != null)
		{
			if (this.propertyDataSource instanceof Property.ValueChangeNotifier)
			{
				Property.ValueChangeNotifier valueChangeNotifier = 
					(Property.ValueChangeNotifier)this.propertyDataSource;
				
				valueChangeNotifier.removeListener(this.dataSourceListener);
			}
		}
		
		this.propertyDataSource = newDataSource;
		
		if (newDataSource != null)
		{
			setValue(newDataSource.getValue());
			
			if (newDataSource instanceof Property.ValueChangeNotifier)
			{
				Property.ValueChangeNotifier valueChangeNotifier = 
					(Property.ValueChangeNotifier)this.propertyDataSource;
				
				valueChangeNotifier.addListener(this.dataSourceListener);
			}
		}
		else
		{
			setValue(null);
		}
	}

	@Override
	public Property getPropertyDataSource()
	{
		return this.propertyDataSource;
	}

	@Override
	public int getTabIndex()
	{
		return this.tabIndex;
	}

	@Override
	public void setTabIndex(int tabIndex)
	{
		this.tabIndex = tabIndex;
	}

	@Override
	public boolean isRequired()
	{
		return required;
	}

	@Override
	public void setRequired(boolean required)
	{
		this.required = required;
	}

	@Override
	public void setRequiredError(String requiredMessage)
	{
		requiredError = requiredMessage;
	}

	@Override
	public String getRequiredError()
	{
		return requiredError;
	}

	@Override
	public void focus()
	{
		super.focus();
	}

}
