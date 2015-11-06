package softeng.coop.dataaccess.validation;

import java.lang.annotation.*;

import javax.validation.*;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { softeng.coop.dataaccess.validation.MultilingualLengthValidator.class} )
@Documented
public @interface MultilingualLength
{
  String message() default "{softeng.coop.dataaccess.validation.MultilingualLength.message}";
  
  /**
   * The minimum size required for all literals of the multilingual field.
   * Default is zero.
   */
  int min() default 0;
  
  /**
   * The maximum size allowed for all literals of the multilingual field.
   * Default is 255.
   */
  int max() default 255;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
