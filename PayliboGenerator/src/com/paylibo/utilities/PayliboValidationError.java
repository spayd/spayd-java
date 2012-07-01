/**
 *  Copyright (c) 2012, Paylibo (www.paylibo.com).
 */
package com.paylibo.utilities;

/**
 *
 * @author petrdvorak
 */
public class PayliboValidationError {
    private String errorCode;
    private String errorDescription;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
    
}
