package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.OrdersPersistenceException;
import com.sg.flooringmastery.model.ClientOrder;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import java.time.LocalDate;
import java.util.Map;

/**
 * @author Younes Boutaleb email address: boutalebyounes@gmail.com Current date:
 * Purpose of the class:
 */
public interface FMService {

    public Map<Integer, ClientOrder> getAllOrders(LocalDate date) throws NoOrderException,
            OrdersPersistenceException;

    public void loadTaxAndProductsInfo() throws
            OrdersPersistenceException;

    public Map<String, Tax> getTaxInfo();

    public Map<String, Product> getProductsInfo();

    public int getOrderNumber(LocalDate futureDate);

    public ClientOrder addOrder(LocalDate date, ClientOrder order);

    public ClientOrder getOrder(int orderNumber, LocalDate date) throws
            OrdersPersistenceException,
            NoOrderException;

    public ClientOrder removeOrder(LocalDate date, int ordreNumber) throws
            OrdersPersistenceException;

    public void writeDailyOrders(LocalDate date) throws OrdersPersistenceException;
    
    public void readAllOrders() throws OrdersPersistenceException;

    public void writeAllOrders()throws OrdersPersistenceException;
    
    public void writeAuditEntry(String entry) throws OrdersPersistenceException;

}//end class
