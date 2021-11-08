package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FMDAOProduct;
import com.sg.flooringmastery.dao.OrdersPersistenceException;
import com.sg.flooringmastery.model.Product;
import java.util.Map;


/**
 *@author Younes
 *Current date: 
 *Purpose of the class:
 */
public class FMDAOProductImplStub implements FMDAOProduct{

    @Override
    public Map<String, Product> getProductsInfo() {
        throw new UnsupportedOperationException("Do nothing"); 
    }

    @Override
    public void readProductsInfo() throws OrdersPersistenceException {
        //do nothing
    }

}
