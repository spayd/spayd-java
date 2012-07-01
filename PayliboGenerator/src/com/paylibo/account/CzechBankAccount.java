/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paylibo.account;

import com.sun.media.sound.InvalidFormatException;

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

    public void setBankCode(String bankCode) throws InvalidFormatException {
        for (int i = 0; i < bankCode.length(); i++) {
            if (bankCode.charAt(i) < 0 && bankCode.charAt(i) > 9) {
                throw new InvalidFormatException("Czech account number (bank code) must be numeric.");
            }
        }
        this.bankCode = bankCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) throws InvalidFormatException {
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) < 0 && number.charAt(i) > 9) {
                throw new InvalidFormatException("Czech account number (basic part) must be numeric.");
            }
        }
        this.number = number;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) throws InvalidFormatException {
        for (int i = 0; i < prefix.length(); i++) {
            if (prefix.charAt(i) < 0 && prefix.charAt(i) > 9) {
                throw new InvalidFormatException("Czech account number (prefix) must be numeric.");
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
