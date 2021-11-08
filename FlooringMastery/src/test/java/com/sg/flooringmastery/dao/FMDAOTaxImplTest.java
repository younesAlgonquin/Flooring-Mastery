/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dao.FMDAOTax;
import com.sg.flooringmastery.dao.FMDAOTaxImpl;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author pc
 */
public class FMDAOTaxImplTest {
    
    
    private FMDAOTax testDao;


    public FMDAOTaxImplTest(){
    
    }

    @BeforeEach
    public void setUp() throws Exception {

        String file = "./Test/TaxTestFile.txt";

        PrintWriter printer;

        printer = new PrintWriter(new FileWriter(file));

        printer.println("TX::Texas::4.45");
        printer.println("WA::Washington::9.25");

        printer.flush();
        printer.close();

        testDao = new FMDAOTaxImpl(file);
        testDao.readTaxInfo();

    }//end setUp

    @Test
    public void getTaxInfoTest() {

        /**
         * Test plan : Map<String, Tax> getTaxInfo()
         * testDao.getTaxInfo().size() -> 2;;
         * testDao.getTaxInfo(texas) -> true
         * testDao.getTaxInfo(washington) -> true
         */
        Tax texas = new Tax("TX");
        texas.setStateName("Texas");
        texas.setTaxRate(new BigDecimal("4.45"));

        Tax washington = new Tax("WA");
        washington.setStateName("Washington");
        washington.setTaxRate(new BigDecimal("9.25"));
        
        Map<String , Tax> taxes = testDao.getTaxInfo();
        
        assertEquals(taxes.size(), 2, "Tax test file contains two entries");
        assertTrue(taxes.containsValue(texas));
        assertTrue(taxes.containsValue(washington));

    }//end getProductsInfoTest
    
}//end class
