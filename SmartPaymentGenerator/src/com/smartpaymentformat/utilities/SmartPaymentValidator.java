/**
 * Copyright (c) 2012, SmartPayment (www.SmartPayment.com).
 */
package com.smartpaymentformat.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author petrdvorak
 */
public class SmartPaymentValidator {

    private static String[] allowedKeys = {
        "ACC",
        "ALT-ACC",
        "AM",
        "CC",
        "RF",
        "RN",
        "DT",
        "PT",
        "MSG",
        "NT",
        "NTA",
        "CRC32"};

    public static List<SmartPaymentValidationError> validatePaymentString(String paymentString) {
        List<SmartPaymentValidationError> errors = new LinkedList<SmartPaymentValidationError>();
        
        if (!paymentString.matches("^SPD\\*[0-9]+\\.[0-9]+\\*.*")) {
            SmartPaymentValidationError error = new SmartPaymentValidationError();
            error.setErrorCode(SmartPaymentValidationError.ERROR_NOT_SPAYD);
            error.setErrorDescription("Invalid data prefix - SPD*{$VERSION}* expected.");
            errors.add(error);
            return errors;
        }
        
        if (!paymentString.matches("^SPD\\*[0-9]+\\.[0-9]+(\\*[0-9A-Z $%*+-.]+:[^\\*]+)+\\*?$")) {
            SmartPaymentValidationError error = new SmartPaymentValidationError();
            error.setErrorCode(SmartPaymentValidationError.ERROR_INVALID_STRUCTURE);
            error.setErrorDescription("Payment String code didn't pass the basic regexp validation.");
            errors.add(error);
            return errors;
        }

        List<String> allowedKeyList = Arrays.asList(allowedKeys);

        String[] components = paymentString.split("\\*");

        boolean ibanFound = false;

        // skip the header and version => start with 2
        for (int i = 2; i < components.length; i++) {
            int index = components[i].indexOf(":");
            String key = components[i].substring(0, index);
            String value = components[i].substring(index + 1);

            if (!allowedKeyList.contains(key) && !key.startsWith("X-")) {
                SmartPaymentValidationError error = new SmartPaymentValidationError();
                error.setErrorCode(SmartPaymentValidationError.ERROR_INVALID_KEY_FOUND);
                error.setErrorDescription("Unknown key detected. Use 'X-' prefix to create your own key.");
                errors.add(error);
                continue;
            }

            if (key.equals("ACC")) {
                ibanFound = true;
                if (!value.matches("^([A-Z]{2,2}[0-9]+)(\\+([A-Z0-9]+))?$")) {
                    SmartPaymentValidationError error = new SmartPaymentValidationError();
                    error.setErrorCode(SmartPaymentValidationError.ERROR_INVALID_IBAN);
                    error.setErrorDescription("IBAN+BIC pair was not in the correct format.");
                    errors.add(error);
                }
            } else if (key.equals("ALT-ACC")) {
                ibanFound = true;
                if (!value.matches("^([A-Z]{2,2}[0-9]+)(\\+([A-Z0-9]+))?(,([A-Z]{2,2}[0-9]+)(\\+([A-Z0-9]+))?)*$")) {
                    SmartPaymentValidationError error = new SmartPaymentValidationError();
                    error.setErrorCode(SmartPaymentValidationError.ERROR_INVALID_ALTERNATE_IBAN);
                    error.setErrorDescription("Alternate accounts are not properly formatted - should be IBAN+BIC list with items separated by ',' character.");
                    errors.add(error);
                }
            } else if (key.equals("AM")) {
                if (!value.matches("^[0-9]{0,10}(\\.[0-9]{0,2})?$") || value.equals(".")) {
                    SmartPaymentValidationError error = new SmartPaymentValidationError();
                    error.setErrorCode(SmartPaymentValidationError.ERROR_INVALID_AMOUNT);
                    error.setErrorDescription("Amount must be a number with at most 2 decimal digits.");
                    errors.add(error);
                }
            } else if (key.equals("CC")) {
                try {
                    Currency.getInstance(value);
                } catch (IllegalArgumentException ex) {
                    SmartPaymentValidationError error = new SmartPaymentValidationError();
                    error.setErrorCode(SmartPaymentValidationError.ERROR_INVALID_CURRENCY);
                    error.setErrorDescription("Currency must be a valid currency from ISO 4271.");
                    errors.add(error);
                }
            } else if (key.equals("RF")) {
                if (value.length() > 16 || value.length() < 1 || !value.matches("^[0-9]+$")) {
                    SmartPaymentValidationError error = new SmartPaymentValidationError();
                    error.setErrorCode(SmartPaymentValidationError.ERROR_INVALID_SENDERS_REFERENCE);
                    error.setErrorDescription("Senders reference must be a decimal string with length between 1 and 16 characters.");
                    errors.add(error);
                }
            } else if (key.equals("RN")) {
                if (value.length() > 40 || value.length() < 1) {
                    SmartPaymentValidationError error = new SmartPaymentValidationError();
                    error.setErrorCode(SmartPaymentValidationError.ERROR_INVALID_RECIPIENT_NAME);
                    error.setErrorDescription("Recipient name must be a string with length between 1 and 40 characters.");
                    errors.add(error);
                }
            } else if (key.equals("NT")) {
                if (!value.equals("E") && !value.equals("P")) {
                    SmartPaymentValidationError error = new SmartPaymentValidationError();
                    error.setErrorCode(SmartPaymentValidationError.ERROR_INVALID_NOTIFICATION_TYPE);
                    error.setErrorDescription("Notification type must be 'E' (e-mail) or 'P' (phone).");
                    errors.add(error);
                }
            } else if (key.equals("DT")) {
                if (!value.matches("^[0-9]{8,8}$")) {
                    SmartPaymentValidationError error = new SmartPaymentValidationError();
                    error.setErrorCode(SmartPaymentValidationError.ERROR_INVALID_DUE_DATE);
                    error.setErrorDescription("Due date must be represented as a decimal string in YYYYmmdd format.");
                    errors.add(error);
                } else {
                    SimpleDateFormat df = new SimpleDateFormat("yyyymmdd");
                    Date date;
                    try {
                        date = df.parse(value);
                        if (date == null) {
                            SmartPaymentValidationError error = new SmartPaymentValidationError();
                            error.setErrorCode(SmartPaymentValidationError.ERROR_INVALID_DUE_DATE);
                            error.setErrorDescription("Due date must be represented as a decimal string in YYYYmmdd format.");
                            errors.add(error);
                        }
                    } catch (ParseException ex) {
                        SmartPaymentValidationError error = new SmartPaymentValidationError();
                        error.setErrorCode(SmartPaymentValidationError.ERROR_INVALID_DUE_DATE);
                        error.setErrorDescription("Due date must be represented as a decimal string in YYYYmmdd format.");
                        errors.add(error);
                    }
                }
            } else if (key.equals("PT")) {
                if (value.length() > 3 || value.length() < 1) {
                    SmartPaymentValidationError error = new SmartPaymentValidationError();
                    error.setErrorCode(SmartPaymentValidationError.ERROR_INVALID_PAYMENT_TYPE);
                    error.setErrorDescription("Payment type must be at represented as a string with length between 1 and 3 characters.");
                    errors.add(error);
                }
            } else if (key.equals("MSG")) {
                if (value.length() > 35 || value.length() < 1) {
                    SmartPaymentValidationError error = new SmartPaymentValidationError();
                    error.setErrorCode(SmartPaymentValidationError.ERROR_INVALID_MESSAGE);
                    error.setErrorDescription("Message must be at represented as a string with length between 1 and 35 characters.");
                    errors.add(error);
                }
            }
        }
        if (!ibanFound) {
            SmartPaymentValidationError error = new SmartPaymentValidationError();
            error.setErrorCode(SmartPaymentValidationError.ERROR_IBAN_NOT_FOUND);
            error.setErrorDescription("You must specify an account number.");
            errors.add(error);
        }
        return errors.isEmpty() ? null : errors;
    }
}
