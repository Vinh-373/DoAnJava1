/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;
import java.math.*;
/**
 *
 * @author Lê Quang Hoàng
 */
public class ProductStockDTO {
    private String productId;
    private String productName;
    private String cpu;
    private String ram;
    private String graphicsCard;
    private String battery;
    private String weight;
    private BigDecimal price;
    private int quantity;
    private String status;
    private int spoiledQuantity;
    private String categoryId;
    private String image;
    public ProductStockDTO() {
    }
    public ProductStockDTO(String productId, String productName, String cpu, String ram, 
                       String graphicsCard, String battery, String weight, BigDecimal price, 
                       int quantity, String status, int spoiledQuantity, String categoryId, 
                       String image) {
    this.productId = productId;
    this.productName = productName;
    this.cpu = cpu;
    this.ram = ram;
    this.graphicsCard = graphicsCard;
    this.battery = battery;
    this.weight = weight;
    this.price = price;
    this.quantity = quantity;
    this.status = status;
    this.spoiledQuantity = spoiledQuantity;
    this.categoryId = categoryId;
    this.image = image;
    
}



    // Getters and Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getCpu() { return cpu; }
    public void setCpu(String cpu) { this.cpu = cpu; }
    public String getRam() { return ram; }
    public void setRam(String ram) { this.ram = ram; }
    public String getGraphicsCard() { return graphicsCard; }
    public void setGraphicsCard(String graphicsCard) { this.graphicsCard = graphicsCard; }
    public String getBattery() { return battery; }
    public void setBattery(String battery) { this.battery = battery; }
    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getSpoiledQuantity() { return spoiledQuantity; }
    public void setSpoiledQuantity(int spoiledQuantity) { this.spoiledQuantity = spoiledQuantity; }
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
