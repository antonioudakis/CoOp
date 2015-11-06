package softeng.ui.vaadin.mvp;

import java.util.*;

import com.vaadin.data.*;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.terminal.CompositeErrorMessage;
import com.vaadin.terminal.ErrorMessage;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.*;

public abstract class ModelField<M, C>
	extends ModelCustomComponent<M, C>
	implements Field
{
	private static final long serialVersionUID = 1L;

	private boolean required = false;
	private String requiredError = "This field is required";
	private boolean readThrough = false;
	private boolean writeThrough = true;
	private int tabIndex = 0;
	private Property propertyDataSource = null;
	private boolean invalidValueAllowed = true;
	private boolean invalidCommitted = false;
	private List<Validator> validators = new ArrayList<Validator>();
	private List<ValueChangeListener> valueChangeListeners = 
		new ArrayList<Property.ValueChangeListener>();
	
	private boolean modified = false;
	
	private class DataSourceListener implements Property.ValueChangeListener
	{
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event)
		{
			if (isReadThrough())
			{
				setModel((M)event.getProperty().getValue());
				dataBind();
			}
		}
	}
	
	private DataSourceListener dataSourceListener =
		new DataSourceListener();
	
	public ModelField()
	{
	}
	
	public ModelField(String caption)
	{
		this.setCaption(caption);
	}
	
	@Override
	public boolean isInvalidCommitted()
	{
		return this.invalidCommitted;
	}

	@Override
	public void setInvalidCommitted(boolean isCommitted)
	{
		this.invalidCommitted = isCommitted;
	}

	@Override
	public void commit() throws SourceException, InvalidValueException
	{
		if (this.getPropertyDataSource() != null && (this.isInvalidCommitted() || this.isValid()))
		{
			M model = this.getModel();
			
			this.getPropertyDataSource().setValue(model);
			
			this.modified = false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void discard() throws SourceException
	{
		if (this.getPropertyDataSource() != null)
		{
			//this.setValue(this.getPropertyDataSource().getValue());
			this.setModel((M)getPropertyDataSource().getValue());
			this.modified = false;
			this.dataBind();
		}
	}

	@Override
	public boolean isWriteThrough()
	{
		return this.writeThrough;
	}

	@Override
	public void setWriteThrough(boolean writeThrough) throws SourceException, InvalidValueException
	{
		if (writeThrough && !this.writeThrough) commit();
		
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
		// Note: All of Vaadin components return always false.
		return this.modified;
	}

	@Override
	public void addValidator(Validator validator)
	{
		if (validator == null) 
			throw new IllegalArgumentException("Argument 'validator' must not be null.");
		
		this.validators.add(validator);
		
		this.requestRepaint();
	}

	@Override
	public void removeValidator(Validator validator)
	{
		if (validator == null) 
			throw new IllegalArgumentException("Argument 'validator' must not be null.");
		
		this.validators.remove(validator);

		this.requestRepaint();
	}

	@Override
	public Collection<Validator> getValidators()
	{
		if (this.validators == null || this.validators.isEmpty())
			return Collections.emptyList();
		
		return Collections.unmodifiableCollection(this.validators);
	}

	@Override
	public boolean isValid()
	{
		if (this.isRequired() && this.isEmpty()) return false;
		
		M model = this.getModel();
		
		for (Validator validator : this.getValidators())
		{
			if (!validator.isValid(model)) return false;
		}
		
		return true;
	}

	@Override
	public void validate() throws InvalidValueException
	{
		if (this.isRequired() && this.isEmpty())
			throw new Validator.EmptyValueException(getRequiredError());
		
		M model = this.getModel();
		
		for (Validator validator : this.getValidators())
		{
			validator.validate(model);
		}
	}

	@Override
	public boolean isInvalidAllowed()
	{
		return this.invalidValueAllowed;
	}

	@Override
	public void setInvalidAllowed(boolean invalidValueAllowed) throws UnsupportedOperationException
	{
		this.invalidValueAllowed = invalidValueAllowed;
	}

	@Override
	public Object getValue()
	{
		if (this.isReadThrough() && this.getPropertyDataSource() != null && !this.isModified())
		{
			this.setValue(this.getPropertyDataSource().getValue());
		}
		
		return this.getModel();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException
	{
		M oldValue = this.getModel();
		
		if (oldValue != null)
		{
			if (oldValue.equals(newValue)) return;
		}
		else if (newValue != null)
		{
			if (newValue.equals(oldValue)) return;
		}
		else return;
		
		M model = (M)newValue;
		
		if (!isInvalidAllowed())
		{
			for (Validator validator : validators)
			{
				validator.validate(model);
			}
		}
		
		super.setModel(model);
		
		this.modified = true;
		
		this.dataBind();
		
		if (this.isWriteThrough()) this.commit();
		
		ValueChangeEvent valueChangeEvent = new ValueChangeEvent(this);
		
		for (ValueChangeListener listener : this.valueChangeListeners)
		{
			listener.valueChange(valueChangeEvent);
		}
	}

	@Override
	public void addListener(ValueChangeListener listener)
	{
		if (listener == null) 
			throw new IllegalArgumentException("Argument 'listener' must not be null.");
		
		this.valueChangeListeners.add(listener);
	}

	@Override
	public void removeListener(ValueChangeListener listener)
	{
		if (listener == null) 
			throw new IllegalArgumentException("Argument 'listener' must not be null.");

		this.valueChangeListeners.remove(listener);
	}

	@Override
	public void valueChange(com.vaadin.data.Property.ValueChangeEvent event)
	{
		Object changedValue = event.getProperty().getValue();
		
		this.setValue(changedValue);
	}

	@SuppressWarnings("unchecked")
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
			M value = (M)newDataSource.getValue();
			this.setModel(value);
			this.dataBind();
			
			if (newDataSource instanceof Property.ValueChangeNotifier)
			{
				Property.ValueChangeNotifier valueChangeNotifier = 
					(Property.ValueChangeNotifier)this.propertyDataSource;
				
				valueChangeNotifier.addListener(this.dataSourceListener);
			}
		}
		else
		{
			this.setModel(null);
			this.dataBind();
		}
	}
	
	@Override
	public void setModel(M model)
	{
		M oldModel = this.getModel();
		
		super.setModel(model);
		
		if (model != null)
		{
			if (model.equals(oldModel)) return;
		}
		else if (oldModel != null)
		{
			if (oldModel.equals(model)) return;
		}
		// If we reach here, both are null, thus equal.
		else return;
		
		ValueChangeEvent valueChangeEvent = new ValueChangeEvent(this);
		
		for (ValueChangeListener listener : this.valueChangeListeners)
		{
			listener.valueChange(valueChangeEvent);
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
		return this.required;
	}

	@Override
	public void setRequired(boolean required)
	{
		this.required = required;
		requestRepaint();
	}

	@Override
	public void setRequiredError(String requiredMessage)
	{
		if (requiredMessage == null) 
			throw new IllegalArgumentException("Argument 'requiredMessage' must not be null.");
		
		this.requiredError = requiredMessage;
	}

	@Override
	public String getRequiredError()
	{
		return this.requiredError;
	}
	
	@Override
	public void focus()
	{
		super.focus();
	}

	@Override
	public void paintContent(PaintTarget target) throws PaintException
	{
		super.paintContent(target);

		if (tabIndex != 0)
			target.addAttribute("tabIndex", tabIndex);
		
		if (isModified())
			target.addAttribute("modified", true);
		
		if (!isReadOnly() && isRequired())
			target.addAttribute("required", true);
		
		if (isRequired() && isEmpty() && getComponentError() == null && getErrorMessage() != null)
			target.addAttribute("hideErrors", true);
		
	}
	
	protected boolean isEmpty()
	{
		return this.getModel() == null;
	}

	@Override
	public ErrorMessage getErrorMessage()
	{
    ErrorMessage validationError = null;

    try 
    {
        validate();
    } 
    catch (Validator.InvalidValueException e) 
    {
        if (!e.isInvisible()) 
        {
            validationError = e;
        }
    }

    // Check if there are any systems errors
    final ErrorMessage superError = super.getErrorMessage();

    // Return if there are no errors at all
    if (superError == null && validationError == null) 
    {
        return null;
    }

    // Throw combination of the error types
    return new CompositeErrorMessage(new ErrorMessage[] { superError,
            validationError });
	}

	/**
	 * If this field has validators, call 'requestRepaint'.
	 */
	protected void updateValidationMarking()
	{
		if (this.getValidators() != null && this.getValidators().size() > 0)
			requestRepaint();
	}
}
