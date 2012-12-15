/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartpaymentformat.string;

import com.smartpaymentformat.account.BankAccount;
import com.smartpaymentformat.account.CzechBankAccount;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author petrdvorak
 */
public class SmartPaymentTest {
    
    public SmartPaymentTest() {
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
     * Test of paymentStringFromAccount method, of class SmartPayment.
     */
    @Test
    public void testPaymentStringFromAccount() throws UnsupportedEncodingException {
        System.out.println("paymentStringFromAccount");
        SmartPaymentParameters parameters = new SmartPaymentParameters();
        parameters.setBankAccount(new CzechBankAccount("19", "123", "0800"));
        SmartPaymentMap extendedParameters = null;
        boolean transliterateParams = false;
        String expResult = "SPD*1.0*ACC:CZ2408000000190000000123";
        String result = SmartPayment.paymentStringFromAccount(parameters, extendedParameters, transliterateParams);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSpecialCharacterEscaping() throws UnsupportedEncodingException {
        String original = "abc  123\u2665\u2620**123  abc-+ěščřžýáíé---%20";
        System.out.println(SmartPayment.escapeDisallowedCharacters(original));
        String expected = "abc  123%E2%99%A5%E2%98%A0%2A%2A123  abc-%2B%C4%9B%C5%A1%C4%8D%C5%99%C5%BE%C3%BD%C3%A1%C3%AD%C3%A9---%2520";
        assertEquals(expected, SmartPayment.escapeDisallowedCharacters(original));
    }
    
    /**
     * Test of paymentStringFromAccount method, of class SmartPayment.
     */
    @Test
    public void testPaymentStringFromAccountAmountAndAlternateAccounts() throws UnsupportedEncodingException {
        System.out.println("paymentStringFromAccount");
        SmartPaymentParameters parameters = new SmartPaymentParameters();
        parameters.setBankAccount(new CzechBankAccount("19", "123", "0800"));
        List<BankAccount> alternateAccounts = new LinkedList<BankAccount>();
        alternateAccounts.add(new CzechBankAccount(null, "19", "5500"));
        alternateAccounts.add(new CzechBankAccount(null, "19", "0100"));
        parameters.setAlternateAccounts(alternateAccounts);
        parameters.setAmount(100.5);
        SmartPaymentMap extendedParameters = null;
        boolean transliterateParams = false;
        String expResult = "SPD*1.0*ACC:CZ2408000000190000000123*ALT-ACC:CZ9755000000000000000019,CZ7301000000000000000019*AM:100.5";
        String result = SmartPayment.paymentStringFromAccount(parameters, extendedParameters, transliterateParams);
        System.out.println(result);
        assertEquals(expResult, result);
    }
}
