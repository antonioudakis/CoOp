package softeng.coop.ui.viewdefinitions.viewmodels;

import java.math.*;
import java.util.*;

import javax.validation.constraints.*;

import softeng.coop.dataaccess.*;

/**
 * Model for IFinancialCardView.
 */
public class FinancialActionViewModel
{
	@NotNull
	@DecimalMin("0.0")
	private BigDecimal localDayCompensation;
	
	@NotNull
	@DecimalMin("0.0")
	private BigDecimal remoteDayCompensation;
	
	@NotNull
	@DecimalMin("0.0")
	private BigDecimal offCountryDayCompensation;
	
	@NotNull
	private PaymentType paymentType;
	
	@NotNull
	private FinancialSource financialSource;
	
	@NotNull
	private PaymentState paymentState;
	
	@NotNull
	private FinancialActionType actionType;
	
	@NotNull
	private Date paymentDate;
	
	public FinancialActionViewModel()
	{
		paymentState = PaymentState.Planned;
		paymentType = PaymentType.FullPayment;
		
		localDayCompensation = new BigDecimal(0.0);
		remoteDayCompensation = new BigDecimal(0.0);
		offCountryDayCompensation = new BigDecimal(0.0);
		
		paymentDate = new Date();
	}

	public BigDecimal getLocalDayCompensation()
	{
		return localDayCompensation;
	}

	public void setLocalDayCompensation(BigDecimal localDayCompensation)
	{
		this.localDayCompensation = localDayCompensation;
	}

	public BigDecimal getRemoteDayCompensation()
	{
		return remoteDayCompensation;
	}

	public void setRemoteDayCompensation(BigDecimal remoteDayCompensation)
	{
		this.remoteDayCompensation = remoteDayCompensation;
	}

	public BigDecimal getOffCountryDayCompensation()
	{
		return offCountryDayCompensation;
	}

	public void setOffCountryDayCompensation(BigDecimal offCountryDayCompensation)
	{
		this.offCountryDayCompensation = offCountryDayCompensation;
	}

	public PaymentType getPaymentType()
	{
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType)
	{
		this.paymentType = paymentType;
	}

	public FinancialSource getFinancialSource()
	{
		return financialSource;
	}

	public void setFinancialSource(FinancialSource financialSource)
	{
		this.financialSource = financialSource;
	}

	public PaymentState getPaymentState()
	{
		return paymentState;
	}

	public void setPaymentState(PaymentState paymentState)
	{
		this.paymentState = paymentState;
	}

	public FinancialActionType getActionType()
	{
		return actionType;
	}

	public void setActionType(FinancialActionType actionType)
	{
		this.actionType = actionType;
	}

	public void setPaymentDate(Date paymentDate)
	{
		this.paymentDate = paymentDate;
	}

	public Date getPaymentDate()
	{
		return paymentDate;
	}
}
