package softeng.validation.constraints;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.*;

@Size(min = 11, max = 11) // AMKA has always 11 digits.
@Pattern(regexp = "^(\\+?[0-9]+)?") // Digits only.
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Documented

public @interface Amka
{
  String message() default "{softeng.validation.constraints.Amka.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
