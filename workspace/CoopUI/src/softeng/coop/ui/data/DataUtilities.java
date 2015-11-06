package softeng.coop.ui.data;

import java.util.*;

import java.beans.*;
import java.lang.reflect.Method;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.ui.vaadin.data.HierarchicalBeanItemContainer;

import com.vaadin.addon.beanvalidation.*;
import com.vaadin.data.*;
import com.vaadin.data.util.*;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.ui.*;

public class DataUtilities
{
	private static NotNullMultilingualValidator notNullMultilingualValidator =
		new NotNullMultilingualValidator();
	
	/**
	 * Focuses a field's window and triggers a 
	 * field value update when the field's window is focused. 
	 */
	@SuppressWarnings("serial")
	public static class FocusValueSetter implements FieldEvents.FocusListener
	{
		private AbstractField field;
		
		private Object value;
		
		public FocusValueSetter(AbstractField field, Object value)
		{
			if (field == null) 
				throw new IllegalArgumentException("Argument 'field' must not be null.");
			
			if (field.getWindow() == null)
				throw new IllegalArgumentException("Argument 'field' should have been added to a window.");
			
			this.field = field;
			this.value = value;
			
			field.getWindow().addListener(this);
			
			field.getWindow().focus();
		}

		@Override
		public void focus(FocusEvent event)
		{
			field.setValue(value);
			
			if (field.getWindow() != null)
			{
				field.getWindow().removeListener(this);
			}
		}
		
	}
	
	/**
	 * Switch the locale of the validators of a JSR-303 validation-aware form.
	 * @param <T> The type of the bean.
	 * @param form The validating form. 
	 * @param locale The locale to switch.
	 */
	public static void setValidatorsLocale(Form form, Locale locale)
	{
		if (form == null) 
			throw new IllegalArgumentException("Argument 'form' must not be null.");
		if (locale == null) 
			throw new IllegalArgumentException("Argument 'locale' must not be null.");
		
		if (form.getValidators() != null)
		{
			for (Validator validator : form.getValidators())
			{
				if (validator instanceof BeanValidationValidator)
				{
					BeanValidationValidator beanValidator = 
						(BeanValidationValidator)validator;
					
					beanValidator.setLocale(locale);
				}
			}
		}
		
		if (form.getItemPropertyIds() != null)
		{
			for (Object propertyId : form.getItemPropertyIds())
			{
				Field field = form.getField(propertyId);
				
				if (field == null) continue;
				
				if (field.isRequired() && field.getValidators() == null)
				{
					field.addValidator(notNullMultilingualValidator);
				}
				
				if (field.getValidators() == null) continue;
				
				for (Validator validator : field.getValidators())
				{
					if (validator instanceof BeanValidationValidator)
					{
						BeanValidationValidator beanValidator = 
							(BeanValidationValidator)validator;
						
						beanValidator.setLocale(locale);
						
						if (beanValidator.isRequired())
							field.setRequiredError(beanValidator.getRequiredMessage());
					}
					else if (validator instanceof NotNullMultilingualValidator)
					{
						NotNullMultilingualValidator notNullValidator = 
							(NotNullMultilingualValidator)validator;

						field.setRequiredError(notNullValidator.getMessage());
						field.setRequired(true);
					}
				}
			}
		}
	}
	
	/**
	 * Re-bind a select component in order to refresh its multilingual
	 * content.
	 */
	public static void updateMultilingual(AbstractSelect component)
	{
		if (component == null) 
			throw new IllegalArgumentException("Argument 'component' must not be null.");
		
		boolean wasReadOnly = component.isReadOnly();
		
		if (wasReadOnly) component.setReadOnly(false);
		
		Container container = component.getContainerDataSource();
		Object selectedValue = component.getValue();
		
		component.setContainerDataSource(null);
		component.setContainerDataSource(container);
		
		component.setValue(selectedValue);
		
		if (wasReadOnly) component.setReadOnly(true);
	}
	
	/**
	 * @param <M> The type of object being wrapped as BeanItem.
	 * @param item The item.
	 * @param context The application context.
	 */
	public static <M> void  revertItem(BeanItem<M> item, ICoopContext context)
	{
		if (context == null) 
			throw new IllegalArgumentException("Argument 'context' must not be null.");
		
		if (item == null) return;
		
		M entity = item.getBean();
		
		if (entity == null) return;
		
		if (!context.getSession().containsEntity(entity)) return;
		
		// First, reload the object from database.
		context.getSession().refreshEntity(entity);
		
		// Second, notify all BeanItem<M> listeners that the 
		// fields have been reverted to their original values.

		try
		{
			BeanInfo info = 
				java.beans.Introspector.getBeanInfo(entity.getClass());
			
			PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
			
			for (final PropertyDescriptor descriptor : propertyDescriptors)
			{
				Property property = item.getItemProperty(descriptor.getName());
				
				if (property == null) continue;

				try
				{
					Method getter = descriptor.getReadMethod();
					
					if (getter == null) continue;
					
					property.setValue(getter.invoke(entity));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			
		}
		catch (IntrospectionException e)
		{
			throw new CoopUISystemException("Could not reflect entity.", e);
		}

	}
	
	/**
	 * Expand a tree up to a selected item.
	 * @param <M> The type if objects in the tree hierarchy.
	 * @param tree The tree, which is expected to be bound to container.
	 * @param container The container bound to the tree.
	 * @param selectedItem The selected item to expand up to.
	 */
	@SuppressWarnings({ "unchecked" })
	public static <M> void expandTree(
			final Tree tree, 
			HierarchicalBeanItemContainer<M> container, 
			final BeanItem<M> selectedItem)
	{
		if (tree == null) 
			throw new IllegalArgumentException("Argument 'tree' must not be null.");
		
		if (container != null && selectedItem != null)
		{
			for (int repetitions = 0; repetitions < 2; repetitions++)
			{
				// Preselect the current value.
				List<M> path = new ArrayList<M>();
				
				for (
						M pathElement = selectedItem.getBean(); 
						pathElement != null; 
						pathElement = (M)container.getParent(pathElement))
				{
					path.add(pathElement);
				}
				
				for (int i = path.size() - 1; i >= 0; i--)
				{
					tree.expandItem(path.get(i));
				}
				
				new FocusValueSetter(tree, selectedItem.getBean());
			}
		}
		else
		{
			tree.setValue(null);
		}
	}
	
	/**
	 * Fill a combo box with the values of an enumeration type.
	 * @param <T> The enumeration type.
	 * @param comboBox The combo box bound to the enumeration type.
	 * @param enumType The enumeration type class.
	 */
	public static <T> void fillComboBox(ComboBox comboBox, Class<T> enumType)
	{
		if (comboBox == null) 
			throw new IllegalArgumentException("Argument 'comboBox' must not be null.");
		
		if (enumType == null) 
			throw new IllegalArgumentException("Argument 'enumType' must not be null.");
		
		T[] values = enumType.getEnumConstants();
		
		if (values == null)
			throw new IllegalArgumentException("Argument 'enumType' is not an enumeration type.");
		
		List<T> valuesList = new ArrayList<T>(values.length);
		
		for (int i = 0; i < values.length; i++)
		{
			valuesList.add(values[i]);
		}
		
		BeanItemContainer<T> container = new BeanItemContainer<T>(enumType, valuesList);
		
		comboBox.setContainerDataSource(container);
	}
	
	public static ResourceBundle getResourceBundleForEnum(Class<?> enumType, Locale locale)
	{
		if (enumType == null) 
			throw new IllegalArgumentException("Argument 'enumType' must not be null.");
		
		if (locale == null) 
			throw new IllegalArgumentException("Argument 'locale' must not be null.");
		
		if (enumType.getEnumConstants() == null)
			throw new IllegalArgumentException("Argument 'enumType' is not an enumeration type.");
		
		if (enumType.getPackage() == null) return null;
		
		if (!enumType.getPackage().getName().equals("softeng.coop.dataaccess")) return null;
		
		try
		{
			String resourceName = String.format(
					"%s.enums.%s", 
					CoopApplication.class.getPackage().getName(), 
					enumType.getSimpleName());
			
			return ResourceBundle.getBundle(resourceName, locale);
		}
		catch (RuntimeException ex)
		{
			return null;
		}
	}
	
	/**
	 * Returns true if the student has supplied all the extra fields required. 
	 */
	public static boolean studentHasCompleteProvision(Student student)
	{
		return !(
			student.getEmail() == null || 
			student.getIdNumber() == null ||
			student.getFatherName() == null ||
			student.getMotherName() == null ||
			student.getDateOfBirth() == null || 
			student.getAddresses().size() < 1 ||
			student.getTelephones().size() < 2 ||
			student.getGender() == null ||
			student.getIdType() == null ||
			student.getIdIssuer() == null || 
			student.getIdIssueDate() == null ||
			student.getTaxId() == null ||
			student.getTaxDivision() == null ||
			student.getSocialSecurityId() == null || 
			student.getIban() == null
			);
	}

	/**
	 * Create a combo box filled with year values.
	 */
	public static ComboBox createYearComboBox()
	{
		ComboBox yearComboBox = new ComboBox();
		
		Calendar now = Calendar.getInstance();
		
		int thisYear = now.get(Calendar.YEAR);
		
		for (int year = thisYear + 3; year >= thisYear - 25; year--)
		{
			yearComboBox.addItem(year);
		}
		
		return yearComboBox;
	}
	
}
