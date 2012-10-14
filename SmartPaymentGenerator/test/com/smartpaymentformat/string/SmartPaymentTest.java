/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartpaymentformat.string;

import com.smartpaymentformat.account.BankAccount;
import com.smartpaymentformat.account.CzechBankAccount;
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
    public void testPaymentStringFromAccount() {
        System.out.println("paymentStringFromAccount");
        SmartPaymentParameters parameters = new SmartPaymentParameters();
        parameters.setBankAccount(new CzechBankAccount("19", "123", "0800"));
        SmartPaymentMap extendedParameters = null;
        boolean transliterateParams = false;
        String expResult = "PAY*1.0*ACC:CZ2408000000190000000123";
        String result = SmartPayment.paymentStringFromAccount(parameters, extendedParameters, transliterateParams);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of paymentStringFromAccount method, of class SmartPayment.
     */
    @Test
    public void testPaymentStringFromAccountAmountAndAlternateAccounts() {
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
        String expResult = "PAY*1.0*ACC:CZ2408000000190000000123*ALT-ACC:CZ2408000000190000000123,CZ2408000000190000000123*AM:100.5";
        String result = SmartPayment.paymentStringFromAccount(parameters, extendedParameters, transliterateParams);
        assertEquals(expResult, result);
    }
}
