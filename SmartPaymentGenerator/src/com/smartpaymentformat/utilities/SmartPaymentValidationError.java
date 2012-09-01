/**
 *  Copyright (c) 2012, SmartPayment (www.SmartPayment.com).
 */
package com.smartpaymentformat.utilities;

/**
 *
 * @author petrdvorak
 */
public class SmartPaymentValidationError {
    
    private String errorCode;
    private String errorDescription;
    
    public static String ERROR_INVALID_STRUCTURE            = "ERROR_INVALID_STRUCTURE";
    public static String ERROR_INVALID_AMOUNT               = "ERROR_INVALID_AMOUNT";
    public static String ERROR_INVALID_CURRENCY             = "ERROR_INVALID_CURRENCY";
    public static String ERROR_INVALID_SENDERS_REFERENCE    = "ERROR_INVALID_SENDERS_REFERENCE";
    public static String ERROR_INVALID_RECIPIENT_NAME       = "ERROR_INVALID_RECIPIENT_NAME";
    public static String ERROR_INVALID_INTERNAL_ID          = "ERROR_INVALID_INTERNAL_ID";
    public static String ERROR_INVALID_DUE_DATE             = "ERROR_INVALID_DUE_DATE";
    public static String ERROR_INVALID_PAYMENT_TYPE         = "ERROR_INVALID_PAYMENT_TYPE";
    public static String ERROR_INVALID_MESSAGE              = "ERROR_INVALID_MESSAGE";
    public static String ERROR_INVALID_KEY_FOUND            = "ERROR_INVALID_KEY_FOUND";
    public static String ERROR_INVALID_IBAN                 = "ERROR_INVALID_IBAN";
    public static String ERROR_IBAN_NOT_FOUND               = "ERROR_IBAN_NOT_FOUND";
    public static String ERROR_REQUEST_GENERIC              = "ERROR_REQUEST_GENERIC";

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
