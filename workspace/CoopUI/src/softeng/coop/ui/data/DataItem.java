package softeng.coop.ui.data;

import java.util.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;

import softeng.coop.business.*;
import softeng.coop.dataaccess.*;
import softeng.ui.vaadin.data.NullableNestedProperty;

public class DataItem<T> extends BeanItem<T>
{
	public class MultilingualProperty 
	implements Property, Property.ValueChangeNotifier, Comparable<MultilingualProperty>
	{
		private static final long serialVersionUID = 1L;

		private Multilingual multilingual;
		
		private List<ValueChangeListener> valueChangeListeners = 
			new ArrayList<ValueChangeListener>();
		
		public MultilingualProperty(Multilingual multilingual)
		{
			if (multilingual == null) 
				throw new IllegalArgumentException("Argument 'multilingual' must not be null.");
			
			this.multilingual = multilingual;
		}
		
		public Multilingual getMultilingual()
		{
			return this.multilingual;
		}
		
		@Override
		public Object getValue()
		{
			return manager.getLiteral(multilingual);
		}

		@Override
		public void setValue(Object newValue) throws ReadOnlyException, ConversionException
		{
			if (newValue == null) 
				throw new IllegalArgumentException("Argument 'newValue' must not be null.");
			
			if (!manager.isWriteable())
				throw new ReadOnlyException();
			
			WriterManagerBase writerManager = (WriterManagerBase)manager;
			
			writerManager.setDefaultLiteral(multilingual, newValue.toString());
			
			fireValueChange();
		}

		@Override
		public Class<?> getType()
		{
			return String.class;
		}

		@Override
		public boolean isReadOnly()
		{
			return !manager.isWriteable();
		}

		@Override
		public void setReadOnly(boolean newStatus)
		{
			// NOP.
		}
		
		public @Override String toString()
		{
			Object value = this.getValue();
			if (value != null)
				return value.toString();
			else
				return "-";
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
		
		@SuppressWarnings("serial")
		private void fireValueChange()
		{
			final Property me = this;
			
			ValueChangeEvent event = new ValueChangeEvent()
			{
				@Override
				public Property getProperty()
				{
					return me;
				}
			};
			
			for (ValueChangeListener listener : this.valueChangeListeners)
			{
				listener.valueChange(event);
			}
		}

		@Override
		public int compareTo(MultilingualProperty o)
		{
			String thisString = this.toString();
			
			String otherString = null;
			
			if (o != null) 
				otherString = o.toString();
			else 
				return 1;
			
			return thisString.compareTo(otherString);
		}
	}

	private static final long serialVersionUID = 1L;
	
	private ManagerBase manager;
	
	private HashMap<Object, MultilingualProperty> multilingualsMap = 
		new HashMap<Object, MultilingualProperty>();
	
	public DataItem(T bean, ManagerBase manager)
	{
		super(bean);
		
		if (manager == null) 
			throw new IllegalArgumentException("Argument 'manager' must not be null.");
		
		this.manager = manager;
	}

	@Override
	public Property getItemProperty(Object id)
	{
		Property property = super.getItemProperty(id);
		
		if (property == null) return null;
		
		if (property.getType().equals(Multilingual.class))
		{
			MultilingualProperty multilingualProperty = this.multilingualsMap.get(id);
			
			if (multilingualProperty == null)
			{
				Multilingual multilingual = (Multilingual)property.getValue();
				
				if (multilingual == null) return property;
				
				multilingualProperty = new MultilingualProperty(multilingual);
				
				this.multilingualsMap.put(id, multilingualProperty);
			}
			
			return multilingualProperty;
		}
		else
		{
			return property;
		}
	}
	
	/**
	 * Adds a nested property for the item, e.g. "manager.address.street". 
	 * If any intermediate getters return null 
	 * values then the property becomes null and read-only.
	 * @return Returns false if the property already exists.
	 */
	public boolean addNullableNestedProperty(String propertyPath)
	{
		return addItemProperty(
				propertyPath, 
				new NullableNestedProperty(getBean(), propertyPath));
	}

}
