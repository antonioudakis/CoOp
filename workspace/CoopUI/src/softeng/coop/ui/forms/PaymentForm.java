package softeng.coop.ui.forms;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.data.MultilingualValidationForm;

public class PaymentForm
extends MultilingualValidationForm<Payment>
{
	private static final long serialVersionUID = 1L;

	public PaymentForm()
	{
		super(Payment.class);
	}
}
