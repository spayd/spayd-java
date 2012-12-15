/**
 *  Copyright (c) 2012, SPAYD (www.spayd.org).
 */
package org.spayd.string;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author petrdvorak
 */
public class SpaydExtendedPaymentAttributeMap extends HashMap<String, String> {

    /**
     * Creates a new map for the SPAYD extended attributes
     * @param map A map for extended parameters to be used, with key and X- values
     */
    public SpaydExtendedPaymentAttributeMap(Map<String, String> map) {
        for (String key : map.keySet()) {
            key = key.toUpperCase();
            if (map.get(key) == null) {
                continue;
            }
            String value = map.get(key).toUpperCase();
            if (!key.startsWith("X-")) {
                key = "X-" + key;
            }
            put(key, value);
        }
    }
    
    /**
     * Builds a string with extended parameter definitions
     * @return A part of the SPAYD string that contains all X- attributes
     * @throws UnsupportedEncodingException 
     */
    public String toExtendedParams() throws UnsupportedEncodingException {
        String returnValue = "";
        for (String key : keySet()) {
            key = key.toUpperCase();
            String value = this.get(key);
            if (value == null) {
                continue;
            }
            if (!key.startsWith("X-")) {
                key = "X-" + key;
            }
            returnValue += key + ":" + SpaydPayment.escapeDisallowedCharacters(value) + "*";
        }
        return returnValue;
    }
    
}
