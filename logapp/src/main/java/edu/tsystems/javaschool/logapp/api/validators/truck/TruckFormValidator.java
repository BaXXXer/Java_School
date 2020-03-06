package edu.tsystems.javaschool.logapp.api.validators.truck;


import edu.tsystems.javaschool.logapp.api.entities.Truck;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class TruckFormValidator implements Validator {
    public boolean supports(Class<?> aClass) {
        return Truck.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"regNumber","regNum.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"capacityKg","capacity.required");

        if(((Truck) o).getCapacityKg()<=0){
            errors.rejectValue("capacityKg","negative Value",new Object[]{"'capacityKg'"}, "capacity can't be negative");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"condition","condition.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"cityId","city id is required");

    }
}
