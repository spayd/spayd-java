/**
 *  Copyright (c) 2012, Paylibo (www.paylibo.com).
 */
package com.paylibo.string;

import java.util.Date;
import net.sf.junidecode.Junidecode;

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
        if (bic == null) {
            this.bic = null;
        } else {
            this.bic = Junidecode.unidecode(bic).toUpperCase();;
        }
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
        if (identifier == null) {
            this.identifier = null;
        } else {
            this.identifier = Junidecode.unidecode(identifier).toUpperCase();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if (message == null) {
            this.message = null;
        } else {
            this.message =  Junidecode.unidecode(message).toUpperCase();
        }
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        if (recipientName == null) {
            this.recipientName = null;
        } else {
            this.recipientName = Junidecode.unidecode(recipientName).toUpperCase();
        }
    }

    public String getSendersReference() {
        return sendersReference;
    }

    public void setSendersReference(String sendersReference) {
        if (sendersReference == null) {
            this.sendersReference = null;
        } else {
            this.sendersReference = Junidecode.unidecode(sendersReference).toUpperCase();
        }
    }
    
}
