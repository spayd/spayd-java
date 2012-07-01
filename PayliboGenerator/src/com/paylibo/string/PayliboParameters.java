/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paylibo.string;

import java.util.Date;

/**
 *
 * @author petrdvorak
 */
public class PayliboParameters {
    
    private String bic;
    private Number amount;
    private String currency;
    private String sendersReference;
    private String recipientName;
    private String identifier;
    private Date   date;
    private String message;

    public Number getAmount() {
        return amount;
    }

    public void setAmount(Number amount) {
        this.amount = amount;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getSendersReference() {
        return sendersReference;
    }

    public void setSendersReference(String sendersReference) {
        this.sendersReference = sendersReference;
    }
    
}
