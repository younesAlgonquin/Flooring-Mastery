package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FMDAOTax;
import com.sg.flooringmastery.dao.OrdersPersistenceException;
import com.sg.flooringmastery.model.Tax;
import java.util.Map;


/**
 *@author Younes
 *Current date: 
 *Purpose of the class:
 */
public class FMDAOTaxImplStub implements FMDAOTax{

    @Override
    public Map<String, Tax> getTaxInfo() {
        throw new UnsupportedOperationException("Do nothing");
    }

    @Override
    public void readTaxInfo() throws OrdersPersistenceException {
        //do nothing
    }

}
