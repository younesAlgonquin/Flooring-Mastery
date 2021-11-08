package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Product;
import java.util.Map;

/**
 * @author Younes Boutaleb email address: boutalebyounes@gmail.com Current date:
 * Purpose of the class:
 */
public interface FMDAOProduct {

    public Map<String, Product> getProductsInfo();

    public void readProductsInfo() throws OrdersPersistenceException;

}
