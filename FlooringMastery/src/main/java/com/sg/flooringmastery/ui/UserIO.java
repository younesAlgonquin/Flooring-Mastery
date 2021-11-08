/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author pc
 */
public interface UserIO {

    void print(String message);

    String readString(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max) ;

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max) ;

    long readLong(String prompt) ;

    long readLong(String prompt, long min, long max)  ;
    
    BigDecimal readBigDecimal(String promt);
    
    BigDecimal readPositiveBigDecimal(String promt, BigDecimal min);
    
    public LocalDate readDate(String prompt);
    
    public LocalDate readFutureDate(String prompt);

}
