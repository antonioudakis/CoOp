package softeng.validation.constraints;

import java.lang.annotation.*;

import javax.validation.*;

//@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
//@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = {softeng.validation.constraints.MyIbanValidator.class})
//@ReportAsSingleViolation
//@Documented

@Documented
//@Constraint(validatedBy = softeng.validation.constraints.MyIbanValidator.class)
@Constraint(validatedBy = {softeng.validation.constraints.IbanValidator.class})
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Iban
{
  String message() default "{softeng.validation.constraints.Iban.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
  
  //String[] value() default {};
}