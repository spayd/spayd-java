/**
 *  Copyright (c) 2012, Paylibo (www.paylibo.com).
 */
package com.paylibo.string;

import com.paylibo.account.BankAccount;

/**
 *
 * @author petrdvorak
 */
public class Paylibo {
    
    private static String protocolVersion = "1.0";
    
    public static String payliboStringFromAccount(BankAccount account, PayliboParameters parameters, PayliboMap extendedParameters) {
        String payliboString = "PAY/" + protocolVersion + "/";
        if (account.getIBAN() != null) {
            payliboString += "IBAN:" + account.getIBAN() + "/";
        }
        if (parameters.getBic() != null) {
            payliboString += "BIC:" + parameters.getBic() + "/";
        }
        if (parameters.getAmount() != null) {
            payliboString += "AM:" + parameters.getAmount() + "/";
        }
        if (parameters.getCurrency() != null) {
            payliboString += "CC:" + parameters.getCurrency() + "/";
        }
        if (parameters.getSendersReference() != null) {
            payliboString += "RF:" + parameters.getSendersReference() + "/";
        }
        if (parameters.getRecipientName() != null) {
            payliboString += "RN:" + parameters.getRecipientName() + "/";
        }
        if (parameters.getIdentifier() != null) {
            payliboString += "ID:" + parameters.getIdentifier() + "/";
        }
        if (parameters.getDate() != null) {
            payliboString += "DT:" + parameters.getDate() + "/";
        }
        if (parameters.getMessage() != null) {
            payliboString += "MSG:" + parameters.getMessage() + "/";
        }
        if (extendedParameters != null && !extendedParameters.isEmpty()) {
            payliboString += extendedParameters.toPayliboExtendedParams();
        }
        return payliboString.substring(0, payliboString.length() - 1);
    }
    
}
