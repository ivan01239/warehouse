package servlets;

import classes.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/addProduct")
public class AddProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int supplierId = Integer.parseInt(request.getParameter("supplierId"));

        try {
            DBConnection.addProduct(name, type, quantity, supplierId);
            response.sendRedirect("index.jsp");
        } catch (SQLException e) {
            throw new ServletException("Error adding product", e);
        }
    }
}

