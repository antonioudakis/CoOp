package softeng.validation.constraints;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.*;

@Pattern(regexp = "[a-zA-Z_0-9\\.!#$%&'\\*+-/=?^_`\\{|\\}~]+@[a-zA-Z_0-9\\.]+\\.[a-zA-Z_0-9]+")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Documented
public @interface Email
{
  String message() default "{softeng.validation.constraints.Email.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
