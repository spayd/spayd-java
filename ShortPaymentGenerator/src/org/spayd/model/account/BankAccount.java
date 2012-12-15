/**
 *  Copyright (c) 2012, SPAYD (www.spayd.org).
 */
package org.spayd.model.account;

/**
 *
 * @author petrdvorak
 */
public abstract class BankAccount {
    
    protected String iban;
    protected String bic;

    public BankAccount() {
    }

    public BankAccount(String iban) {
        this.iban = iban;
    }

    public BankAccount(String iban, String bic) {
        this.iban = iban;
        this.bic = bic;
    }
    
    public abstract String getIBAN();
    public abstract void setIBAN(String iban);
    public abstract String getBIC();
    public abstract void setBIC(String bic);
    
}
