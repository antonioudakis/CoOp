package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.presenters.*;

import softeng.ui.vaadin.mvp.*;

public class InsuranceContractsTableComponent
extends TableComponent<CoOp, InsuranceContract>
{
	private static final long serialVersionUID = 1L;
	
	public InsuranceContractsTableComponent()
	{
		super(createDefaultColumnSpecifications());
	}

	private static List<ColumnSpecification> createDefaultColumnSpecifications()
	{
		ArrayList<ColumnSpecification> list = new ArrayList<TableComponent.ColumnSpecification>();
		
		list.add(new ColumnSpecification("name", "NAME_CAPTION"));
		
		return list;
	}

	@Override
	public Class<?> getType()
	{
		return InsuranceContract.class;
	}

	@Override
	protected IModalView<BeanItem<InsuranceContract>> showAddForm(BeanItem<InsuranceContract> item)
	{
		return showForm(item);
	}

	@Override
	protected IModalView<BeanItem<InsuranceContract>> showEditForm(BeanItem<InsuranceContract> item)
	{
		return showForm(item);
	}
	
	private IModalView<BeanItem<InsuranceContract>> showForm(BeanItem<InsuranceContract> item)
	{
		InsuranceContractDialog dialog = new InsuranceContractDialog();
		
		dialog.setModal(true);
		dialog.setModel(item);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected InsuranceContract createNewElement()
	{
		InsuranceContract contract = new InsuranceContract();
		
		contract.setAttachments(new LinkedHashSet<Attachment>());

		return contract;
	}

	@Override
	protected Presenter<Collection<InsuranceContract>, ICoopContext, ? extends IView<Collection<InsuranceContract>, ICoopContext>> supplyPresenter()
	{
		return new InsuranceContractsTablePresenter(this);
	}

	@Override
	protected void addToParent(InsuranceContract item)
	{
		item.setCoop(getParentModel());
	}

	@Override
	protected void removeFromParent(InsuranceContract item)
	{
		item.setCoop(null);
	}
}
