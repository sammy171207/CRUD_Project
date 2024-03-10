import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/print")
public class PrintStudents extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM user";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                // Process resultSet and display student list
                while (resultSet.next()) {
                    // Retrieve data from resultSet and format as HTML to display student list
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            response.getWriter().println("An error occurred while fetching student data.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Method not supported
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
