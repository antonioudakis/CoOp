package softeng.validation.constraints;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.*;

@Size(min = 10, max = 16) //according to the E.164 numbering plan
@Pattern(regexp = "^(\\+?[0-9]+)?")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Documented

public @interface Telephone 
{
	  String message() default "{softeng.validation.constraints.Telephone.message}";

	  Class<?>[] groups() default {};

	  Class<? extends Payload>[] payload() default {};
}
