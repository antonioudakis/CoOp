package softeng.coop.dataaccess.validation;

import javax.validation.*;

import softeng.coop.dataaccess.*;

public class MultilingualLengthValidator
implements ConstraintValidator<MultilingualLength, Multilingual>
{
	private MultilingualLength annotation;

	@Override
	public void initialize(MultilingualLength constraintAnnotation)
	{
		this.annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(Multilingual value, ConstraintValidatorContext context)
	{
		if (value == null || value.getLiterals() == null) return true;
		
		if (value.getLiterals().size() == 0) return true;
		
		int minLength = Integer.MAX_VALUE;
		int maxLength = Integer.MIN_VALUE;
		
		for (Literal literal : value.getLiterals())
		{
			String text = literal.getText();
			
			int textLength = text.length();
			
			if (textLength < minLength) minLength = textLength;
			if (textLength > maxLength) maxLength = textLength;
		}
		
		return maxLength <= annotation.max() && minLength >= annotation.min();
	}

}
