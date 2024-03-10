import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String course = request.getParameter("course");
        int year = Integer.parseInt(request.getParameter("year"));

        try (Connection con = DatabaseUtil.getConnection()) {
            String query = "INSERT INTO user (name, email, course, year) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, course);
                ps.setInt(4, year);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    response.sendRedirect(request.getContextPath() + "/print");
                } else {
                    response.getWriter().println("Failed to register student.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            response.getWriter().println("An error occurred while registering student.");
        }
    }
}
