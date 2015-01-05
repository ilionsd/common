package com.insign.common.linearalgebra.exceptions;

/**
 * Created by ilion on 05.01.2015.
 */
public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(Object parameter, String condition) {
        super("Parameter " + parameter + "does not satisfy condition:\"" + condition + "\"");
    }
}
