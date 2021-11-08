package com.sg.flooringmastery.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 * @author Younes Boutaleb email address: boutalebyounes@gmail.com Current date:
 * Purpose of the class:
 */

@Component
public class FMAuditDaoImpl implements FMAuditDao {

    
    public static final String AUDIT_FILE= "FMAudit.txt";
    
    public FMAuditDaoImpl(){
    
    }
    
    @Override
    public void writeAuditEntry(String entry) throws
            OrdersPersistenceException {

        PrintWriter printer;

        try {

            printer = new PrintWriter(new FileWriter(AUDIT_FILE, true));

        } catch (IOException io) {

            throw new OrdersPersistenceException("Could not write transaction's details", io);

        }

        LocalDateTime date = LocalDateTime.now();
        String dateAsString = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyyy--hh:mm:ss-a"));

        printer.println(dateAsString + "::" + entry);
        printer.flush();
        printer.close();

    }

}
