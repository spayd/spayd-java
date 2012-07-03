/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paylibo.utilities;

import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author petrdvorak
 */
public class PayliboValidatorTest {
    
    public PayliboValidatorTest() {
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
     * Test of validatePayliboString method, of class PayliboValidator.
     */
    @Test
    public void testValidatePayliboStringBasic() {
        System.out.println("validatePayliboString");
        String payliboString = "PAY/1.0/232131";
        List result = PayliboValidator.validatePayliboString(payliboString);
        // 1 error is expected
        assertEquals(result.size(), 1);
    }
    
}
