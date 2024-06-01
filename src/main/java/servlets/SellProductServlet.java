package servlets;

import classes.DBConnection;
import classes.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/sellProduct")
public class SellProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String clientName = request.getParameter("clientName");
        String clientPhone = request.getParameter("clientPhone");

        try {
            DBConnection.addClient(clientName, clientPhone);
            List<Client> clients = DBConnection.getClients();
            Client client = clients.stream()
                    .filter(c -> c.getName().equals(clientName) && c.getPhone().equals(clientPhone))
                    .findFirst()
                    .orElse(null);

            if (client != null) {
                try {
                    DBConnection.addTransaction(productId, client.getId(), quantity);
                    response.sendRedirect("index.jsp");
                } catch (SQLException e) {
                    if (e.getMessage().contains("Not enough product quantity available")) {
                        request.setAttribute("error", "Недостаточно товара на складе.");
                    } else if (e.getMessage().contains("Product not found")) {
                        request.setAttribute("error", "Товар не найден.");
                    } else {
                        request.setAttribute("error", "Ошибка при продаже товара: " + e.getMessage());
                    }
                    request.getRequestDispatcher("sellProduct.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Ошибка при создании клиента.");
                request.getRequestDispatcher("sellProduct.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Ошибка при продаже товара: " + e.getMessage());
            request.getRequestDispatcher("sellProduct.jsp").forward(request, response);
        }
    }
}

