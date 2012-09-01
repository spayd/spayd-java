/**
 * Copyright (c) 2012, Paylibo (www.paylibo.com).
 */
package com.paylibo.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author petrdvorak
 */
public class PayliboValidator {

    private static String[] allowedKeys = {
        "IBAN", "BIC", "AM",
        "CC", "RF", "RN",
        "ID", "DT", "PT",
        "MSG"};

    public static List<PayliboValidationError> validatePayliboString(String payliboString) {
        List<PayliboValidationError> errors = new LinkedList<PayliboValidationError>();
        if (!payliboString.matches("PAY\\*[0-9]+\\.[0-9]+\\*(/[0-9A-Z $%*+-.]+:[^*]+\\*)+/?")) {
            PayliboValidationError error = new PayliboValidationError();
            error.setErrorCode(PayliboValidationError.ERROR_INVALID_STRUCTURE);
            error.setErrorDescription("Paylibo code didn't pass the basic regexp validation.");
            errors.add(error);
            return errors;
        }

        List<String> allowedKeyList = Arrays.asList(allowedKeys);

        String[] components = payliboString.split("/");

        boolean ibanFound = false;

        // skip the header and version => start with 2
        for (int i = 2; i < components.length; i++) {
            int index = components[i].indexOf(":");
            String key = components[i].substring(0, index);
            String value = components[i].substring(index + 1);

            if (!allowedKeyList.contains(key) && !key.startsWith("X-")) {
                PayliboValidationError error = new PayliboValidationError();
                error.setErrorCode(PayliboValidationError.ERROR_INVALID_KEY_FOUND);
                error.setErrorDescription("Unknown key detected. Use 'X-' prefix to create your own key.");
                errors.add(error);
                continue;
            }

            if (key.equals("IBAN")) {
                ibanFound = true;
                if (!value.matches("^([A-Z]{2,2}[0-9]+)( ([A-Z]{2,2}[0-9]+))*$")) {
                    PayliboValidationError error = new PayliboValidationError();
                    error.setErrorCode(PayliboValidationError.ERROR_INVALID_IBAN);
                    error.setErrorDescription("IBAN number was not in the correct format.");
                    errors.add(error);
                }
            } else if (key.equals("AM")) {
                if (!value.matches("^[0-9]{0,10}(\\.[0-9]{0,2})?$") || value.equals(".")) {
                    PayliboValidationError error = new PayliboValidationError();
                    error.setErrorCode(PayliboValidationError.ERROR_INVALID_AMOUNT);
                    error.setErrorDescription("Amount must be a number with at most 2 decimal digits.");
                    errors.add(error);
                }
            } else if (key.equals("CC")) {
                try {
                    Currency.getInstance(value);
                } catch (IllegalArgumentException ex) {
                    PayliboValidationError error = new PayliboValidationError();
                    error.setErrorCode(PayliboValidationError.ERROR_INVALID_CURRENCY);
                    error.setErrorDescription("Currency must be a valid currency from ISO 4271.");
                    errors.add(error);
                }
            } else if (key.equals("RF")) {
                if (value.length() > 16 || value.length() < 1 || !value.matches("^[0-9]+$")) {
                    PayliboValidationError error = new PayliboValidationError();
                    error.setErrorCode(PayliboValidationError.ERROR_INVALID_SENDERS_REFERENCE);
                    error.setErrorDescription("Senders reference must be a decimal string with length between 1 and 16 characters.");
                    errors.add(error);
                }
            } else if (key.equals("RN")) {
                if (value.length() > 40 || value.length() < 1) {
                    PayliboValidationError error = new PayliboValidationError();
                    error.setErrorCode(PayliboValidationError.ERROR_INVALID_RECIPIENT_NAME);
                    error.setErrorDescription("Recipient name must be a string with length between 1 and 40 characters.");
                    errors.add(error);
                }
            } else if (key.equals("ID")) {
                if (value.length() > 10 || value.length() < 1) {
                    PayliboValidationError error = new PayliboValidationError();
                    error.setErrorCode(PayliboValidationError.ERROR_INVALID_INTERNAL_ID);
                    error.setErrorDescription("Internal ID must be a string with length between 1 and 10 characters.");
                    errors.add(error);
                }
            } else if (key.equals("DT")) {
                if (!value.matches("^[0-9]{8,8}$")) {
                    PayliboValidationError error = new PayliboValidationError();
                    error.setErrorCode(PayliboValidationError.ERROR_INVALID_DUE_DATE);
                    error.setErrorDescription("Due date must be represented as a decimal string in YYYYmmdd format.");
                    errors.add(error);
                } else {
                    SimpleDateFormat df = new SimpleDateFormat("yyyymmdd");
                    Date date;
                    try {
                        date = df.parse(value);
                        if (date == null) {
                            PayliboValidationError error = new PayliboValidationError();
                            error.setErrorCode(PayliboValidationError.ERROR_INVALID_DUE_DATE);
                            error.setErrorDescription("Due date must be represented as a decimal string in YYYYmmdd format.");
                            errors.add(error);
                        }
                    } catch (ParseException ex) {
                        PayliboValidationError error = new PayliboValidationError();
                        error.setErrorCode(PayliboValidationError.ERROR_INVALID_DUE_DATE);
                        error.setErrorDescription("Due date must be represented as a decimal string in YYYYmmdd format.");
                        errors.add(error);
                    }
                }
            } else if (key.equals("PT")) {
                if (value.length() > 3 || value.length() < 1) {
                    PayliboValidationError error = new PayliboValidationError();
                    error.setErrorCode(PayliboValidationError.ERROR_INVALID_PAYMENT_TYPE);
                    error.setErrorDescription("Payment type must be at represented as a string with length between 1 and 3 characters.");
                    errors.add(error);
                }
            } else if (key.equals("MSG")) {
                if (value.length() > 35 || value.length() < 1) {
                    PayliboValidationError error = new PayliboValidationError();
                    error.setErrorCode(PayliboValidationError.ERROR_INVALID_MESSAGE);
                    error.setErrorDescription("Message must be at represented as a string with length between 1 and 35 characters.");
                    errors.add(error);
                }
            }
        }
        if (!ibanFound) {
            PayliboValidationError error = new PayliboValidationError();
            error.setErrorCode(PayliboValidationError.ERROR_IBAN_NOT_FOUND);
            error.setErrorDescription("You must specify an account number.");
            errors.add(error);
        }
        return errors.isEmpty() ? null : errors;
    }
}
