/**
 * Copyright (c) 2012, SmartPayment (www.SmartPayment.com).
 */
package com.smartpaymentformat.string;

import com.smartpaymentformat.account.BankAccount;
import java.text.SimpleDateFormat;
import net.sf.junidecode.Junidecode;

/**
 *
 * @author petrdvorak
 */
public class SmartPayment {

    private static String protocolVersion = "1.0";

    public static String paymentStringFromAccount(SmartPaymentParameters parameters, SmartPaymentMap extendedParameters, boolean transliterateParams) {
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
                paymentString += parameters.getBankAccount().getIBAN();
                if (parameters.getBankAccount().getBIC() != null) {
                    paymentString += "+" + parameters.getBankAccount().getBIC();
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
            paymentString += "RN:" + (transliterateParams
                    ? Junidecode.unidecode(parameters.getRecipientName().toUpperCase())
                    : parameters.getRecipientName()).replaceAll("\\*", "%2A") + "*";
        }
        if (parameters.getDate() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            paymentString += "DT:" + simpleDateFormat.format(parameters.getDate()) + "*";
        }
        if (parameters.getMessage() != null) {
            paymentString += "MSG:" + (transliterateParams
                    ? Junidecode.unidecode(parameters.getMessage().toUpperCase())
                    : parameters.getMessage()).replaceAll("\\*", "%2A") + "*";
        }
        if (parameters.getNotificationType() != null) {
            if (parameters.getNotificationType() == SmartPaymentParameters.PaymentNotificationType.email) {
                paymentString += "NT:E*";
            } else if (parameters.getNotificationType() == SmartPaymentParameters.PaymentNotificationType.phone) {
                paymentString += "NT:P*";
            }
        }
        if (parameters.getNotificationValue() != null) {
            paymentString += "NTA:" + parameters.getNotificationValue() + "*";
        }
        if (extendedParameters != null && !extendedParameters.isEmpty()) {
            paymentString += (transliterateParams
                    ? Junidecode.unidecode(extendedParameters.toExtendedParams().toUpperCase())
                    : extendedParameters.toExtendedParams()).replaceAll("\\*", "%2A");
        }
        return paymentString.substring(0, paymentString.length() - 1);
    }
}
