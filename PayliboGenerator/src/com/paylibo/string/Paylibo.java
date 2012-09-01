/**
 *  Copyright (c) 2012, Paylibo (www.paylibo.com).
 */
package com.paylibo.string;

import com.paylibo.account.BankAccount;
import java.text.SimpleDateFormat;
import net.sf.junidecode.Junidecode;

/**
 *
 * @author petrdvorak
 */
public class Paylibo {
    
    private static String protocolVersion = "1.0";
    
    public static String payliboStringFromAccount(BankAccount account, PayliboParameters parameters, PayliboMap extendedParameters, boolean transliterateParams) {
        String payliboString = "PAY*" + protocolVersion + "*";
        if (account.getIBAN() != null) {
            payliboString += "ACC:" + account.getIBAN();
            if (parameters.getBic() != null) {
                payliboString += "+" + parameters.getBic();
            }
            payliboString += "*";
        }
        if (parameters.getAmount() != null) {
            payliboString += "AM:" + parameters.getAmount() + "*";
        }
        if (parameters.getCurrency() != null) {
            payliboString += "CC:" + parameters.getCurrency() + "*";
        }
        if (parameters.getSendersReference() != null) {
            payliboString += "RF:" + parameters.getSendersReference() + "*";
        }
        if (parameters.getRecipientName() != null) {
            payliboString += "RN:" + (transliterateParams?
                                            Junidecode.unidecode(parameters.getRecipientName().toUpperCase())
                                            :parameters.getRecipientName()) + "*";
        }
        if (parameters.getIdentifier() != null) {
            payliboString += "ID:" + parameters.getIdentifier() + "*";
        }
        if (parameters.getDate() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            payliboString += "DT:" + simpleDateFormat.format(parameters.getDate()) + "*";
        }
        if (parameters.getMessage() != null) {
            payliboString += "MSG:" + (transliterateParams?
                                            Junidecode.unidecode(parameters.getMessage().toUpperCase())
                                            :parameters.getMessage()) + "*";
        }
        if (extendedParameters != null && !extendedParameters.isEmpty()) {
            payliboString += extendedParameters.toPayliboExtendedParams();
        }
        return payliboString.substring(0, payliboString.length() - 1);
    }
    
}
