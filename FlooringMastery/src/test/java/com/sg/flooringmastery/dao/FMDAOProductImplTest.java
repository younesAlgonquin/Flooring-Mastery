/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dao.FMDAOProductImpl;
import com.sg.flooringmastery.dao.FMDAOProduct;
import com.sg.flooringmastery.model.Product;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author pc
 */
public class FMDAOProductImplTest {

    private FMDAOProduct testDao;

    public FMDAOProductImplTest() {

    }

    @BeforeEach
    public void setUp() throws Exception {

        String file = "./Test/productTestFile.txt";

        PrintWriter printer;

        printer = new PrintWriter(new FileWriter(file));

        printer.println("Carpet::2.25::2.10");
        printer.println("Wood::5.15::4.75");

        printer.flush();
        printer.close();

        testDao = new FMDAOProductImpl(file);
        testDao.readProductsInfo();

    }//end setUp

    @Test
    public void getProductsInfoTest() {

        /**
         * Test plan : Map<String, Product> getProductsInfo()
         * testDao.getProductsInfo().size() -> 2;;
         * testDao.containsValue(carpetProduct) -> true
         * testDao.containsValue(woodProduct) -> true
         */
        Product carpetProduct = new Product("Carpet");
        carpetProduct.setCostPerSquareFoot(new BigDecimal("2.25"));
        carpetProduct.setLaborCostPerSquareFoot(new BigDecimal("2.10"));

        Product woodProduct = new Product("Wood");
        woodProduct.setCostPerSquareFoot(new BigDecimal("5.15"));
        woodProduct.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        
        Map<String , Product> products = testDao.getProductsInfo();
        
        assertEquals(products.size(), 2, "Product test file contains two entries");
        assertTrue(products.containsValue(carpetProduct));
        assertTrue(products.containsValue(woodProduct));

    }//end getProductsInfoTest

}
