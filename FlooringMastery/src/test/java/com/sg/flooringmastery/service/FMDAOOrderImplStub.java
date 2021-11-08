package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FMDAOOrder;
import com.sg.flooringmastery.dao.OrdersPersistenceException;
import com.sg.flooringmastery.model.ClientOrder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Younes Current date: Purpose of the class:
 */
public class FMDAOOrderImplStub implements FMDAOOrder {

    private ClientOrder order;
    private LocalDate orderDate;

    public FMDAOOrderImplStub() {

        order = new ClientOrder(1);
        order.setCustomerName("william");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("1.50"));
        order.setArea(new BigDecimal("150"));
        order.setProductType("Wood");
        order.setCostPerSquareFoot(new BigDecimal("1.20"));
        order.setLaborCostPerSquareFoot(new BigDecimal("2.30"));

        orderDate = LocalDate.parse("01-01-2033", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }

    @Override
    public ClientOrder getOrders(int Ordernumber, LocalDate date) throws OrdersPersistenceException {

        if ((orderDate.equals(date)) && (order.getOrderNumber() == Ordernumber)) {
            return order;
        } else if ((orderDate.equals(date)) && (order.getOrderNumber() != Ordernumber)) {
            return null;
        } else {
            throw new OrdersPersistenceException("No orders for this date");
        }

    }//end getOrders

    @Override
    public Map<Integer, ClientOrder> getAllOrders(LocalDate date) throws OrdersPersistenceException {

        if (orderDate.equals(date)) {

            Map<Integer, ClientOrder> dailyOrders = new HashMap<>();
            dailyOrders.put(order.getOrderNumber(), order);
            return dailyOrders;
        } else {
            throw new OrdersPersistenceException("No orders for this date");
        }
    }//end getAllOrders

    @Override
    public ClientOrder addOrder(LocalDate date, ClientOrder orderObj) {

        if (orderDate.equals(date) && order.getOrderNumber() == orderObj.getOrderNumber()) {
            return order;
        } else {
            return null;
        }
    }//end addOrder

    @Override
    public ClientOrder removeOrder(LocalDate date, int OrderNumber) throws OrdersPersistenceException {

        if (orderDate.equals(date) && order.getOrderNumber() == OrderNumber) {
            return order;
        } else if (orderDate.equals(date) && order.getOrderNumber() != OrderNumber) {
            return null;
        } else {

            throw new OrdersPersistenceException("No orders for this date");
        }
    }//end removeOrder

    @Override
    public int getOrderNumber(LocalDate futureDate) {
        
        if(orderDate.equals(futureDate))
            return 2;
        else
            return 1;
        
        
    }//end getOrderNumber

    @Override
    public void readDailyOrders(LocalDate date) throws OrdersPersistenceException {
        //Do nothing
    }

    @Override
    public void writeDailyOrders(LocalDate date) throws OrdersPersistenceException {
        //Do nothing
    }

    @Override
    public void readAllOrders() throws OrdersPersistenceException {
        //Do nothing
    }

    @Override
    public void writeAllOrders() throws OrdersPersistenceException {

        //Do nothing
    }

}
