package softeng.coop.ui.viewdefinitions;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.util.*;

public interface ISelectable<M>
{
	void addAddingListener(IListener<ModelEvent<M>> listener);
	void removeAddingListener(IListener<ModelEvent<M>> listener);
	
	void addEditingListener(IListener<ModelEvent<M>> listener);
	void removeEditingListener(IListener<ModelEvent<M>> listener);
	
	void addDeletingListener(IListener<ModelEvent<M>> listener);
	void removeDeletingListener(IListener<ModelEvent<M>> listener);
	
	void addSelectedChangeListener(IListener<ModelEvent<M>> listener);
	void removeSelectedChangeListener(IListener<ModelEvent<M>> listener);
	
	boolean isReadOnly();
	void setReadOnly(boolean readOnly);
	
	M getSelectedValue();
	void setSelectedValue(M value);
	
	BeanItem<M> getSelectedItem();

	public boolean isEditVisible();
	public void setEditVisible(boolean visible);

	public boolean isAddVisible();
	public void setAddVisible(boolean visible);
	
	public boolean isDeleteVisible();
	public void setDeleteVisible(boolean visible);
}
