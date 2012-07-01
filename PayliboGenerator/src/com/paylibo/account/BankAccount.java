/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paylibo.account;

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
