/**
 *  Copyright (c) 2012, SPAYD (www.spayd.org).
 */
package org.spayd.string;

import java.util.Date;
import java.util.List;
import org.spayd.model.account.BankAccount;

/**
 *
 * @author petrdvorak
 */
public class SpaydPaymentAttributes {
    
    private BankAccount bankAccount;
    private List<BankAccount> alternateAccounts;
    private Number amount;
    private String currency;
    private String sendersReference;
    private String recipientName;
    private Date   date;
    private String paymentType;
    private String message;
    private PaymentNotificationType notificationType;
    private String notificationValue;
    private String crc32;

    public enum PaymentNotificationType {
        email,
        phone
    }

    public List<BankAccount> getAlternateAccounts() {
        return alternateAccounts;
    }

    public void setAlternateAccounts(List<BankAccount> alternateAccounts) {
        this.alternateAccounts = alternateAccounts;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Number getAmount() {
        return amount;
    }

    public void setAmount(Number amount) {
        this.amount = amount;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentNotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(PaymentNotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationValue() {
        return notificationValue;
    }

    public void setNotificationValue(String notificationValue) {
        this.notificationValue = notificationValue;
    }
    
    public String getCrc32() {
        return crc32;
    }

    public void setCrc32(String crc32) {
        this.crc32 = crc32;
    }
    
}
