package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Tax;
import java.util.Map;

/**
 * @author Younes Boutaleb email address: boutalebyounes@gmail.com Current date:
 * Purpose of the class:
 */
public interface FMDAOTax {

    public Map<String, Tax> getTaxInfo();

    public void readTaxInfo() throws OrdersPersistenceException;

}
