package com.sg.flooringmastery.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author Younes Boutaleb email address: boutalebyounes@gmail.com Current date:
 * Purpose of the class:
 */
public class ClientOrder {

    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
//    private BigDecimal materialCost;
//    private BigDecimal laborCost;
//    private BigDecimal tax;I
//    private BigDecimal total;

    public ClientOrder(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    

    public int getOrderNumber() {
        return orderNumber;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }
    
    
    //-----------------------------------------------------------------------
    public BigDecimal calculateMaterialCost(){
    
        return area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
    
    }
    
    public BigDecimal calculateLaborCost(){
    
        return area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
    
    }
    
    public BigDecimal calculateTax() {
    
        return (calculateMaterialCost().add(calculateLaborCost()))
                .multiply(taxRate.divide(new BigDecimal("100")))
                .setScale(2, RoundingMode.HALF_UP);
    }
    
    public BigDecimal calculateTotal(){
    
        return calculateMaterialCost().add(calculateLaborCost()).add(calculateTax()).setScale(2, RoundingMode.HALF_UP);
    
    }
    
    //------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.orderNumber;
        hash = 59 * hash + Objects.hashCode(this.customerName);
        hash = 59 * hash + Objects.hashCode(this.state);
        hash = 59 * hash + Objects.hashCode(this.taxRate);
        hash = 59 * hash + Objects.hashCode(this.productType);
        hash = 59 * hash + Objects.hashCode(this.area);
        hash = 59 * hash + Objects.hashCode(this.costPerSquareFoot);
        hash = 59 * hash + Objects.hashCode(this.laborCostPerSquareFoot);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClientOrder other = (ClientOrder) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.costPerSquareFoot, other.costPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSquareFoot, other.laborCostPerSquareFoot)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|",
                orderNumber, customerName, state, taxRate , productType, area , costPerSquareFoot ,
                        laborCostPerSquareFoot, calculateMaterialCost(), calculateLaborCost(), calculateTax(), calculateTotal());
    
    }
    


    
    
    

}//end class
