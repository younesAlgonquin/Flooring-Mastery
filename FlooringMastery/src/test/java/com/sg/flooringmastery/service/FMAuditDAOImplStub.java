package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.OrdersPersistenceException;
import com.sg.flooringmastery.dao.FMAuditDao;
import com.sg.flooringmastery.dao.FMAuditDao;
import com.sg.flooringmastery.dao.OrdersPersistenceException;


/**
 *@author Younes
 *Current date: 
 *Purpose of the class:
 */
public class FMAuditDAOImplStub implements FMAuditDao{

    @Override
    public void writeAuditEntry(String entry) throws OrdersPersistenceException {
        //do nothing
    }

}
