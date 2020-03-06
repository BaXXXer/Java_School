package edu.tsystems.javaschool.logapp.api.validators.truck;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RegNumberValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TruckRegNumber {
    String message() default "{RegNumber}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
