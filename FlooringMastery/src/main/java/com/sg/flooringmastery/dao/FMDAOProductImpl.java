package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Product;
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
public class FMDAOProductImpl implements FMDAOProduct {

    private Map<String, Product> productDictionnaire = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private static final String DELIMITER = "::";
    private final String FILE_NAME;

    public FMDAOProductImpl() {

        FILE_NAME = "Products.txt";
    }

    public FMDAOProductImpl(String file) {

        FILE_NAME = file;
    }

    @Override
    public Map<String, Product> getProductsInfo() {

        return productDictionnaire;
    }//end getListOfProducts

    private Product unmmarshallProduct(String productAsString) {

        String[] array = productAsString.split(DELIMITER);

        Product product;
        try {
            product = new Product(array[0]);
            product.setCostPerSquareFoot(new BigDecimal(array[1]));
            product.setLaborCostPerSquareFoot(new BigDecimal(array[2]));

        } catch (NumberFormatException ne) {

            product = null;
        }

        return product;

    }//end 

    @Override
    public void readProductsInfo() throws OrdersPersistenceException {

        Scanner loader;

        try {

            loader = new Scanner(new BufferedReader(new FileReader(FILE_NAME)));

            Product product;
            String line;

            while (loader.hasNextLine()) {

                line = loader.nextLine();
                product = this.unmmarshallProduct(line);

                if (product != null) {
                    productDictionnaire.put(product.getProductType(), product);
                }

            }

            loader.close();

        } catch (FileNotFoundException fe) {

            throw new OrdersPersistenceException("Cannot load products information.", fe);

        }

    }//end readProductsInfo

}//end class
