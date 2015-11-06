package softeng.validation.constraints;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.*;

@Size(min = 5, max = 5)
@Pattern(regexp = "[0-9]+")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Documented
public @interface PostalCode
{
  String message() default "{softeng.validation.constraints.PostalCode.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
