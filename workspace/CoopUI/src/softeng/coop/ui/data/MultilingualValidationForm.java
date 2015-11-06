package softeng.coop.ui.data;

import java.util.*;

import java.beans.*;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;

import com.vaadin.addon.beanvalidation.*;
import com.vaadin.data.*;
import com.vaadin.data.util.*;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;

/**
 * A BeanValidationForm that supports changing validation
 * error messages according to the current locale.
 * @param <T> The type of instance being edited.
 */
public class MultilingualValidationForm<T>
 extends Form
{
	private static final long serialVersionUID = 1L;
	
	private Class<T> beanClass;

  private static javax.validation.ValidatorFactory factory = 
  	javax.validation.Validation.buildDefaultValidatorFactory();

  private javax.validation.Validator validator;
  
  private String caption;
  
	public MultilingualValidationForm(Class<T> beanClass)
	{
		if (beanClass == null) 
			throw new IllegalArgumentException("Argument 'beanClass' must not be null.");
		
		this.beanClass = beanClass;
		this.validator = factory.getValidator();
	}

	@Override
	public void requestRepaint()
	{
		CoopApplication application = CoopApplicationServlet.getCurrentApplication();
		
		if (application != null)
			DataUtilities.setValidatorsLocale(this, application.getLocale());

		super.requestRepaint();
	}

	@Override
	public void addField(Object propertyId, Field field)
	{
		if (isReadOnly()) field.setReadOnly(true);
		
    Item item = getItemDataSource();
    
		if (propertyId instanceof String && item instanceof BeanItem)
		{
			String property = (String)propertyId;
			
			String[] pathElements = property.split("\\.");
			
			Class<?> holderType = ((BeanItem<?>)item).getBean().getClass();
			
			try
			{
			
				for (int i = 0; i < pathElements.length; i++)
				{
					String pathElement = pathElements[i];
					
					PropertyDescriptor descriptor = new PropertyDescriptor(pathElement, holderType);
					
					if (i < pathElements.length - 1)
					{
						holderType = descriptor.getPropertyType();
					}
					else
					{
				    BeanValidationValidator validator = addValidatorToField(field, pathElement, holderType);
				    
				    validator.setLocale(CoopApplicationServlet.getCurrentApplication().getLocale());
				    
				    if (Multilingual.class.isAssignableFrom(descriptor.getPropertyType()))
				    {
					    if (field.getIcon() == null)
					    {
					    	field.setIcon(new ThemeResource("../images/globe3.png"));
					    }
					    
					    field.addValidator(new MultilingualLiteralsValidator(getItemDataSource().getItemProperty(propertyId)));
				    }
					}
				}
			
			}
			catch (IntrospectionException ex)
			{
				super.addField(propertyId, field);
				return;
			}
    
		}

    super.addField(propertyId, field);
	}
	
	public Class<T> getBeanClass()
	{
		return beanClass;
	}

	@Override
	public void setItemDataSource(Item newDataSource, Collection<?> propertyIds)
	{
		ensureCompatibleDataSource(newDataSource);
		super.setItemDataSource(newDataSource, propertyIds);

		if (newDataSource != null)
			super.setCaption(caption);
		else
			super.setCaption(null);
	}

	@Override
	public void setItemDataSource(Item newDataSource)
	{
		ensureCompatibleDataSource(newDataSource);
		super.setItemDataSource(newDataSource);
		
		if (newDataSource != null)
			super.setCaption(caption);
		else
			super.setCaption(null);
	}
	
	private void ensureCompatibleDataSource(Item dataSource)
	{
		if (dataSource instanceof BeanItem)
		{
			@SuppressWarnings("unchecked")
			BeanItem<T> item = (BeanItem<T>)dataSource;
			if (!beanClass.isAssignableFrom(item.getBean().getClass()))
			{
				throw new IllegalArgumentException("Bean must be of type " + beanClass.getName());
			}
		}
	}
	
	private MultilingualBeanValidator addValidatorToField(
			Field field,
      Object propertyId, 
      Class<?> beanClass)
	{
    MultilingualBeanValidator validator = new MultilingualBeanValidator(
        beanClass, String.valueOf(propertyId));
    
    javax.validation.metadata.PropertyDescriptor constraintsForProperty = 
    	this.validator.getConstraintsForClass(beanClass).getConstraintsForProperty(propertyId.toString());
    
    if(constraintsForProperty != null)
    {
			int nonNotNullValidators = constraintsForProperty.getConstraintDescriptors().size();
			if (validator.isRequired())
			{
				field.setRequired(true);
				field.setRequiredError(validator.getRequiredMessage());
				nonNotNullValidators--;
			}
			if(nonNotNullValidators > 0)
			{
				field.addValidator(validator);
			}
		}
    
		return validator;
	}
	
	@Override
	public void setCaption(String caption)
	{
		this.caption = caption;
		
		if (this.getItemDataSource() != null)
			super.setCaption(caption);
		else
			requestRepaint();
	}
}
