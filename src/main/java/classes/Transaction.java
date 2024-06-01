package classes;

public class Transaction {
    private int id;
    private int productId;
    private int clientId;
    private int quantity;
    private String date;

    public Transaction(int id, int productId, int clientId, int quantity, String date) {
        this.id = id;
        this.productId = productId;
        this.clientId = clientId;
        this.quantity = quantity;
        this.date = date;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

