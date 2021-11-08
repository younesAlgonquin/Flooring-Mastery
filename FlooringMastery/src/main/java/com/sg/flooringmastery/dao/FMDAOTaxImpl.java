package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import org.springframework.stereotype.Component;

/**
 * @author Younes Boutaleb email address: boutalebyounes@gmail.com Current date:
 * Purpose of the class:
 */

@Component
public class FMDAOTaxImpl implements FMDAOTax {

    private Map<String, Tax> taxDictionnaire = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private static final String DELIMITER = "::";
    private final String FILE_NAME;

    public FMDAOTaxImpl() {

        FILE_NAME = "Taxes.txt";
    }

    public FMDAOTaxImpl(String file) {

        FILE_NAME = file;
    }

    @Override
    public Map<String, Tax> getTaxInfo() {

        return taxDictionnaire;

    }// end getListOfTaxes

    //--------------------------------------------------- read tax info
    private Tax unmmarshallTax(String taxAsString) {

        String[] array = taxAsString.split(DELIMITER);

        Tax taxInfo;
        try {
            taxInfo = new Tax(array[0]);
            taxInfo.setStateName(array[1]);
            taxInfo.setTaxRate(new BigDecimal(array[2]));

        } catch (NumberFormatException ne) {

            taxInfo = null;
        }

        return taxInfo;

    }//end 

    @Override
    public void readTaxInfo() throws OrdersPersistenceException {

        Scanner loader;

        try {

            loader = new Scanner(new BufferedReader(new FileReader(FILE_NAME)));

            Tax taxInfo;
            String line;

            while (loader.hasNextLine()) {

                line = loader.nextLine();
                taxInfo = this.unmmarshallTax(line);
                if (taxInfo != null) {
                    taxDictionnaire.put(taxInfo.getStateAbbreviation(), taxInfo);
                }

            }

            loader.close();

        } catch (FileNotFoundException fe) {

            throw new OrdersPersistenceException("Cannot load tax information");
        }

    }//end readTaxInfo

}//end class
