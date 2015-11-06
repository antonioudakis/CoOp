package softeng.coop.business;

import java.math.BigDecimal;

import softeng.coop.business.payments.PaymentAggregate;
import softeng.coop.dataaccess.FinancialSource;
import softeng.coop.dataaccess.Student;

public class PaymentAggregateImpl
	extends PaymentAggregate
{
	public PaymentAggregateImpl(BigDecimal totalAmount)
	{
		if (totalAmount == null) totalAmount = new BigDecimal(0);

		this.setTotalAmount(totalAmount);
	}
	
	public PaymentAggregateImpl(BigDecimal totalAmount, Student student /*, FinancialSource source*/)
	{
		this(totalAmount);
		
		this.setStudent(student);
		//this.setSource(source);
	}
	
	public PaymentAggregateImpl(BigDecimal totalAmount, Student student, FinancialSource source)
	{
		this(totalAmount);
		
		this.setStudent(student);
		this.setSource(source);
	}
	
	public PaymentAggregateImpl(BigDecimal totalAmount, FinancialSource source)
	{
		this(totalAmount);
		
		this.setSource(source);
	}
}
