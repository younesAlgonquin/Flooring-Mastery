package com.sg.flooringmastery.dao;


/**
 *@author Younes Boutaleb
 *email address: boutalebyounes@gmail.com
 *Current date: 
 *Purpose of the class:
 */
public class CorruptedFileException extends Exception{
    
    public CorruptedFileException(String message){
    
    
        super(message);
    }
    
        public CorruptedFileException(String message, Throwable cause){
    
    
        super(message, cause);
    }

}
