package softeng.validation.constraints;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.*;

@Min(value = 1900)
@Max(value = 2200)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Documented
public @interface Year
{
  String message() default "{softeng.validation.constraints.Year.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
