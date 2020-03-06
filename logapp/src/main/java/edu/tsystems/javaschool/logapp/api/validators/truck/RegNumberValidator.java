package edu.tsystems.javaschool.logapp.api.validators.truck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegNumberValidator implements ConstraintValidator<TruckRegNumber,String> {
    public boolean isValid(String regNum, ConstraintValidatorContext constraintValidatorContext) {
        if(regNum == null){
            return false;
        }
        if(regNum.matches("[A-Z]{2}\\d{5}")){
            return true;
        }
        return false;
    }

    public void initialize(TruckRegNumber constraintAnnotation) {

    }
}
