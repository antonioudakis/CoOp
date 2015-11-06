package softeng.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.sourceforge.wife.validation.util.IBAN;

public class IbanValidator implements ConstraintValidator<Iban, String>
{

	private IBAN myIban;
	
	@Override
	public void initialize(Iban constraintAnnotation) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) 
	{
		if (value == null || value.equals("")) return true;
		myIban = new IBAN(value);
		
		return myIban.isValid();
	}
	
}
//public class IbanValidator implements ConstraintValidator<Iban, Object>
//{
//	private IBAN myiban;
//	
//	public IbanValidator(Object iban) 
//	{
//		myiban = new IBAN((String)iban);
//	}
//
//	@Override
//	public void initialize(Iban constraintAnnotation) 
//	{
////		myiban.setIban(constraintAnnotation.)
//		
//	}
//
//	@Override
//	public boolean isValid(Object value, ConstraintValidatorContext context) 
//	{	
//		if (value == null) return true;
//		myiban.setIban((String)value);
//		
//		return myiban.isValid();
//	}
//
//
//}
