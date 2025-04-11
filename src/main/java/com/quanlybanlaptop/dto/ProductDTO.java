package com.quanlybanlaptop.dto;

import java.math.BigDecimal;

public class ProductDTO {
    private int idProduct;           // ID sản phẩm
    private String name;                // Tên sản phẩm
    private String cpu;                 // CPU
    private String sizeScreen;          // Kích thước màn hình
    private String ram;                 // RAM
    private String graphicsCard;        // Card đồ họa
    private String battery;             // Pin
    private String operatingSystem;     // Hệ điều hành
    private String weight;              // Trọng lượng
    private BigDecimal price;              // Giá bán
    private int quantityStore;          // Số lượng tồn kho
    private int status;              // Trạng thái (còn hàng/hết hàng)
    private int idCategory;            // Danh mục
    private int idCompany;            // Danh mục
    private String image;               // Đường dẫn hình ảnh
    private String rom;                 // ROM
    private String nameCategory;
    private String nameCompany;

    // Constructor đầy đủ
    public ProductDTO(int idProduct, String name, String cpu, String sizeScreen, String ram,
                      String graphicsCard, String battery, String operatingSystem, String weight,  BigDecimal price,
                      int quantityStore, int status,
                      int idCategory, int idCompany, String image, String rom, String nameCategory, String nameCompany) {
        this.idProduct = idProduct;
        this.name = name;
        this.cpu = cpu;
        this.sizeScreen = sizeScreen;
        this.ram = ram;
        this.graphicsCard = graphicsCard;
        this.battery = battery;
        this.operatingSystem = operatingSystem;
        this.weight = weight;
        this.price = price;
        this.quantityStore = quantityStore;
        this.status = status;
        this.idCategory = idCategory;
        this.idCompany = idCompany;
        this.image = image;
        this.rom = rom;
        this.nameCategory = nameCategory;
        this.nameCompany = nameCompany;
    }

    // Constructor mặc định
    public ProductDTO() {
    }

    // Getter và Setter cho idProduct
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    // Getter và Setter cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter và Setter cho cpu
    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    // Getter và Setter cho sizeScreen
    public String getSizeScreen() {
        return sizeScreen;
    }

    public void setSizeScreen(String sizeScreen) {
        this.sizeScreen = sizeScreen;
    }

    // Getter và Setter cho ram
    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    // Getter và Setter cho graphicsCard
    public String getGraphicsCard() {
        return graphicsCard;
    }

    public void setGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    // Getter và Setter cho battery
    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    // Getter và Setter cho operatingSystem
    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    // Getter và Setter cho weight
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    // Getter và Setter cho price
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // Getter và Setter cho quantityStore
    public int getQuantityStore() {
        return quantityStore;
    }

    public void setQuantityStore(int quantityStore) {
        this.quantityStore = quantityStore;
    }

    // Getter và Setter cho status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Getter và Setter cho spoiledQuantity
    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    // Getter và Setter cho category
    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    // Getter và Setter cho image
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Getter và Setter cho rom
    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
    public String getNameCompany() {
        return nameCompany;
    }
    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }
    public String getNameStatus(int status) {
        switch (status) {
            case 1:
                return "Đang hoạt động";
            case 0:
                return  "Ngưng hoạt động";
        }
        return "Lỗi";
    }
    // Phương thức toString
    @Override
    public String toString() {
        return "ProductDTO{" +
                "idProduct='" + idProduct + '\'' +
                ", name='" + name + '\'' +
                ", cpu='" + cpu + '\'' +
                ", sizeScreen='" + sizeScreen + '\'' +
                ", ram='" + ram + '\'' +
                ", graphicsCard='" + graphicsCard + '\'' +
                ", battery='" + battery + '\'' +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", weight='" + weight + '\'' +
                ", price=" + price +
                ", quantityStore=" + quantityStore +
                ", status='" + status + '\'' +
                ", idCompany=" + idCompany +
                ", nameCompany=" + nameCompany +
                ", idCategory='" + idCategory + '\'' +
                ", nameCategory=" + nameCategory +
                ", image='" + image + '\'' +
                ", rom='" + rom + '\'' +
                '}';
    }

}