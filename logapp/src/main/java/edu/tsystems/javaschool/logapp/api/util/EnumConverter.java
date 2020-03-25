package edu.tsystems.javaschool.logapp.api.util;

import edu.tsystems.javaschool.logapp.api.entity.Driver;

public class EnumConverter {

    public static Driver.Status convertDriverStatusToEnum(String status){
        Driver.Status[] values = Driver.Status.values();
        for(Driver.Status st: values){
            if(st.toString().equals(status)){
                return st;
            }
        }
        return null;
    }
}
