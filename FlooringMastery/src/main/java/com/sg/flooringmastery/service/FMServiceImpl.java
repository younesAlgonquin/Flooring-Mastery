package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FMAuditDao;
import com.sg.flooringmastery.dao.FMDAOOrder;
import com.sg.flooringmastery.dao.FMDAOProduct;
import com.sg.flooringmastery.dao.FMDAOTax;
import com.sg.flooringmastery.dao.OrdersPersistenceException;
import com.sg.flooringmastery.model.ClientOrder;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Younes Boutaleb email address: boutalebyounes@gmail.com Current date:
 * Purpose of the class:
 */
@Component
public class FMServiceImpl implements FMService {

    private FMDAOOrder daoOrder;
    private FMDAOTax daoTax;
    private FMDAOProduct daoProduct;
    private FMAuditDao daoAudit;

    @Autowired
    public FMServiceImpl(FMDAOOrder daoOrder, FMDAOTax daoTax, FMDAOProduct daoProduct, FMAuditDao daoAudit) {

        this.daoOrder = daoOrder;
        this.daoTax = daoTax;
        this.daoProduct = daoProduct;
        this.daoAudit = daoAudit;
    }

    @Override
    public Map<Integer, ClientOrder> getAllOrders(LocalDate date) throws NoOrderException,
            OrdersPersistenceException {

        Map<Integer, ClientOrder> ordersMap = daoOrder.getAllOrders(date);

        if (ordersMap.isEmpty()) {

            throw new NoOrderException("No order for this date");

        } else {
            return ordersMap;
        }

    }//end getAllOrders

    @Override
    public Map<String, Tax> getTaxInfo() {

        return daoTax.getTaxInfo();

    }

    @Override
    public Map<String, Product> getProductsInfo() {

        return daoProduct.getProductsInfo();

    }

    @Override
    public void loadTaxAndProductsInfo() throws
            OrdersPersistenceException {

        daoTax.readTaxInfo();
        daoProduct.readProductsInfo();

    }//end loadTaxAndProductsInfo

    @Override
    public int getOrderNumber(LocalDate futureDate) {

        return daoOrder.getOrderNumber(futureDate);

    }//end  getOrderNumber

    @Override
    public ClientOrder addOrder(LocalDate date, ClientOrder order) {

        return daoOrder.addOrder(date, order);
    }//end addOrder

    @Override
    public ClientOrder getOrder(int orderNumber, LocalDate date) throws
            OrdersPersistenceException,
            NoOrderException {

        ClientOrder order = daoOrder.getOrders(orderNumber, date);

        if (order == null) {
            throw new NoOrderException("The specified number does not correspond to any order in this date");
        }

        return order;
    }//end getOrder

    @Override
    public ClientOrder removeOrder(LocalDate date, int ordreNumber) throws
            OrdersPersistenceException {

        return daoOrder.removeOrder(date, ordreNumber);

    }//end removeOrder

    @Override
    public void readAllOrders() throws OrdersPersistenceException {

        daoOrder.readAllOrders();

    }//end writeAllOrders

    @Override
    public void writeDailyOrders(LocalDate date) throws OrdersPersistenceException {

        daoOrder.writeDailyOrders(date);
    }

    @Override
    public void writeAllOrders() throws OrdersPersistenceException {

        daoOrder.writeAllOrders();

    }//end writeAllOrders

    @Override
    public void writeAuditEntry(String entry) throws OrdersPersistenceException {

        daoAudit.writeAuditEntry(entry);

    }

}//end class
