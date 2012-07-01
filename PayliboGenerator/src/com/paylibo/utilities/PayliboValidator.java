/**
 *  Copyright (c) 2012, Paylibo (www.paylibo.com).
 */
package com.paylibo.utilities;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author petrdvorak
 */
public class PayliboValidator {
    
    public static List<PayliboValidationError> validatePayliboString(String payliboString) {
        List<PayliboValidationError> errors = new LinkedList<PayliboValidationError>();
        if (!payliboString.matches("PAY/[0-9]+\\.[0-9]+(/[0-9A-Z $%*+-.]+:[0-9A-Z $%*+-.:]*)+")) {
            PayliboValidationError error = new PayliboValidationError();
            error.setErrorCode(PayliboValidationError.ERROR_INVALID_STRUCTURE);
            error.setErrorDescription("Paylibo code didn't pass the basic regexp validation.");
            errors.add(error);
        }
        return errors.isEmpty()?null:errors;
    }
    
}
