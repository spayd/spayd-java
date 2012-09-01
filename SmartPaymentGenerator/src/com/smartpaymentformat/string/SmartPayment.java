/**
 *  Copyright (c) 2012, SmartPayment (www.SmartPayment.com).
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

    public static String paymentStringFromAccount(BankAccount account, SmartPaymentParameters parameters, SmartPaymentMap extendedParameters, boolean transliterateParams) {
        String paymentString = "PAY*" + protocolVersion + "*";
        if (account.getIBAN() != null) {
            paymentString += "ACC:" + account.getIBAN();
            if (parameters.getBic() != null) {
                paymentString += "+" + parameters.getBic();
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
        if (parameters.getIdentifier() != null) {
            paymentString += "ID:" + parameters.getIdentifier() + "*";
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
        if (extendedParameters != null && !extendedParameters.isEmpty()) {
            paymentString += (transliterateParams
                    ? Junidecode.unidecode(extendedParameters.toExtendedParams().toUpperCase())
                    : extendedParameters.toExtendedParams()).replaceAll("\\*", "%2A");
        }
        return paymentString.substring(0, paymentString.length() - 1);
    }
}
