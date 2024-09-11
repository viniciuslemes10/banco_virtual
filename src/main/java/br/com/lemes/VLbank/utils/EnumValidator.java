package br.com.lemes.VLbank.utils;

import br.com.lemes.VLbank.exceptions.ArgumentInvalidException;

import java.util.EnumSet;
import java.util.Set;

public class EnumValidator {
    public static <E extends Enum<E>> void checkIfEnumIsValid(E value, Class<E> enumType, String errorMessage) {
        if(value == null) {
            throw new ArgumentInvalidException(errorMessage + " cannot be null.");
        }

        Set<E> validValues = EnumSet.allOf(enumType);

        if(!validValues.contains(value)) {
            throw new ArgumentInvalidException(errorMessage + " is invalid: " + value);
        }
    }
}
