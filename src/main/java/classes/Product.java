package classes;

public class Product {
    private int id;
    private String name;
    private String type;
    private int quantity;
    private int supplierId;

    public Product(int id, String name, String type, int quantity, int supplierId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.supplierId = supplierId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
}
