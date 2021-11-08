package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.ClientOrder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.springframework.stereotype.Component;



/**
 * @author Younes Boutaleb email address: boutalebyounes@gmail.com Current date:
 * Purpose of the class:
 */
@Component
public class FMDAOOrderImpl implements FMDAOOrder {

    private Map<LocalDate, Map<Integer, ClientOrder>> orders = new TreeMap<>();
    private static final String DELIMITER = "::";
    private final String DATA_EXPORT_FILE;

    public FMDAOOrderImpl() {
        
        DATA_EXPORT_FILE = "DataExport.txt";

    }

    public FMDAOOrderImpl(String file) {
        
        DATA_EXPORT_FILE = file;

    }

    @Override
    public Map<Integer, ClientOrder> getAllOrders(LocalDate date) throws
            OrdersPersistenceException {

        if (!orders.containsKey(date)) {

            readDailyOrders(date);
        }//end if

        return orders.get(date);
    }

    @Override
    public ClientOrder getOrders(int Ordernumber, LocalDate date) throws
            OrdersPersistenceException {

        return this.getAllOrders(date).get(Ordernumber);

    }

    @Override
    public ClientOrder addOrder(LocalDate date, ClientOrder order){

        Map<Integer, ClientOrder> dateOrders;

        if (!orders.containsKey(date)) {

            try {
                readDailyOrders(date);
                dateOrders = orders.get(date);

            } catch (OrdersPersistenceException ex) {

                dateOrders = new HashMap<>();

            }//end catch
        } else {

            dateOrders = orders.get(date);
        }

        ClientOrder returnedOrder = dateOrders.put(order.getOrderNumber(), order);
        orders.put(date, dateOrders);

        return returnedOrder;
    }

    @Override
    public ClientOrder removeOrder(LocalDate date, int OrderNumber) throws
            OrdersPersistenceException {
        Map<Integer, ClientOrder> dateOrders = this.getAllOrders(date);
        ClientOrder removerOrder = dateOrders.remove(OrderNumber);
        orders.put(date, dateOrders);
        return removerOrder;

    }//end removeOrder
    
    
    
    @Override
    public int getOrderNumber(LocalDate futureDate) {

        int orderNumber = 0;
        try {
            Map<Integer, ClientOrder> futureOrders = getAllOrders(futureDate);
            TreeSet<Integer> orderNumbersSet = new TreeSet(futureOrders.keySet());
            orderNumber = orderNumbersSet.last();

        } catch (OrdersPersistenceException | NoSuchElementException ex) {

        }
        orderNumber += 1;
        return orderNumber;

    }//end  getOrderNumber

    //--------------------------------------------------------------------Read write Data
    private ClientOrder unmmarshallOrder(String orderAsString) {

        String[] array = orderAsString.split(DELIMITER);

        ClientOrder order;
        try {
            order = new ClientOrder(Integer.parseInt(array[0]));
            order.setCustomerName(array[1]);
            order.setState(array[2]);
            order.setTaxRate(new BigDecimal(array[3]));
            order.setProductType(array[4]);
            order.setArea(new BigDecimal(array[5]));
            order.setCostPerSquareFoot(new BigDecimal(array[6]));
            order.setLaborCostPerSquareFoot(new BigDecimal(array[7]));
            return order;

        } catch (NumberFormatException ne) {

            order = null;
        }
        return order;
    }

    @Override
    public void readDailyOrders(LocalDate date) throws
            OrdersPersistenceException {

        Scanner loader;

        try {
            String file = "Orders_"
                    + String.format("%02d%02d%04d", date.getMonthValue(), date.getDayOfMonth(), date.getYear())
                    + ".txt";

            loader = new Scanner(new BufferedReader(new FileReader(file)));

            ClientOrder loadedOrder;
            String line;
            Map<Integer, ClientOrder> dateOrders = new HashMap<>();

            while (loader.hasNextLine()) {

                line = loader.nextLine();
                loadedOrder = this.unmmarshallOrder(line);
                if (loadedOrder != null) {
                    dateOrders.put(loadedOrder.getOrderNumber(), loadedOrder);
                }

            }//end while

            orders.put(date, dateOrders);

            loader.close();

        } catch (FileNotFoundException fe) {

            throw new OrdersPersistenceException("No order for this date.", fe);

        }

    }//end readDailyOrders

    @Override
    public void readAllOrders() throws
            OrdersPersistenceException {

        Scanner loader;

        try {

            loader = new Scanner(new BufferedReader(new FileReader(DATA_EXPORT_FILE)));

            ClientOrder loadedOrder;
            String line;
            Map<Integer, ClientOrder> dateOrders;
            LocalDate date;

            while (loader.hasNextLine()) {

                line = loader.nextLine();
                try {
                    date = LocalDate.parse(line.split(DELIMITER)[12], DateTimeFormatter.ofPattern("MM-dd-yyyy"));

                    loadedOrder = this.unmmarshallOrder(line);

                    if (loadedOrder != null) {
                        if (!orders.containsKey(date)) {

                            dateOrders = new HashMap<>();
                            dateOrders.put(loadedOrder.getOrderNumber(), loadedOrder);
                            orders.put(date, dateOrders);

                        } else {
                            dateOrders = orders.get(date);
                            dateOrders.put(loadedOrder.getOrderNumber(), loadedOrder);
                            orders.put(date, dateOrders);
                        }
                    }
                } catch (DateTimeParseException ex) {
                }

            }//end while

            loader.close();

        } catch (FileNotFoundException fe) {

            throw new OrdersPersistenceException("Could not load Orders.", fe);

        }

    }//end readAllOrders

    private String marshallOrder(ClientOrder order) {

        String orderAsString = order.getOrderNumber() + DELIMITER
                + order.getCustomerName() + DELIMITER
                + order.getState() + DELIMITER
                + order.getTaxRate() + DELIMITER
                + order.getProductType() + DELIMITER
                + order.getArea() + DELIMITER
                + order.getCostPerSquareFoot() + DELIMITER
                + order.getLaborCostPerSquareFoot() + DELIMITER
                + order.calculateMaterialCost() + DELIMITER
                + order.calculateLaborCost() + DELIMITER
                + order.calculateTax() + DELIMITER
                + order.calculateTotal();

        return orderAsString;
    }

    @Override
    public void writeDailyOrders(LocalDate date) throws OrdersPersistenceException {

        PrintWriter printer;
        String file = "Orders_"
                + String.format("%02d%02d%04d", date.getMonthValue(), date.getDayOfMonth(), date.getYear())
                + ".txt";

        try {
            printer = new PrintWriter(new FileWriter(file));

        } catch (IOException io) {

            throw new OrdersPersistenceException("Could not save this order's information", io);

        }

        List<ClientOrder> list = new ArrayList(orders.get(date).values());
        String orderAsString;

        printer.println("OrderNumber::CustomerName::State::TaxRate::ProductType::Area::CostPerSquareFoot::"
                + "LaborCostPerSquareFoot::MaterialCost::LaborCost::Tax::Total");

        for (ClientOrder order : list) {

            orderAsString = marshallOrder(order);
            printer.println(orderAsString);
            printer.flush();
        }
        printer.close();

    }

    @Override
    public void writeAllOrders() throws OrdersPersistenceException {

        PrintWriter printer;

        try {
            printer = new PrintWriter(new FileWriter(DATA_EXPORT_FILE));

        } catch (IOException io) {

            throw new OrdersPersistenceException("Could not export orders information", io);

        }
        printer.println("OrderNumber::CustomerName::State::TaxRate::ProductType::Area::CostPerSquareFoot::"
                + "LaborCostPerSquareFoot::MaterialCost::LaborCost::Tax::Total::OrderDate");

        Set<LocalDate> datesSet = orders.keySet();
        datesSet.stream()
                .forEach(
                        (date) -> {
                            Map<Integer, ClientOrder> dateOrders = orders.get(date);
                            dateOrders.values().stream()
                                    .forEach(
                                            (order) -> {
                                                String orderAsString = marshallOrder(order);
                                                printer.println(orderAsString + "::" + date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
                                                printer.flush();
                                            }
                                    );
                        }
                );
        printer.close();

    }//end writeAllOrders

}
