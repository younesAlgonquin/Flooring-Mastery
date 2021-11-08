package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.model.ClientOrder;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Younes Boutaleb email address: boutalebyounes@gmail.com Current date:
 * Purpose of the class:
 */
@Component
public class FMView {

    
    private UserIO io;

    @Autowired
    public FMView(UserIO io) {

        this.io = io;
    }

    public int getOption() {

        System.out.println(
                "  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n"
                + "* <<Flooring Program>>\n"
                + "* 1. Display Orders\n"
                + "* 2. Add an Order\n"
                + "* 3. Edit an Order\n"
                + "* 4. Remove an Order\n"
                + "* 5. Export All Data\n"
                + "* 6. Quit\n"
                + "*\n"
                + "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *"
        );

        return io.readInt("Select an option from the menu above", 1, 6);

    }//end getOption

    public LocalDate getDate() {

        return io.readDate("Enter a date (MM-dd-yyyy):");

    }//end getDate

    public String displayOrdersListHeader() {

        return String.format("|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|",
                "OrderNumber", "CustomerName ", "State ", "TaxRate ", "ProductType", "Area ", "CostPer SF ",
                "LaborCost SF", "MaterialCost ", "LaborCost ", "Tax", "Total");
    }//end displayOrdersListHeader

    public void displayAllOrders(List<ClientOrder> list) {

        System.out.println(displayOrdersListHeader());
        list.stream()
                .forEach(
                        (order) -> {
                            System.out.println(order.toString());
                        }
                );

    }//end displayAllOrders

    //--------------------------------------------------------Create order methods
    public LocalDate getFutureDate() {

        return io.readFutureDate("Enter your order date starting from tomorrow (MM-dd-yyyy) ");

    }//end getFutureDate

    private String getValidName(String prompt) throws InvalidOrderInfoException {

        Pattern pattern = Pattern.compile("[^a-zA-Z0-9 .,]");

        boolean invalidName = true;
        String name = "";
        while (invalidName) {
            name = io.readString(prompt);
            if (name.toUpperCase().equals("QUIT")) {

                throw new InvalidOrderInfoException("Order canceled. Going back to main menu");
            }//end if
            Matcher matcher = pattern.matcher(name);
            invalidName = matcher.find();

        }
        return name;

    }//end getValidName

    private String getValidNotBlankName(String prompt) throws InvalidOrderInfoException {

        String name = "";

        while (name.isBlank()) {

            name = getValidName(prompt);
        }

        return name;

    }//end getValidNotBlankName

    private void displayStatesInfo(Map<String, Tax> taxDictionnaire) {

        System.out.println(String.format("|%-15s|%-15s|%-15s|", "State", "State Name", "Tax Rate"));
        taxDictionnaire.values().stream()
                .forEach(
                        (taxInfo) -> {
                            System.out.println(taxInfo.toString());
                        }
                );
    }//end displayStatesInfo

    private Tax getValidState(Map<String, Tax> taxDictionnaire, String prompt) throws InvalidOrderInfoException {

        boolean invalidState = true;
        String stateAbbreviation = null;

        while (invalidState) {

            stateAbbreviation = io.readString(prompt);

            if (stateAbbreviation.equalsIgnoreCase("QUIT")) {

                throw new InvalidOrderInfoException("Order canceled. Going back to main menu");
            }//end if

            if (stateAbbreviation.isBlank() || taxDictionnaire.containsKey(stateAbbreviation)) {

                invalidState = false;

            }//end if

        }//end while

        return taxDictionnaire.get(stateAbbreviation);

    }//end getValidState

    private Tax getValidNotBlankState(Map<String, Tax> taxDictionnaire, String prompt) throws InvalidOrderInfoException {

        Tax taxInfo = null;
        while (taxInfo == null) {

            taxInfo = getValidState(taxDictionnaire, prompt);

        }//end while

        return taxInfo;
    }//end getValidNotBlankState

    private void displayProducts(Map<String, Product> productDictionnaire) {

        System.out.println(String.format("|%-15s|%-15s|%-20s|", "Product type", "Cost per sq", "Labor cost per sq"));
        productDictionnaire.values().stream()
                .forEach(
                        (product) -> {
                            System.out.println(product.toString());
                        }
                );
    }//end displayStatesInfo

    private Product getValidProduct(Map<String, Product> productDictionnaire, String prompt)
            throws InvalidOrderInfoException {

        boolean invalidProduct = true;
        String productType = null;

        while (invalidProduct) {

            productType = io.readString(prompt);
            if (productType.equalsIgnoreCase("QUIT")) {

                throw new InvalidOrderInfoException("Order canceled. Going back to main menu");
            }//end if

            if (productType.isBlank() || productDictionnaire.containsKey(productType)) {

                invalidProduct = false;

            }//end if

        }//end while

        return productDictionnaire.get(productType);

    }//end getValidProduct

    private Product getValidNotNullProduct(Map<String, Product> productDictionnaire, String prompt)
            throws InvalidOrderInfoException {

        Product product = null;

        while (product == null) {

            product = getValidProduct(productDictionnaire, prompt);

        }//end while

        return product;

    }

    private BigDecimal getValidArea() throws InvalidOrderInfoException {

        BigDecimal area = io.readPositiveBigDecimal("Enter area (minimum 100 sq ft) or tape 99 to quit:", new BigDecimal("99"));

        if (area.equals(new BigDecimal(99))) {

            throw new InvalidOrderInfoException("Order canceled. Giong back to main menu");

        }

        return area;
    }//end getValidArea

    public ClientOrder createOrder(Map<String, Tax> taxDictionnaire, Map<String, Product> productDictionnaire, int orderNumber)
            throws InvalidOrderInfoException {

        String customerName = getValidNotBlankName("Enter name or tape quit to go to main menu:");
        displayStatesInfo(taxDictionnaire);
        Tax taxInfo = getValidNotBlankState(taxDictionnaire, "Enter State name (Two lwtters) or tape quit to go to main menu");
        displayProducts(productDictionnaire);
        Product product = getValidNotNullProduct(productDictionnaire, "Enter product type or tape quit to go to main menu ");
        BigDecimal area = getValidArea();

        ClientOrder order = new ClientOrder(orderNumber);
        order.setCustomerName(customerName);
        order.setState(taxInfo.getStateAbbreviation());
        order.setTaxRate(taxInfo.getTaxRate());
        order.setProductType(product.getProductType());
        order.setArea(area);
        order.setCostPerSquareFoot(product.getCostPerSquareFoot());
        order.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());

        return order;
    }//end createOrder

    public void diplayOrderInfo(ClientOrder order) {

        System.out.println(displayOrdersListHeader());
        System.out.println(order.toString());

    }//end diplayOrderInfo

    public String promptForComfirmation(String prompt) throws InvalidOrderInfoException {

        String choice = "";

        while (!(choice.equals("Y") || choice.equals("N"))) {

            choice = io.readString(prompt).toUpperCase();

        }//end while

        if (choice.equals("N")) {

            throw new InvalidOrderInfoException("Operation canceled. Going back to main menu");
        }

        return choice;

    }//end promptForConfirmation

//-------------------------------------------------------------- Edit Order methods
    public int promptForOrderNumber() {

        return io.readInt("Enter Order number", 1, 10000);

    }//end promptForOrderNumber

    public BigDecimal promptForUpdatedArea(String prompt) throws InvalidOrderInfoException {

        String input;
        BigDecimal area = null;

        while (area == null || (area.compareTo(new BigDecimal(100)) < 0)) {

            input = io.readString(prompt);

            if (input.isBlank()) {

                throw new InvalidOrderInfoException("");

            } else {

                try {
                    area = new BigDecimal(input);
                } catch (NumberFormatException ne) {

                }

            }//end else
        }//end while

        return area;
    }//end promptForUpdatedArea

    public ClientOrder updateOrder(Map<String, Tax> taxDictionnaire, Map<String, Product> productDictionnaire, ClientOrder order)
            throws InvalidOrderInfoException {

        String customerName = getValidName("Enter name or hit enter (" + order.getCustomerName() + "):");
        displayStatesInfo(taxDictionnaire);
        Tax taxInfo = getValidState(taxDictionnaire, "Enter State name (Two lwtters) hit enter (" + order.getState() + "):");
        displayProducts(productDictionnaire);
        Product product = getValidProduct(productDictionnaire, "Enter product type or hit enter (" + order.getProductType() + "):");
        BigDecimal area = null;
        try {
            area = promptForUpdatedArea("Enter area (minimum 100 sq ft) or hit enter (" + order.getArea() + "):");
        } catch (InvalidOrderInfoException ioe) {
        }

        if (!customerName.isBlank()) {
            order.setCustomerName(customerName);
        }

        if (taxInfo != null) {
            order.setState(taxInfo.getStateAbbreviation());
            order.setTaxRate(taxInfo.getTaxRate());
        }

        if (product != null) {
            order.setProductType(product.getProductType());
            order.setCostPerSquareFoot(product.getCostPerSquareFoot());
            order.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());
        }

        if (area != null) {
            order.setArea(area);
        }

        return order;

    }//end updateOrder

    public void displayGreetingMessage() {

        io.print("Thank you for your trust. Good by.");

    }//end displayGreetingMessage

    public void displayWelcomeMessage() {

        io.print("Welcome to TSG Corp, We are happy to serve you");

    }//end displayGreetingMessage

}//end class
