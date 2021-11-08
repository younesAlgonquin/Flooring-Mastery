/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.service.NoOrderException;
import com.sg.flooringmastery.service.FMService;
import com.sg.flooringmastery.service.FMServiceImpl;
import com.sg.flooringmastery.dao.FMAuditDao;
import com.sg.flooringmastery.dao.FMDAOOrder;
import com.sg.flooringmastery.dao.FMDAOProduct;
import com.sg.flooringmastery.dao.FMDAOTax;
import com.sg.flooringmastery.dao.OrdersPersistenceException;
import com.sg.flooringmastery.model.ClientOrder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author pc
 */
public class FMServiceImplTest {

    private FMService service;
    ClientOrder order1;
    LocalDate orderDate1;
    ClientOrder order2;
    LocalDate orderDate2;

    public FMServiceImplTest() {

        FMDAOOrder daoOrder = new FMDAOOrderImplStub();
        FMDAOProduct daoProduct = new FMDAOProductImplStub();
        FMDAOTax daoTax = new FMDAOTaxImplStub();
        FMAuditDao daoAudit = new FMAuditDAOImplStub();

        service = new FMServiceImpl(daoOrder, daoTax, daoProduct, daoAudit);
    }

    @BeforeEach
    public void setUp() {

        order1 = new ClientOrder(1);
        order1.setCustomerName("william");
        order1.setState("CA");
        order1.setTaxRate(new BigDecimal("1.50"));
        order1.setArea(new BigDecimal("150"));
        order1.setProductType("Wood");
        order1.setCostPerSquareFoot(new BigDecimal("1.20"));
        order1.setLaborCostPerSquareFoot(new BigDecimal("2.30"));

        order2 = new ClientOrder(2);
        order2.setCustomerName("smith");
        order2.setState("WA");
        order2.setTaxRate(new BigDecimal("1.50"));
        order2.setArea(new BigDecimal("150"));
        order2.setProductType("Wood");
        order2.setCostPerSquareFoot(new BigDecimal("1.20"));
        order2.setLaborCostPerSquareFoot(new BigDecimal("2.30"));

        orderDate1 = LocalDate.parse("01-01-2033", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        orderDate2 = LocalDate.parse("01-02-2033", DateTimeFormatter.ofPattern("MM-dd-yyyy"));

    }

    @Test
    public void getAllOrdersTest() {

        /**
         * Test plan : Map<Integer, ClientOrder> getAllOrders(LocalDate date)
         * service.getAllOrders (orderDate1) -> Map;; service.getAllOrders
         * (orderDate2) -> throw OrdersPersistenceException;;
         */
        try {
            Map<Integer, ClientOrder> dateOrders = service.getAllOrders(orderDate1);
            assertEquals(dateOrders.size(), 1, "It contains only order1");
            assertTrue(dateOrders.containsValue(order1));
        } catch (OrdersPersistenceException | NoOrderException ex) {

            fail("it shouldn't throw exception");

        }

        try {
            Map<Integer, ClientOrder> dateOrders = service.getAllOrders(orderDate2);
            fail("it dhould throw exception");
        } catch (OrdersPersistenceException | NoOrderException ex) {

            return;

        }
    }//end getAllOrdersTest

    @Test
    public void getOrdersTest() {

        /**
         * Test plan : ClientOrder getOrder(int orderNumber, LocalDate date)
         * service.getOrders (orderDate) -> Map;; service.getAllOrders
         * (orderDate) -> throw OrdersPersistenceException;;
         */
        try {
            ClientOrder returnedOrder1 = service.getOrder(order1.getOrderNumber(), orderDate1);
            assertEquals(order1, returnedOrder1, "It contains only order1");
        } catch (OrdersPersistenceException | NoOrderException ex) {

            fail("it shouldn't throw exception");

        }

        try {
            ClientOrder returnedOrder2 = service.getOrder(order2.getOrderNumber(), orderDate1);
            fail("it shouldn't throw NoOrderException");

        } catch (OrdersPersistenceException ox) {

            fail("Wrong exception");

        } catch (NoOrderException ex) {

            return;
        }

        try {
            ClientOrder returnedOrder3 = service.getOrder(order2.getOrderNumber(), orderDate2);
            fail("it should throw exception");

        } catch (NoOrderException ox) {

            fail("Wrong exception");

        } catch (OrdersPersistenceException ex) {

            return;
        }
    }//end getAllOrdersTest

    @Test
    public void addOrderTest() {

        /**
         * Test plan : ClientOrder addOrder(LocalDate date, ClientOrder order);
         * service.addOrder (orderDate1, order1) -> order1;; service.addOrder
         * (orderDate1, order1) -> null
         */
        ClientOrder returnedOrder1 = service.addOrder(orderDate1, order1);
        ClientOrder returnedOrder2 = service.addOrder(orderDate2, order2);

        assertEquals(returnedOrder1, order1, "Order1 overwritten");
        assertNull(returnedOrder2, "Map only contains order1");

    }//end addOrderTest

    @Test
    public void removeOrderTest() {

        /**
         * Test plan : ClientOrder addOrder(LocalDate date, ClientOrder order);
         * service.addOrder (orderDate1, order1) -> order1;; service.addOrder
         * (orderDate1, order1) -> null
         */
        try {
            ClientOrder returnedOrder1 = service.removeOrder(orderDate1, order1.getOrderNumber());
            assertEquals(order1, returnedOrder1, "It contains only order1");
        } catch (OrdersPersistenceException ex) {

            fail("it shouldn't throw exception");

        }

        try {
            ClientOrder returnedOrder2 = service.removeOrder(orderDate1, order2.getOrderNumber());
            assertNull(returnedOrder2, "Order2 does not exist in this date");

        } catch (OrdersPersistenceException ox) {

            fail("it shouldn't throw exception");

        }

        try {
            ClientOrder returnedOrder3 = service.removeOrder(orderDate2, order2.getOrderNumber());
            fail("it should throw exception. No orders in this date");
        } catch (OrdersPersistenceException ex) {

            return;
        }

    }//end removeOrderTest

    @Test
    public void getOrderNumberTest() {

        /**
         * Test plan : int getOrderNumber(LocalDate futureDate);
         * service.getOrderNumber (orderDate1) -> 2;; 
         * service.addOrder(orderDate2) -> 1
         */

        int orderNumber1 = service.getOrderNumber(orderDate1);
        int orderNumber2 = service.getOrderNumber(orderDate2);
        
        assertEquals(orderNumber1, 2, "This date has one order which number equals1");
        assertEquals(orderNumber2, 1, "This date doesn't have any order");

    }//end getAllOrdersTest

}//end class
