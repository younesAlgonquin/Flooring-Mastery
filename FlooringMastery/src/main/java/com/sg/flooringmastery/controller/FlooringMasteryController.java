package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.OrdersPersistenceException;
import com.sg.flooringmastery.model.ClientOrder;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import com.sg.flooringmastery.service.FMService;
import com.sg.flooringmastery.service.NoOrderException;
import com.sg.flooringmastery.ui.FMView;
import com.sg.flooringmastery.ui.InvalidOrderInfoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Younes Boutaleb email address: boutalebyounes@gmail.com Current date:
 * Purpose of the class:
 */
@Component
public class FlooringMasteryController {

    FMService service;
    FMView view;

    @Autowired
    public FlooringMasteryController(FMService service, FMView view) {
        this.service = service;
        this.view = view;

    }

    public void run() {

        int option = 0;
        view.displayWelcomeMessage();

        try {
            service.writeAuditEntry("Start Session.");
            service.loadTaxAndProductsInfo();
            service.readAllOrders();

            while (option != 6) {

                option = view.getOption();
                try {

                    switch (option) {

                        case 1:
                            displayOrders();
                            break;

                        case 2:
                            addOrder();
                            break;

                        case 3:
                            editOrder();
                            break;

                        case 4:
                            removeOrder();
                            break;

                        case 5:
                            saveOrders();
                            break;

                        case 6:
                            view.displayGreetingMessage();
                            service.writeAuditEntry("End Session.");
                            break;
                    }//end switch

                } catch (NoOrderException ne) {

                    System.out.println(ne.getMessage());

                } catch (OrdersPersistenceException ope) {

                    System.out.println(ope.getMessage());

                } catch (InvalidOrderInfoException ioe) {

                    System.out.println(ioe.getMessage());
                }

            }//end while

        } catch (OrdersPersistenceException ex) {
            System.out.println(ex.getMessage());
        }

    }//end run

    public void displayOrders() throws NoOrderException,
            OrdersPersistenceException {

        LocalDate date = view.getDate();
        Map<Integer, ClientOrder> ordersMap = service.getAllOrders(date);
        view.displayAllOrders(new ArrayList(ordersMap.values()));
        service.writeAuditEntry("List Of orders displayed.");

    }//end displayOrders

    public void addOrder() throws InvalidOrderInfoException,
            OrdersPersistenceException {

        Map<String, Tax> taxDictionnaire = service.getTaxInfo();
        Map<String, Product> productDictionnaire = service.getProductsInfo();
        LocalDate futureDate = view.getFutureDate();

        int orderNumber = service.getOrderNumber(futureDate);
        ClientOrder order = view.createOrder(taxDictionnaire, productDictionnaire, orderNumber);

        view.diplayOrderInfo(order);
        view.promptForComfirmation("Do you want to place this order (Y/N)");
        service.addOrder(futureDate, order);
        service.writeDailyOrders(futureDate);
        service.writeAuditEntry("Order (" + futureDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
                + "," + order.getOrderNumber()  + ") generated");

    }//end addOrder

    public void editOrder() throws NoOrderException,
            OrdersPersistenceException,
            InvalidOrderInfoException {

        Map<String, Tax> taxDictionnaire = service.getTaxInfo();
        Map<String, Product> productDictionnaire = service.getProductsInfo();
        LocalDate date = view.getDate();
        int orderNumber = view.promptForOrderNumber();

        ClientOrder retrievedOrder = service.getOrder(orderNumber, date);

        ClientOrder order = view.updateOrder(taxDictionnaire, productDictionnaire, retrievedOrder);
        view.diplayOrderInfo(order);
        view.promptForComfirmation("Do you want to update this order (Y/N)");
        service.addOrder(date, order);
        service.writeDailyOrders(date);
        service.writeAuditEntry("Order (" + date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
                + "," + order.getOrderNumber() + ") updated");

    }//end editOrder

    public void removeOrder() throws NoOrderException,
            OrdersPersistenceException,
            InvalidOrderInfoException {

        LocalDate date = view.getDate();
        int orderNumber = view.promptForOrderNumber();

        ClientOrder order = service.getOrder(orderNumber, date);
        view.diplayOrderInfo(order);

        view.promptForComfirmation("Do you want to remove this order (Y/N)");
        service.removeOrder(date, orderNumber);
        service.writeDailyOrders(date);
        service.writeAuditEntry("Order (" + date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
                + "," + order.getOrderNumber()  + ") removed");

    }//end removeOrder

    public void saveOrders() throws OrdersPersistenceException {

        service.writeAllOrders();
        service.writeAuditEntry("Data exported");

    }//saveOrders
}
