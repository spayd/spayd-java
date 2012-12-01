/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartpaymentformat.utilities;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author petrdvorak
 */
public class SmartPaymentValidatorTest {
    
    public SmartPaymentValidatorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of validatePaymentString method, of class SmartPaymentValidator.
     */
    @Test
    public void testValidatePaymentStringBasic() throws UnsupportedEncodingException {
        System.out.println("validatePaymentString");
        String paymentString = "SPD*1.0*232131";
        List result = SmartPaymentValidator.validatePaymentString(paymentString);
        // 1 error is expected
        assertEquals(result.size(), 1);
    }
    
    /**
     * Test of validatePaymentString method, of class SmartPaymentValidator.
     */
    @Test
    public void testValidatePaymentStringSimpleCorrect() throws UnsupportedEncodingException {
        System.out.println("validatePaymentString");
        String paymentString = "SPD*1.0*ACC:CZ3155000000001043006511";
        List<SmartPaymentValidationError> result = SmartPaymentValidator.validatePaymentString(paymentString);
        // 0 error is expected
        if (result != null && result.size() > 0) {
            System.out.println(result.get(0).getErrorDescription());
        }
        assertNull(result);
    }
    
    /**
     * Test of generating Paylibo string with alternate account
     */
    @Test
    public void testValidatePaymentStringAlternateAccounts() throws UnsupportedEncodingException {
        System.out.println("testValidatePaymentStringAlternateAccounts");
        String paymentString = "SPD*1.0*ACC:CZ3155000000001043006511+RBCZ66*ALT-ACC:CZ3155000000001043006511+RBCZ66,CZ3155000000001043006511+RBCZ66,CZ3155000000001043006511+RBCZ66";
        List<SmartPaymentValidationError> result = SmartPaymentValidator.validatePaymentString(paymentString);
        // 0 error is expected
        if (result != null && result.size() > 0) {
            System.out.println(result.get(0).getErrorDescription());
        }
        assertNull(result);
    }
    
    /**
     * Test of the situation with "**" in the string.
     */
    @Test
    public void testDoubleStarInString() throws UnsupportedEncodingException {
        System.out.println("testDoubleStarInString");
        String paymentString = "SPD*1.0*AM:100**ACC:CZ05678876589087329";
        List<SmartPaymentValidationError> result = SmartPaymentValidator.validatePaymentString(paymentString);
        // 0 error is expected
        if (result != null && result.size() > 0) {
            System.out.println(result.get(0).getErrorDescription());
        }
        assertEquals(result.size(), 1);
    }
    
}
