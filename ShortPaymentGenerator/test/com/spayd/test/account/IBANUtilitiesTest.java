/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spayd.test.account;

import org.spayd.model.account.CzechBankAccount;
import org.spayd.utilities.IBANUtilities;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author petrdvorak
 */
public class IBANUtilitiesTest {
    
    public IBANUtilitiesTest() {
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
     * Test of computeIBANFromBankAccount method, of class IBANUtilities.
     */
    @Test
    public void testComputeIBANFromBankAccount() {
        System.out.println("computeIBANFromBankAccount");
        String prefix = "19";
        String number = "2000145399";
        String bank = "0800";
        CzechBankAccount account = new CzechBankAccount(prefix, number, bank);
        String expResult = "CZ6508000000192000145399";
        String result = IBANUtilities.computeIBANFromCzechBankAccount(account);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testComputeIBANFromBankAccount2() {
        System.out.println("computeIBANFromBankAccount2");
        String prefix = "178124";
        String number = "4159";
        String bank = "0710";
        CzechBankAccount account = new CzechBankAccount(prefix, number, bank);
        String expResult = "CZ6907101781240000004159";
        String result = IBANUtilities.computeIBANFromCzechBankAccount(account);
        assertEquals(expResult, result);
    }
}
