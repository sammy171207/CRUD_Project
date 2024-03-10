import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/edit")
public class EditStudent extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String course = request.getParameter("course");
        int year = Integer.parseInt(request.getParameter("year"));

        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "UPDATE user SET name=?, email=?, course=?, year=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, course);
                preparedStatement.setInt(4, year);
                preparedStatement.setInt(5, id);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    response.sendRedirect(request.getContextPath() + "/print");
                } else {
                    response.getWriter().println("Failed to update student information.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            response.getWriter().println("An error occurred while updating student information.");
        }
    }
}
