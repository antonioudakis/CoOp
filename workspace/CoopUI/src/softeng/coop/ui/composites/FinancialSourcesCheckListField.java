package softeng.coop.ui.composites;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;

@SuppressWarnings("serial")
public class FinancialSourcesCheckListField
extends CheckListField<FinancialSource>
{
	private CoOp coop;
	
	public FinancialSourcesCheckListField()
	{
		super(createColumnSpecifications());
	}

	private static List<ColumnSpecification> createColumnSpecifications()
	{
		ArrayList<ColumnSpecification> list = new ArrayList<ColumnSpecification>();
		
		list.add(new ColumnSpecification("name", "NAME_CAPTION"));
		list.add(new ColumnSpecification("code", "CODE_CAPTION"));
		
		return list;
	}

	@Override
	public Class<?> getType()
	{
		return FinancialSource.class;
	}

	public void setCoop(CoOp coop)
	{
		this.coop = coop;
	}

	public CoOp getCoop()
	{
		return coop;
	}

	@Override
	protected void addToParent(FinancialSource element)
	{
		if (coop == null) return;
		
		Session session = getContext().getSession();
		
		if (!session.isLoaded(element, "coOps")) return;
		
		element.getCoOps().add(coop);
	}

	@Override
	protected void removeFromParent(FinancialSource element)
	{
		if (coop == null) return;
		
		Session session = getContext().getSession();
		
		if (!session.isLoaded(element, "coOps")) return;
		
		element.getCoOps().remove(coop);
	}

}
