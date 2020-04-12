package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DriverUpdateDTO {
    public Integer id;
    public String firstName;
    public String lastName;
    public String personalCode;
    public int hoursWorked;
    public Status status;
    public int cityId;
    public enum Status {
        REST,CARGO_HANDLING, REST_ON_SHIFT,DRIVING,CO_DRIVER}

}
