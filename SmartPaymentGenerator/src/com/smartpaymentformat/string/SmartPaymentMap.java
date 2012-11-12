/**
 *  Copyright (c) 2012, SmartPayment (www.SmartPayment.com).
 */
package com.smartpaymentformat.string;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author petrdvorak
 */
public class SmartPaymentMap extends HashMap<String, String> {

    public SmartPaymentMap(Map<String, String[]> map) {
        for (String key : map.keySet()) {
            key = key.toUpperCase();
            if (map.get(key) == null) continue;
            String value = map.get(key).toString().toUpperCase();
            if (!key.startsWith("X-")) {
                key = "X-" + key;
            }
            put(key, value);
        }
    }
    
    public String toExtendedParams() {
        String returnValue = "";
        for (String key : keySet()) {
            key = key.toUpperCase();
            String value = this.get(key);
            if (value == null) continue;
            if (!key.startsWith("X-")) {
                key = "X-" + key;
            }
            returnValue += key + ":" + value.replaceAll("\\*", "%2A") + "*";
        }
        return returnValue;
    }
    
}
