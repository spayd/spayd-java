/**
 *  Copyright (c) 2012, SmartPayment (www.SmartPayment.com).
 */
package com.smartpaymentformat.account;

/**
 *
 * @author petrdvorak
 */
public abstract class BankAccount {
    
    protected String iban;

    public BankAccount() {
    }

    public BankAccount(String iban) {
        this.iban = iban;
    }
    
    public abstract String getIBAN();
    public abstract void setIBAN(String iban);
    
}
