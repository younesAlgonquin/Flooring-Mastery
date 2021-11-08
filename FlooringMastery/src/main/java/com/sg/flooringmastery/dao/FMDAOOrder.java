package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.ClientOrder;
import java.time.LocalDate;
import java.util.Map;

/**
 * @author Younes Boutaleb email address: boutalebyounes@gmail.com Current date:
 * Purpose of the class:
 */
public interface FMDAOOrder {

    public ClientOrder getOrders(int Ordernumber, LocalDate date) throws
            OrdersPersistenceException;

    public Map<Integer, ClientOrder> getAllOrders(LocalDate date) throws
            OrdersPersistenceException;

    public ClientOrder addOrder(LocalDate date, ClientOrder order);

    public ClientOrder removeOrder(LocalDate date, int OrderNumber) throws
            OrdersPersistenceException;
    
    public int getOrderNumber(LocalDate futureDate);

    public void readDailyOrders(LocalDate date) throws
            OrdersPersistenceException;

    public void writeDailyOrders(LocalDate date) throws
            OrdersPersistenceException;

    public void readAllOrders() throws OrdersPersistenceException;

    public void writeAllOrders() throws OrdersPersistenceException;

}
