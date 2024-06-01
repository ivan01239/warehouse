package classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:warehouse.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTables() throws SQLException {
        String createProductsTable = "CREATE TABLE IF NOT EXISTS products ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "type TEXT NOT NULL,"
                + "quantity INTEGER NOT NULL,"
                + "supplier_id INTEGER,"
                + "FOREIGN KEY (supplier_id) REFERENCES suppliers(id)"
                + ");";

        String createSuppliersTable = "CREATE TABLE IF NOT EXISTS suppliers ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "phone TEXT NOT NULL"
                + ");";

        String createClientsTable = "CREATE TABLE IF NOT EXISTS clients ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "phone TEXT NOT NULL"
                + ");";

        String createTransactionsTable = "CREATE TABLE IF NOT EXISTS transactions ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "product_id INTEGER NOT NULL,"
                + "client_id INTEGER NOT NULL,"
                + "quantity INTEGER NOT NULL,"
                + "date TEXT NOT NULL,"
                + "FOREIGN KEY (product_id) REFERENCES products(id),"
                + "FOREIGN KEY (client_id) REFERENCES clients(id)"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createProductsTable);
            stmt.execute(createSuppliersTable);
            stmt.execute(createClientsTable);
            stmt.execute(createTransactionsTable);
        }
    }

    public static void addSupplier(String name, String phone) throws SQLException {
        String sql = "INSERT INTO Suppliers(name, phone) VALUES(?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.executeUpdate();
        }
    }

    public static void addProduct(String name, String type, int quantity, int supplierId) throws SQLException {
        String sql = "INSERT INTO Products(name, type, quantity, supplier_id) VALUES(?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setInt(3, quantity);
            pstmt.setInt(4, supplierId);
            pstmt.executeUpdate();
        }
    }

    public static void addClient(String name, String phone) throws SQLException {
        String sql = "INSERT INTO Clients(name, phone) VALUES(?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.executeUpdate();
        }
    }

    public static void addTransaction(int productId, int clientId, int quantity) throws SQLException {
        String transactionSql = "INSERT INTO transactions(product_id, client_id, quantity, date) VALUES(?, ?, ?, datetime('now'))";
        String updateProductSql = "UPDATE products SET quantity = quantity - ? WHERE id = ? AND quantity >= ?";

        try (Connection conn = connect();
             PreparedStatement transactionPstmt = conn.prepareStatement(transactionSql);
             PreparedStatement updateProductPstmt = conn.prepareStatement(updateProductSql)) {

            conn.setAutoCommit(false);

            // Update product quantity
            updateProductPstmt.setInt(1, quantity);
            updateProductPstmt.setInt(2, productId);
            updateProductPstmt.setInt(3, quantity);
            int affectedRows = updateProductPstmt.executeUpdate();

            if (affectedRows == 0) {
                conn.rollback();
                throw new SQLException("Не хватает товара для продажи");
            }

            // Add transaction
            transactionPstmt.setInt(1, productId);
            transactionPstmt.setInt(2, clientId);
            transactionPstmt.setInt(3, quantity);
            transactionPstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            throw e;
        }
    }


    public static List<Product> getAvailableProducts() throws SQLException {
        String sql = "SELECT * FROM Products";
        List<Product> products = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                int quantity = rs.getInt("quantity");
                int supplierId = rs.getInt("supplier_id");
                products.add(new Product(id, name, type, quantity, supplierId));
            }
        }
        return products;
    }

    public static int getProductQuantity(String productName) throws SQLException {
        String sql = "SELECT quantity FROM Products WHERE name = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity");
            }
        }
        return 0;
    }

    public static List<Supplier> getSuppliers() throws SQLException {
        String sql = "SELECT * FROM Suppliers";
        List<Supplier> suppliers = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                suppliers.add(new Supplier(id, name, phone));
            }
        }
        return suppliers;
    }

    public static List<Client> getClients() throws SQLException {
        String sql = "SELECT * FROM Clients";
        List<Client> clients = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                clients.add(new Client(id, name, phone));
            }
        }
        return clients;
    }

    public static List<Transaction> getTransactions() throws SQLException {
        String sql = "SELECT * FROM Transactions";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int productId = rs.getInt("product_id");
                int clientId = rs.getInt("client_id");
                int quantity = rs.getInt("quantity");
                String date = rs.getString("date");
                transactions.add(new Transaction(id, productId, clientId, quantity, date));
            }
        }
        return transactions;
    }
}
