package softeng.coop.ui.composites;

import com.vaadin.data.util.*;

import softeng.coop.business.*;
import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.ui.vaadin.mvp.*;

public class ProfessorPickerField
	extends PickerField<Professor>
{
	private static final long serialVersionUID = 1L;
	
	public ProfessorPickerField()
	{
		super("surname");
	}
	
	public ProfessorPickerField(String caption)
	{
		this();
		setCaption(caption);
	}

	@Override
	public Class<?> getType()
	{
		return Professor.class;
	}

	@Override
	protected IModalView<BeanItem<Professor>> showBrowseForm(BeanItem<Professor> item)
	{
		if (!isReadOnly())
		{
			ChooseProfessorDialog dialog = new ChooseProfessorDialog();
			
			dialog.setModal(true);
			
			getApplication().getMainWindow().addWindow(dialog);
			
			dialog.setModel(item);
			dialog.dataBind();

			return dialog;
		}
		else
		{
			if (item == null) return null;
			
			PersonDialog<Professor> dialog = new PersonDialog<Professor>();

			
			dialog.setModal(true);
			
			getApplication().getMainWindow().addWindow(dialog);
			
			dialog.setModel(item);
			dialog.dataBind();

			return dialog;
		}
	}

	@Override
	protected Presenter<Professor, ICoopContext, ? extends IView<Professor, ICoopContext>> supplyPresenter()
	{
		return null;
	}

	@Override
	protected BeanItem<Professor> createBeanItem(Professor value)
	{
		if (value == null) return null;
		
		if (getContext() != null)
		{
			Session session = getContext().getSession();
			
			if (session != null)
			{
				return new DataItem<Professor>(value, session.getBaseManager());
			}
		}

		return super.createBeanItem(value);
	}
}
