package com.sg.flooringmastery.service;


/**
 *@author Younes Boutaleb
 *email address: boutalebyounes@gmail.com
 *Current date: 
 *Purpose of the class:
 */
public class NoOrderException extends Exception{
    
    public NoOrderException(String message){
    
    
        super(message);
    }
    
        public NoOrderException(String message, Throwable cause){
    
    
        super(message, cause);
    }
}//end class
