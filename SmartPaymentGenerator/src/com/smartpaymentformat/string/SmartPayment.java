/**
 * Copyright (c) 2012, SmartPayment (www.SmartPayment.com).
 */
package com.smartpaymentformat.string;

import com.smartpaymentformat.account.BankAccount;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import net.sf.junidecode.Junidecode;

/**
 *
 * @author petrdvorak
 */
public class SmartPayment {

    private static String protocolVersion = "1.0";
    
    public static String escapeDisallowedCharacters(String originalString) throws UnsupportedEncodingException {
        String working = "";
        for (int i = 0; i < originalString.length(); i++) {
            if (originalString.charAt(i) > 127) { // escape non-ascii characters
                working += URLEncoder.encode("" + originalString.charAt(i), "UTF-8");
            } else {
                if (originalString.charAt(i) == '*') { // star is a special character for the SPAYD format
                    working += "%2A";
                } else if (originalString.charAt(i) == '+') { // plus is a special character for URL encode
                    working += "%2B";
                } else if (originalString.charAt(i) == '%') { // percent is an escape character
                    working += "%25";
                } else {
                    working += originalString.charAt(i); // ascii characters may be used as expected
                }
            }
        }
        return working;
    }

    public static String paymentStringFromAccount(SmartPaymentParameters parameters, SmartPaymentMap extendedParameters, boolean transliterateParams) throws UnsupportedEncodingException {
        String paymentString = "SPD*" + protocolVersion + "*";
        if (parameters.getBankAccount().getIBAN() != null) {
            paymentString += "ACC:" + parameters.getBankAccount().getIBAN();
            if (parameters.getBankAccount().getBIC() != null) {
                paymentString += "+" + parameters.getBankAccount().getBIC();
            }
            paymentString += "*";
        }
        if (parameters.getAlternateAccounts() != null && !parameters.getAlternateAccounts().isEmpty()) {
            paymentString += "ALT-ACC:";
            boolean firstItem = true;
            for (BankAccount bankAccount : parameters.getAlternateAccounts()) {
                if (!firstItem) {
                    paymentString += ",";
                } else {
                    firstItem = false;
                }
                paymentString += bankAccount.getIBAN();
                if (bankAccount.getBIC() != null) {
                    paymentString += "+" + bankAccount.getBIC();
                }
            }
            paymentString += "*";
        }
        if (parameters.getAmount() != null) {
            paymentString += "AM:" + parameters.getAmount() + "*";
        }
        if (parameters.getCurrency() != null) {
            paymentString += "CC:" + parameters.getCurrency() + "*";
        }
        if (parameters.getSendersReference() != null) {
            paymentString += "RF:" + parameters.getSendersReference() + "*";
        }
        if (parameters.getRecipientName() != null) {
            if (transliterateParams) {
                paymentString += "RN:" 
                        + SmartPayment.escapeDisallowedCharacters(Junidecode.unidecode(parameters.getRecipientName().toUpperCase()))
                        + "*";
            } else {
                paymentString += "RN:"
                        + SmartPayment.escapeDisallowedCharacters(parameters.getRecipientName())
                        + "*";
            }
        }
        if (parameters.getDate() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            paymentString += "DT:" + simpleDateFormat.format(parameters.getDate()) + "*";
        }
        if (parameters.getMessage() != null) {
            if (transliterateParams) {
                paymentString += "MSG:" 
                        + SmartPayment.escapeDisallowedCharacters(Junidecode.unidecode(parameters.getMessage().toUpperCase()))
                        + "*";
            } else {
                paymentString += "MSG:"
                        + SmartPayment.escapeDisallowedCharacters(parameters.getMessage())
                        + "*";
            }
        }
        if (parameters.getNotificationType() != null) {
            if (parameters.getNotificationType() == SmartPaymentParameters.PaymentNotificationType.email) {
                paymentString += "NT:E*";
            } else if (parameters.getNotificationType() == SmartPaymentParameters.PaymentNotificationType.phone) {
                paymentString += "NT:P*";
            }
        }
        if (parameters.getNotificationValue() != null) {
            paymentString += "NTA:" + SmartPayment.escapeDisallowedCharacters(parameters.getNotificationValue()) + "*";
        }
        if (extendedParameters != null && !extendedParameters.isEmpty()) {
            paymentString += extendedParameters.toExtendedParams();
        }
        return paymentString.substring(0, paymentString.length() - 1);
    }
}
