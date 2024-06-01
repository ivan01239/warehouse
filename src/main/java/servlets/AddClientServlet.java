package servlets;

import classes.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/addClient")
public class AddClientServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");

        try {
            DBConnection.addClient(name, phone);
            response.sendRedirect("index.jsp");
        } catch (SQLException e) {
            throw new ServletException("Error adding client", e);
        }
    }
}

