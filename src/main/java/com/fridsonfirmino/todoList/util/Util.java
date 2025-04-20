package com.fridsonfirmino.todoList.util;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Util {

    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = wrappedSource.getPropertyDescriptors();
        Set<String> nullPropertyNames = new HashSet<>();

        for (PropertyDescriptor pd : pds) {
            Object propertyName = wrappedSource.getPropertyValue(pd.getName());
            if (propertyName == null) {
                nullPropertyNames.add(pd.getName());
            }
        }

        String[] result = new String[nullPropertyNames.size()];       

        return nullPropertyNames.toArray(result);
    }
    
}
