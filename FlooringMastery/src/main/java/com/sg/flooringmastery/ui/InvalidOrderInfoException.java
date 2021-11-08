package com.sg.flooringmastery.ui;


/**
 *@author Younes Boutaleb
 *email address: boutalebyounes@gmail.com
 *Current date: 
 *Purpose of the class:
 */
public class InvalidOrderInfoException extends Exception{
    
    public InvalidOrderInfoException(String message){
    
    
        super(message);
    }
    
        public InvalidOrderInfoException(String message, Throwable cause){
    
    
        super(message, cause);
    }

}
