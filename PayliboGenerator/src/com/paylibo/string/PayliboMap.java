/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paylibo.string;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author petrdvorak
 */
public class PayliboMap extends HashMap<String, String> {

    public PayliboMap(Map<String, String[]> map) {
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
    
    public String toPayliboExtendedParams() {
        String returnValue = "";
        for (String key : keySet()) {
            key = key.toUpperCase();
            String value = this.get(key);
            if (value == null) continue;
            if (!key.startsWith("X-")) {
                key = "X-" + key;
            }
            returnValue += key + ":" + value + "/";
        }
        return returnValue;
    }
    
}
