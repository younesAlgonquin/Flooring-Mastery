/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.ClientOrder;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author pc
 */
public class FMDAOOrderImplTest {

    private FMDAOOrder testDao;
    private ClientOrder order1;
    private ClientOrder order2;
    private LocalDate date1;
    private LocalDate date2;
    private LocalDate date3;

    public FMDAOOrderImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {

        String file = "./Test/FMTestFile.txt";

        PrintWriter printer;

        printer = new PrintWriter(new FileWriter(file));

        printer.println("3::Albert Einstein::KY::6.00::Carpet::217.00::2.25::2.10::488.25::455.70::56.64::1000.59::06-02-2013");
        printer.println("21::dia::TX::4.45::Carpet::888::2.25::2.10::1998.00::1864.80::171.89::4034.69::01-01-2022");

        printer.flush();
        printer.close();

        testDao = new FMDAOOrderImpl(file);
        testDao.readAllOrders();

        order1 = new ClientOrder(3);
        order1.setCustomerName("Albert Einstein");
        order1.setState("KY");
        order1.setTaxRate(new BigDecimal("6.00"));
        order1.setProductType("Carpet");
        order1.setArea(new BigDecimal("217.00"));
        order1.setCostPerSquareFoot(new BigDecimal("2.25"));
        order1.setLaborCostPerSquareFoot(new BigDecimal("2.10"));

        order2 = new ClientOrder(21);
        order2.setCustomerName("dia");
        order2.setState("TX");
        order2.setTaxRate(new BigDecimal("4.45"));
        order2.setProductType("Carpet");
        order2.setArea(new BigDecimal("888"));
        order2.setCostPerSquareFoot(new BigDecimal("2.25"));
        order2.setLaborCostPerSquareFoot(new BigDecimal("2.10"));

        date1 = LocalDate.parse("06-02-2013", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        date2 = LocalDate.parse("01-01-2022", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        date3 = LocalDate.parse("01-01-2222", DateTimeFormatter.ofPattern("MM-dd-yyyy"));

    }//end setUp

    @Test
    public void getOrdersTest() throws OrdersPersistenceException {

        /**
         * Test Plan : ClientOrder getOrders(int Ordernumber, LocalDate date)
         *
         * testDao.get(3, "06-02-2013") -> order1;
         * testDao.get(21,"01-01-2022")-> order2; testDao.get(50,"01-01-2022")->
         * OrdersPersistenceException; ; testDao.get(21,"01-01-2023") ->
         * OrdersPersistenceException; testDao.get(50,"01-01-2023") ->
         * OrdersPersistenceException;
         *
         */
        ClientOrder retrievedOrder1 = testDao.getOrders(3, date1);
        ClientOrder retrievedOrder2 = testDao.getOrders(21, date2);
        ClientOrder retrievedOrder3 = testDao.getOrders(50, date2);

        assertEquals(order1, retrievedOrder1, "Retrieve the albert's order");
        assertEquals(order2, retrievedOrder2, "Retrieve the dia's order");
        assertNull(retrievedOrder3, "Invalid order date");

        try {
            ClientOrder retrievedOrder4 = testDao.getOrders(21, date3);
            fail("Has to throw OrdersPersistenceException");
        } catch (OrdersPersistenceException ex) {

            return;
        }
        try {
            ClientOrder retrievedOrder5 = testDao.getOrders(50, date3);
            fail("Has to throw OrdersPersistenceException");
        } catch (OrdersPersistenceException ex) {

            return;
        }

    }//end getOrdersTest

    @Test
    public void getAllOrdersTest() throws OrdersPersistenceException {

        /**
         * Test Plan : Map<Integer, ClientOrder> getAllOrders(LocalDate date)
         *
         * testDao.getAllOrders(date1).size() -> 1;
         * testDao.getAllOrders(date1).get(3)-> order1;
         * testDao.getAllOrders(date3) -> OrdersPersistenceException;
         *
         */
        Map<Integer, ClientOrder> retrievedData1 = testDao.getAllOrders(date1);

        assertEquals(retrievedData1.size(), 1, "Retrieve one order only");
        assertEquals(retrievedData1.get(3), order1, "Retrieve the albert's order only");

        try {
            Map<Integer, ClientOrder> retrievedData2 = testDao.getAllOrders(date3);
            fail("Has to throw OrdersPersistenceException");
        } catch (OrdersPersistenceException ex) {

            return;
        }

    }//end getAllOrdersTest

    @Test
    public void addOrderTest() throws OrdersPersistenceException {

        /**
         * Test Plan : ClientOrder addOrder(LocalDate date, ClientOrder order)
         *
         * testDao.addOrder(date1, order1) -> order1; testDao.addOrder(date1,
         * order2) -> null; testDao.getOrders(21, date1)-> order2;
         *
         */
        ClientOrder returnedOrder1 = testDao.addOrder(date1, order1);
        ClientOrder returnedOrder2 = testDao.addOrder(date1, order2);
        ClientOrder returnedOrder3 = testDao.getOrders(21, date1);

        assertEquals(order1, returnedOrder1, "return the albert's order");
        assertNull(returnedOrder2, "return null");
        assertEquals(order2, returnedOrder3, "return the dia's order");

    }//end addOrderTest

    @Test
    public void removeOrderTest() throws OrdersPersistenceException {

        /**
         * Test Plan :  ClientOrder removeOrder(LocalDate date, int OrderNumber)
         *
         * testDao.removeOrder(date1, 3) -> order1;
         * tetsDao.getOrders(3, date1) -> null
         * testDao.removeOrder(date1, 99) -> null; 
         *
         */
        ClientOrder returnedOrder1 = testDao.removeOrder(date1, 3);
        ClientOrder returnedOrder2 = testDao.getOrders(3, date1);
        ClientOrder returnedOrder3 = testDao.removeOrder(date1, 99);
        

        assertEquals(order1, returnedOrder1, "Remove the albert's order");
        assertNull(returnedOrder2, "albert's order removed");
        assertNull(returnedOrder3, "No order removed");

    }//removeOrderTest

}//end class
