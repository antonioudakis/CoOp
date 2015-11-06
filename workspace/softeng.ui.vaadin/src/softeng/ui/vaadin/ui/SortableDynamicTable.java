package softeng.ui.vaadin.ui;

import java.util.*;

import softeng.ui.vaadin.data.*;

import com.vaadin.data.*;
import com.vaadin.ui.*;

/**
 * A Table which supports sorting on its dynamically generated columns.
 * Must be used with a container implementing the GenericSortableContainer interface in order
 * to support sorting of dynamic columns.  
 */
@SuppressWarnings("serial")
public class SortableDynamicTable
extends Table
{
	private static class GeneratedColumnComparator
	implements Comparator<Object>
	{
		private HashMap<Object, Object> generatedColumnsMap;
		
		private Table table;
		
		private Object columnId;
		
		private ColumnGenerator columnGenerator;
		
		private boolean ascending;
		
		public GeneratedColumnComparator(Table table, Object columnId, boolean ascending)
		{
			if (table == null) 
				throw new IllegalArgumentException("Argument 'table' must not be null.");
			if (columnId == null) 
				throw new IllegalArgumentException("Argument 'columnId' must not be null.");
			
			this.table = table;
			this.columnId = columnId;
			this.columnGenerator = table.getColumnGenerator(columnId);
			
			if (this.columnGenerator == null) 
				throw new IllegalArgumentException("Argument 'columnId' does not correspond to a dynamic column generator.");
			
			this.ascending = ascending;
			this.generatedColumnsMap = new HashMap<Object, Object>(this.table.size());
		}

		@Override
		public int compare(Object object1, Object object2)
		{
			if (ascending) return compareAscending(object1, object2);
			else return -compareAscending(object1, object2);
		}
		
		@SuppressWarnings("unchecked")
		private int compareAscending(Object object1, Object object2)
		{
			if (object1 == null)
			{
				return object2 != null ? -1 : 0;
			}
			else if (object2 == null)
			{
				return 1;
			}
			else
			{
				Object columnValue1 = getColumnValue(object1);
				
				if (!(columnValue1 instanceof Comparable<?>)) return 0;
				
				Object columnValue2 = getColumnValue(object2);
				
				return ((Comparable<Object>)columnValue1).compareTo(columnValue2);
			}
		}
		
		private Object getColumnValue(Object itemId)
		{
			if (generatedColumnsMap.containsKey(itemId)) return generatedColumnsMap.get(itemId);
			
			Object value = columnGenerator.generateCell(table, itemId, columnId);
			
			generatedColumnsMap.put(itemId, value);
			
			return value;
		}
		
	}
	
	private static Set<Object> emptyIdSet = Collections.unmodifiableSet(new HashSet<Object>(0));
	
	private Set<Object> generatedIds = new HashSet<Object>();
	
	public SortableDynamicTable()
	{
		
	}
	
	public SortableDynamicTable(String caption)
	{
		super(caption);
	}
	
	@Override
  public Collection<?> getSortableContainerPropertyIds() 
  {
		final Container c = getContainerDataSource();
		
		HashSet<Object> propertyIds;
		
		if (!isSortDisabled())
		{
			if (c instanceof Container.Sortable) 
			{
				Container.Sortable sortableContainer = (Container.Sortable)c;
				
				propertyIds = 
					new HashSet<Object>(sortableContainer.getSortableContainerPropertyIds().size() + generatedIds.size());
				
				((Container.Sortable) c).getSortableContainerPropertyIds();
				
				propertyIds.addAll(sortableContainer.getSortableContainerPropertyIds());
				propertyIds.addAll(generatedIds);
			} 
			else 
			{
				propertyIds = 
					new HashSet<Object>(generatedIds);
			}
			
			return propertyIds;
		}
		else
		{
			return emptyIdSet;
		}
  }

	@Override
	public void addGeneratedColumn(Object id, ColumnGenerator generatedColumn)
	{
		super.addGeneratedColumn(id, generatedColumn);
		
		generatedIds.add(id);
	}

	@Override
	public boolean removeGeneratedColumn(Object columnId)
	{
		boolean removed = super.removeGeneratedColumn(columnId);
		
		if (removed) generatedIds.remove(columnId);
		
		return removed;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void sort(Object[] propertyIds, boolean[] ascending) throws UnsupportedOperationException
	{
		// Is propertyIds a single property and mapped to a generated column?
		if (propertyIds.length == 1)
		{
			Object propertyId = propertyIds[0];
			
			if (generatedIds.contains(propertyId) && getContainerDataSource() instanceof IGenericSortableContainer<?>)
			{
				ColumnGenerator columnGenerator = getColumnGenerator(propertyId);
				
				if (columnGenerator != null)
				{
					IGenericSortableContainer<Object> sortableContainer = (IGenericSortableContainer<Object>)getContainerDataSource();
					
					Comparator<Object> comparator = 
						new GeneratedColumnComparator(this, propertyId, ascending[0]);
					
					sortableContainer.sort(comparator);
					
					return;
				}
			}
		}
		
		super.sort(propertyIds, ascending);
	}
	
}
