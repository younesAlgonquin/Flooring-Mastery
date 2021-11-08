package com.sg.flooringmastery.dao;


/**
 *@author Younes Boutaleb
 *email address: boutalebyounes@gmail.com
 *Current date: 
 *Purpose of the class:
 */
public class OrdersPersistenceException extends Exception{
    
    public OrdersPersistenceException(String message){
    
    
        super(message);
    }
    
        public OrdersPersistenceException(String message, Throwable cause){
    
    
        super(message, cause);
    }

}
