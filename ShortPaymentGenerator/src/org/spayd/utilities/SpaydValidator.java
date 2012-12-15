/**
 *  Copyright (c) 2012, SPAYD (www.spayd.org).
 */
package org.spayd.utilities;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.spayd.model.error.SpaydValidationError;

/**
 *
 * @author petrdvorak
 */
public class SpaydValidator {

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

    public static List<SpaydValidationError> validatePaymentString(String paymentString) throws UnsupportedEncodingException {
        List<SpaydValidationError> errors = new LinkedList<SpaydValidationError>();
        
        if (!Charset.forName("ISO-8859-1").newEncoder().canEncode(paymentString)) { // check encoding
            SpaydValidationError error = new SpaydValidationError();
            error.setErrorCode(SpaydValidationError.ERROR_INVALID_CHARSET);
            error.setErrorDescription("Invalid charset - only ISO-8859-1 characters must be used");
            errors.add(error);
            return errors;
        } 
        
        if (!paymentString.matches("^SPD\\*[0-9]+\\.[0-9]+\\*.*")) {
            SpaydValidationError error = new SpaydValidationError();
            error.setErrorCode(SpaydValidationError.ERROR_NOT_SPAYD);
            error.setErrorDescription("Invalid data prefix - SPD*{$VERSION}* expected.");
            errors.add(error);
            return errors;
        }
        
        if (!paymentString.matches("^SPD\\*[0-9]+\\.[0-9]+(\\*[0-9A-Z $%*+-.]+:[^\\*]+)+\\*?$")) {
            SpaydValidationError error = new SpaydValidationError();
            error.setErrorCode(SpaydValidationError.ERROR_INVALID_STRUCTURE);
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
            if (index == -1) { // missing pair between two stars ("**")
                SpaydValidationError error = new SpaydValidationError();
                error.setErrorCode(SpaydValidationError.ERROR_INVALID_STRUCTURE);
                error.setErrorDescription("Payment String code didn't pass the basic regexp validation.");
                errors.add(error);
                continue;
            }
            String key = components[i].substring(0, index);
            String value = URLDecoder.decode(components[i].substring(index + 1).replaceAll("\\+", "%2B"), "UTF-8");
                    
            if (!allowedKeyList.contains(key) && !key.startsWith("X-")) {
                SpaydValidationError error = new SpaydValidationError();
                error.setErrorCode(SpaydValidationError.ERROR_INVALID_KEY_FOUND);
                error.setErrorDescription("Unknown key detected. Use 'X-' prefix to create your own key.");
                errors.add(error);
                continue;
            }

            if (key.equals("ACC")) {
                ibanFound = true;
                if (!value.matches("^([A-Z]{2,2}[0-9]+)(\\+([A-Z0-9]+))?$")) {
                    SpaydValidationError error = new SpaydValidationError();
                    error.setErrorCode(SpaydValidationError.ERROR_INVALID_IBAN);
                    error.setErrorDescription("IBAN+BIC pair was not in the correct format.");
                    errors.add(error);
                }
            } else if (key.equals("ALT-ACC")) {
                ibanFound = true;
                if (!value.matches("^([A-Z]{2,2}[0-9]+)(\\+([A-Z0-9]+))?(,([A-Z]{2,2}[0-9]+)(\\+([A-Z0-9]+))?)*$")) {
                    SpaydValidationError error = new SpaydValidationError();
                    error.setErrorCode(SpaydValidationError.ERROR_INVALID_ALTERNATE_IBAN);
                    error.setErrorDescription("Alternate accounts are not properly formatted - should be IBAN+BIC list with items separated by ',' character.");
                    errors.add(error);
                }
            } else if (key.equals("AM")) {
                if (!value.matches("^[0-9]{0,10}(\\.[0-9]{0,2})?$") || value.equals(".")) {
                    SpaydValidationError error = new SpaydValidationError();
                    error.setErrorCode(SpaydValidationError.ERROR_INVALID_AMOUNT);
                    error.setErrorDescription("Amount must be a number with at most 2 decimal digits.");
                    errors.add(error);
                }
            } else if (key.equals("CC")) {
                try {
                    Currency.getInstance(value);
                } catch (IllegalArgumentException ex) {
                    SpaydValidationError error = new SpaydValidationError();
                    error.setErrorCode(SpaydValidationError.ERROR_INVALID_CURRENCY);
                    error.setErrorDescription("Currency must be a valid currency from ISO 4271.");
                    errors.add(error);
                }
            } else if (key.equals("RF")) {
                if (value.length() > 16 || value.length() < 1 || !value.matches("^[0-9]+$")) {
                    SpaydValidationError error = new SpaydValidationError();
                    error.setErrorCode(SpaydValidationError.ERROR_INVALID_SENDERS_REFERENCE);
                    error.setErrorDescription("Senders reference must be a decimal string with length between 1 and 16 characters.");
                    errors.add(error);
                }
            } else if (key.equals("RN")) {
                if (value.length() > 40 || value.length() < 1) {
                    SpaydValidationError error = new SpaydValidationError();
                    error.setErrorCode(SpaydValidationError.ERROR_INVALID_RECIPIENT_NAME);
                    error.setErrorDescription("Recipient name must be a string with length between 1 and 40 characters.");
                    errors.add(error);
                }
            } else if (key.equals("NT")) {
                if (!value.equals("E") && !value.equals("P")) {
                    SpaydValidationError error = new SpaydValidationError();
                    error.setErrorCode(SpaydValidationError.ERROR_INVALID_NOTIFICATION_TYPE);
                    error.setErrorDescription("Notification type must be 'E' (e-mail) or 'P' (phone).");
                    errors.add(error);
                }
            } else if (key.equals("DT")) {
                if (!value.matches("^[0-9]{8,8}$")) {
                    SpaydValidationError error = new SpaydValidationError();
                    error.setErrorCode(SpaydValidationError.ERROR_INVALID_DUE_DATE);
                    error.setErrorDescription("Due date must be represented as a decimal string in YYYYmmdd format.");
                    errors.add(error);
                } else {
                    SimpleDateFormat df = new SimpleDateFormat("yyyymmdd");
                    Date date;
                    try {
                        date = df.parse(value);
                        if (date == null) {
                            SpaydValidationError error = new SpaydValidationError();
                            error.setErrorCode(SpaydValidationError.ERROR_INVALID_DUE_DATE);
                            error.setErrorDescription("Due date must be represented as a decimal string in YYYYmmdd format.");
                            errors.add(error);
                        }
                    } catch (ParseException ex) {
                        SpaydValidationError error = new SpaydValidationError();
                        error.setErrorCode(SpaydValidationError.ERROR_INVALID_DUE_DATE);
                        error.setErrorDescription("Due date must be represented as a decimal string in YYYYmmdd format.");
                        errors.add(error);
                    }
                }
            } else if (key.equals("PT")) {
                if (value.length() > 3 || value.length() < 1) {
                    SpaydValidationError error = new SpaydValidationError();
                    error.setErrorCode(SpaydValidationError.ERROR_INVALID_PAYMENT_TYPE);
                    error.setErrorDescription("Payment type must be at represented as a string with length between 1 and 3 characters.");
                    errors.add(error);
                }
            } else if (key.equals("MSG")) {
                if (value.length() > 60 || value.length() < 1) {
                    SpaydValidationError error = new SpaydValidationError();
                    error.setErrorCode(SpaydValidationError.ERROR_INVALID_MESSAGE);
                    error.setErrorDescription("Message must be at represented as a string with length between 1 and 60 characters.");
                    errors.add(error);
                }
            }
        }
        if (!ibanFound) {
            SpaydValidationError error = new SpaydValidationError();
            error.setErrorCode(SpaydValidationError.ERROR_IBAN_NOT_FOUND);
            error.setErrorDescription("You must specify an account number.");
            errors.add(error);
        }
        return errors.isEmpty() ? null : errors;
    }
}
