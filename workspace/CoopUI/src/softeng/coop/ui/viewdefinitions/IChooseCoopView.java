package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.ICoopContext;
import softeng.ui.vaadin.mvp.*;

public interface IChooseCoopView
	extends IView<BeanItemContainer<CoOp>, ICoopContext>
{
	public static class FilterOptions
	{
		private boolean showInactive;
		
		private boolean showOtherDepartments;
		
		private boolean showOtherUniversities;
		
		public FilterOptions()
		{
			this.showInactive = false;
			this.showOtherDepartments = false;
		}

		public void setShowInactive(boolean showInactive)
		{
			this.showInactive = showInactive;
		}

		public boolean isShowInactive()
		{
			return showInactive;
		}

		public boolean isShowOtherDepartments()
		{
			return showOtherDepartments;
		}

		public void setShowOtherDepartments(boolean showOtherDepartments)
		{
			this.showOtherDepartments = showOtherDepartments;
		}

		public boolean isShowOtherUniversities()
		{
			return showOtherUniversities;
		}

		public void setShowOtherUniversities(boolean showOtherUniversities)
		{
			this.showOtherUniversities = showOtherUniversities;
		}
	}
	
	void addQueryChangeListener(IListener<FilterOptions> listener);
	void removeQueryChangeListener(IListener<FilterOptions> listener);
	
	FilterOptions getFilterOptions();
	
	CoOp getSelectedCoOp();
	void setSelectedCoOp(CoOp coop);
	
	boolean allowCreateNew();
	void setAllowCreateNew(boolean allowCreateNew);
}
