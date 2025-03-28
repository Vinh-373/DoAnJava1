package DTO;

import java.math.BigDecimal;

public class ProductDTO {
    private String idProduct;           // ID sản phẩm
    private String name;                // Tên sản phẩm
    private String cpu;                 // CPU
    private String ram;                 // RAM
    private String rom;                 // ROM
    private String graphicsCard;        // Card đồ họa
    private String battery;             // Pin
    private String weight;              // Trọng lượng
    private BigDecimal priceStore;      // Giá bán
    private int quantityStore;          // Số lượng tồn kho
    private boolean statusStore;        // Trạng thái (còn hàng/hết hàng)
    private int brokenQuantityStore;    // Số lượng hỏng
    private String category;            // Danh mục
    private String image;               // Đường dẫn hình ảnh
    private String operatingSystem;     // Hệ điều hành
    private String origin;              // Xuất xứ

    // Constructor đầy đủ
    public ProductDTO(String idProduct, String name, String cpu, String ram, String rom,
                      String graphicsCard, String battery, String weight, BigDecimal priceStore,
                      int quantityStore, boolean statusStore, int brokenQuantityStore,
                      String category, String image, String operatingSystem, String origin) {
        this.idProduct = idProduct;
        this.name = name;
        this.cpu = cpu;
        this.ram = ram;
        this.rom = rom;
        this.graphicsCard = graphicsCard;
        this.battery = battery;
        this.weight = weight;
        this.priceStore = priceStore;
        this.quantityStore = quantityStore;
        this.statusStore = statusStore;
        this.brokenQuantityStore = brokenQuantityStore;
        this.category = category;
        this.image = image;
        this.operatingSystem = operatingSystem;
        this.origin = origin;
    }

    // Constructor mặc định
    public ProductDTO() {
    }

    // Getter và Setter
    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getGraphicsCard() {
        return graphicsCard;
    }

    public void setGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public BigDecimal getPriceStore() {
        return priceStore;
    }

    public void setPriceStore(BigDecimal priceStore) {
        this.priceStore = priceStore;
    }

    public int getQuantityStore() {
        return quantityStore;
    }

    public void setQuantityStore(int quantityStore) {
        this.quantityStore = quantityStore;
    }

    public boolean getStatusStore() {
        return statusStore;
    }

    public void setStatusStore(boolean statusStore) {
        this.statusStore = statusStore;
    }

    public int getBrokenQuantityStore() {
        return brokenQuantityStore;
    }

    public void setBrokenQuantityStore(int brokenQuantityStore) {
        this.brokenQuantityStore = brokenQuantityStore;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    // Phương thức toString để hỗ trợ debug
    @Override
    public String toString() {
        return "ProductDTO{" +
                "idProduct='" + idProduct + '\'' +
                ", name='" + name + '\'' +
                ", cpu='" + cpu + '\'' +
                ", ram='" + ram + '\'' +
                ", rom='" + rom + '\'' +
                ", graphicsCard='" + graphicsCard + '\'' +
                ", battery='" + battery + '\'' +
                ", weight='" + weight + '\'' +
                ", priceStore=" + priceStore +
                ", quantityStore=" + quantityStore +
                ", statusStore=" + statusStore +
                ", brokenQuantityStore=" + brokenQuantityStore +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", origin='" + origin + '\'' +
                '}';
    }
}