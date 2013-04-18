/**
 *  Copyright (c) 2012, SPAYD (www.spayd.org).
 */
package org.spayd.model.account;

import org.spayd.utilities.IBANUtilities;

/**
 *
 * @author petrdvorak
 */
public class CzechBankAccount extends BankAccount {

    private String prefix;
    private String number;
    private String bankCode;

    public CzechBankAccount() {
    }

    private boolean validateMod11(String number) {
        int weight = 1;
        int sum = 0;
        for (int k = number.length() - 1; k >= 0; k--) {
            sum += (number.charAt(k) - '0') * weight;
            weight *= 2;
        }

        if ((sum % 11) != 0) {
            return false;
        }

        return true;
    }

    public CzechBankAccount(String prefix, String number, String bankCode) {
        validateAccountParameters(prefix, number, bankCode);
        this.prefix = prefix;
        this.number = number;
        this.bankCode = bankCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) throws IllegalArgumentException {
        validateAccountParameters(null, null, bankCode);
        this.bankCode = bankCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) throws IllegalArgumentException {
        validateAccountParameters(null, number, null);
        this.number = number;
    }

    public String getPrefix() {
        if (prefix == null) {
            return "000000";
        }
        return prefix;
    }

    public void setPrefix(String prefix) throws IllegalArgumentException {
        validateAccountParameters(prefix, null, null);
        this.prefix = prefix;
    }

    @Override
    public void setIBAN(String iban) {
        this.iban = iban;
        this.bankCode = iban.substring(4, 8);
        this.number = iban.substring(14, 24);
        this.prefix = iban.substring(8, 14);
    }

    @Override
    public String getIBAN() {
        return IBANUtilities.computeIBANFromCzechBankAccount(this);
    }

    @Override
    public String getBIC() {
        return bic;
    }

    @Override
    public void setBIC(String bic) {
        this.bic = bic;
    }

    private void validateAccountParameters(String prefix, String number, String bankCode) throws IllegalArgumentException {
        if (prefix != null) {
            for (int i = 0; i < prefix.length(); i++) {
                if (prefix.charAt(i) < 0 && prefix.charAt(i) > 9) {
                    throw new IllegalArgumentException("Czech account number (prefix) must be numeric.");
                }
            }
            if (!this.validateMod11(prefix)) {
                throw new IllegalArgumentException("Czech account number (prefix) must pass bank mod 11 test.");
            }
        }
        if (number != null) {
            for (int i = 0; i < number.length(); i++) {
                if (number.charAt(i) < 0 && number.charAt(i) > 9) {
                    throw new IllegalArgumentException("Czech account number (basic part) must be numeric.");
                }
            }
            if (!this.validateMod11(number)) {
                throw new IllegalArgumentException("Czech account number (basic part) must pass bank mod 11 test.");
            }
        }
        if (bankCode != null) {
            for (int i = 0; i < bankCode.length(); i++) {
                if (bankCode.charAt(i) < 0 && bankCode.charAt(i) > 9) {
                    throw new IllegalArgumentException("Czech account number (bank code) must be numeric.");
                }
            }
        }
    }
}
