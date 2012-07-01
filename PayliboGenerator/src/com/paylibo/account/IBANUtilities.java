/**
 *  Copyright (c) 2012, Paylibo (www.paylibo.com).
 */
package com.paylibo.account;

/**
 *
 * @author petrdvorak
 */
class IBANUtilities {

    /**
     * Computes the IBAN number from a given Czech account information.
     * @param account A Czech account model class.
     * @return An IBAN number.
     */
    public static String computeIBANFromCzechBankAccount(CzechBankAccount account) {
        // preprocess the numbers
        String prefix = String.format("%06d", Long.valueOf(account.getPrefix()));
        String number = String.format("%010d", Long.valueOf(account.getNumber()));
        String bank = String.format("%04d", Long.valueOf(account.getBankCode()));
        
        // calculate the check sum
        String buf = bank + prefix + number + "123500";
        int index = 0;
        String dividend;
        int pz = -1;
        while (index <= buf.length()) {
            if (pz < 0) {
                dividend = buf.substring(index, Math.min(index + 9, buf.length()));
                index += 9;
            } else if (pz >= 0 && pz <= 9) {
                dividend = pz + buf.substring(index, Math.min(index + 8, buf.length()));
                index += 8;
            } else {
                dividend = pz + buf.substring(index, Math.min(index + 7, buf.length()));
                index += 7;
            }
            pz = Integer.valueOf(dividend) % 97;
        }
        pz = 98 - pz;
        
        // assign the checksum
        String checksum = String.format("%02d", pz);
        
        // build the IBAN number
        return "CZ" + checksum + bank + prefix + number;
    }
}
