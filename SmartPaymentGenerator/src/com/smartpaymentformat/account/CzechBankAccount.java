/**
 *  Copyright (c) 2012, SmartPayment (www.SmartPayment.com).
 */
package com.smartpaymentformat.account;

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
    
    public CzechBankAccount(String prefix, String number, String bankCode) {
        this.prefix = prefix;
        this.number = number;
        this.bankCode = bankCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) throws IllegalArgumentException {
        for (int i = 0; i < bankCode.length(); i++) {
            if (bankCode.charAt(i) < 0 && bankCode.charAt(i) > 9) {
                throw new IllegalArgumentException("Czech account number (bank code) must be numeric.");
            }
        }
        this.bankCode = bankCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) throws IllegalArgumentException {
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) < 0 && number.charAt(i) > 9) {
                throw new IllegalArgumentException("Czech account number (basic part) must be numeric.");
            }
        }
        this.number = number;
    }

    public String getPrefix() {
        if (prefix == null) return "000000";
        return prefix;
    }

    public void setPrefix(String prefix) throws IllegalArgumentException {
        for (int i = 0; i < prefix.length(); i++) {
            if (prefix.charAt(i) < 0 && prefix.charAt(i) > 9) {
                throw new IllegalArgumentException("Czech account number (prefix) must be numeric.");
            }
        }
        this.prefix = prefix;
    }

    @Override
    public void setIBAN(String iban) {
        this.iban = iban;
        this.bankCode = iban.substring(4, 8);
        this.number   = iban.substring(16, 26);
        this.prefix   = iban.substring(9, 15);
    }
    
    @Override
    public String getIBAN() {
        return IBANUtilities.computeIBANFromCzechBankAccount(this);
    }
    
}
