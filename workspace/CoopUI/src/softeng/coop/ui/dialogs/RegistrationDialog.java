package softeng.coop.ui.dialogs;

import java.util.*;

import com.vaadin.data.util.*;
import com.vaadin.ui.*;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.ui.vaadin.mvp.*;

public class RegistrationDialog extends CoopDialog<BeanItem<Student>>
	implements IRegistrationView
{
	private static final long serialVersionUID = 1L;
	
	private Table table;
	
	private DataItemContainer<CoOp> coopsContainer;
	
	private List<String> visibleColumns;
	
	public RegistrationDialog()
	{
		visibleColumns = new ArrayList<String>();
		
		visibleColumns.add("academicYear");
		visibleColumns.add("semester");
		visibleColumns.add("name");
		
		table = new Table();
	}

	public RegistrationDialog(String caption)
	{
		this();
		setCaption(caption);
	}

	@Override
	public void dataBind()
	{
	}

	@Override
	public DataItemContainer<CoOp> getCoopsContainer()
	{
		return this.coopsContainer;
	}

	@Override
	public void setCoopsContainer(DataItemContainer<CoOp> container)
	{
		this.coopsContainer = container;
		
		this.coopsContainer.setContainerPropertyIds(this.visibleColumns);
		
		this.table.setContainerDataSource(container);
		
		if (container != null)
		{
			this.table.setVisibleColumns(visibleColumns.toArray());
		}
	}

	@Override
	public CoOp getSelectedCoOp()
	{
		return (CoOp)this.table.getValue();
	}

	@Override
	protected Presenter<BeanItem<Student>, ICoopContext, ? extends IView<BeanItem<Student>, ICoopContext>> supplyPresenter()
	{
		return new RegistrationPresenter(this);
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		setClosable(false);
		getOkCancelView().setReadOnly(false);
		
		//table.setWidth("100%");
		//table.setHeight("100%");
		//this.getContent().setSizeFull();
		
		table.setSelectable(true);
		addComponent(table);
		
		table.setSizeFull();
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		this.setupTableColumnCaptions();
		
		if (getCaption() == null || getCaption().isEmpty())
			setCaption(getLocalizedString("CAPTION"));
	}
	
	private void setupTableColumnCaptions()
	{
		table.setColumnHeader("academicYear", getLocalizedString("ACADEMIC_YEAR_CAPTION"));
		table.setColumnHeader("semester", getLocalizedString("SEMESTER_CAPTION"));
		table.setColumnHeader("name", getLocalizedString("NAME_CAPTION"));
	}

}
