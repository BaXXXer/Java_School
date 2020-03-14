package edu.tsystems.javaschool.logapp.api.validator.truck;


import edu.tsystems.javaschool.logapp.api.entity.Truck;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class TruckFormValidator implements Validator {
    public boolean supports(Class<?> aClass) {
        return Truck.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"regNumber","regNum.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"capacityTons","capacity.required");

        if(((Truck) o).getCapacityTons()<=0){
            errors.rejectValue("capacityTons","negative Value",new Object[]{"'capacityTons'"}, "capacity can't be negative");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"condition","condition.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"cityId","city id is required");

    }
}
