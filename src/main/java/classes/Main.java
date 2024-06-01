package classes;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            DBConnection.createTables();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Добро пожаловать в систему управления складом!");

        while (!exit) {
            System.out.println("\nВыберите команду:");
            System.out.println("1. Добавить товар");
            System.out.println("2. Отпустить товар покупателю");
            System.out.println("3. Показать доступные товары");
            System.out.println("4. Показать количество товара");
            System.out.println("5. Группировать товары по видам");
            System.out.println("6. Показать поставщиков");
            System.out.println("7. Показать покупателей");
            System.out.println("8. Добавить поставщика");
            System.out.println("9. Выйти");

            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    addProduct(scanner);
                    break;
                case "2":
                    sellProduct(scanner);
                    break;
                case "3":
                    showAvailableProducts();
                    break;
                case "4":
                    showProductQuantity(scanner);
                    break;
                case "5":
                    groupProductsByType();
                    break;
                case "6":
                    showSuppliers();
                    break;
                case "7":
                    showClients();
                    break;
                case "8":
                    addSupplier(scanner);
                    break;
                case "9":
                    exit = true;
                    break;
                default:
                    System.out.println("Неверная команда, попробуйте снова.");
            }
        }

        scanner.close();
        System.out.println("Выход из программы.");
    }

    private static void addProduct(Scanner scanner) {
        System.out.println("Введите название товара:");
        String name = scanner.nextLine();
        System.out.println("Введите тип товара:");
        String type = scanner.nextLine();
        System.out.println("Введите количество товара:");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите ID поставщика:");
        int supplierId = Integer.parseInt(scanner.nextLine());

        try {
            DBConnection.addProduct(name, type, quantity, supplierId);
            System.out.println("Товар добавлен.");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении товара: " + e.getMessage());
        }
    }

    private static void sellProduct(Scanner scanner) {
        System.out.println("Введите ID товара:");
        int productId = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите количество:");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите имя клиента:");
        String clientName = scanner.nextLine();
        System.out.println("Введите телефон клиента:");
        String clientPhone = scanner.nextLine();

        try {
            DBConnection.addClient(clientName, clientPhone);
            List<Client> clients = DBConnection.getClients();
            Client client = clients.stream().filter(c -> c.getName().equals(clientName) && c.getPhone().equals(clientPhone)).findFirst().orElse(null);

            if (client != null) {
                DBConnection.addTransaction(productId, client.getId(), quantity);
                System.out.println("Товар отпущен клиенту.");
            } else {
                System.out.println("Ошибка при создании клиента.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при продаже товара: " + e.getMessage());
        }
    }

    private static void showAvailableProducts() {
        try {
            List<Product> products = DBConnection.getAvailableProducts();
            System.out.println("Доступные товары:");
            for (Product product : products) {
                System.out.println(product.getName() + ": " + product.getQuantity() + " шт.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении доступных товаров: " + e.getMessage());
        }
    }

    private static void showProductQuantity(Scanner scanner) {
        System.out.println("Введите название товара:");
        String productName = scanner.nextLine();
        try {
            int quantity = DBConnection.getProductQuantity(productName);
            System.out.println("Количество товара " + productName + ": " + quantity + " шт.");
        } catch (SQLException e) {
            System.out.println("Ошибка при получении количества товара: " + e.getMessage());
        }
    }

    private static void groupProductsByType() {
        try {
            List<Product> products = DBConnection.getAvailableProducts();
            System.out.println("Группировка товаров по видам:");
            products.stream().collect(Collectors.groupingBy(Product::getType)).forEach((type, groupedProducts) -> {
                System.out.println("Тип: " + type);
                for (Product product : groupedProducts) {
                    System.out.println(" - " + product.getName() + ": " + product.getQuantity() + " шт.");
                }
            });
        } catch (SQLException e) {
            System.out.println("Ошибка при группировке товаров: " + e.getMessage());
        }
    }

    private static void showSuppliers() {
        try {
            List<Supplier> suppliers = DBConnection.getSuppliers();
            System.out.println("Поставщики:");
            for (Supplier supplier : suppliers) {
                System.out.println(supplier.getName() + ": " + supplier.getPhone());
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении поставщиков: " + e.getMessage());
        }
    }

    private static void showClients() {
        try {
            List<Client> clients = DBConnection.getClients();
            System.out.println("Клиенты:");
            for (Client client : clients) {
                System.out.println(client.getName() + ": " + client.getPhone());
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении клиентов: " + e.getMessage());
        }
    }

    private static void addSupplier(Scanner scanner) {
        System.out.println("Введите имя поставщика:");
        String name = scanner.nextLine();
        System.out.println("Введите телефон поставщика:");
        String phone = scanner.nextLine();

        try {
            DBConnection.addSupplier(name, phone);
            System.out.println("Поставщик добавлен.");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении поставщика: " + e.getMessage());
        }
    }
}
