import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/edit")
public class EditStudent extends HttpServlet {

    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/StudentDB";
    private static final String USER = "root";
    private static final String PASSWORD = "abc123";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
        PrintWriter out= response.getWriter();

        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                // Retrieve existing student information from database based on ID
                // You would typically fetch the existing information and pre-populate the form fields for the user to edit
                // For demonstration purposes, we'll simply print the student ID here
                out.print("<!DOCTYPE html>");
                out.print("<html lang=\"en\">");
                out.print("<head>");
                out.print("<meta charset=\"UTF-8\">");
                out.print("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.print("<title>Edit Student Information</title>");
                out.print("<style>");
                out.print("body { font-family: Arial, sans-serif;}");
                out.print("form { max-width: 400px; margin: 0 auto;}");
                out.print("input[type='text'], input[type='email'], input[type='number'] { width: 100%; padding: 8px; margin-bottom: 10px; box-sizing: border-box;}");
                out.print("input[type='submit'] { background-color: #007bff; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer;}");
                out.print("input[type='submit']:hover { background-color: #0056b3; }");
                out.print("</style>");
                out.print("</head>");
                out.print("<body>");
                out.print("<h2>Edit Student Information</h2>");
                out.print("<form action='edit' method='post'>");
                out.print("<input type='hidden' name='id' value='" + id + "'>");
                out.print("Name: <input type='text' name='name'><br>");
                out.print("Email: <input type='email' name='email'><br>");
                out.print("Course: <input type='text' name='course'><br>");
                out.print("Year: <input type='number' name='year'><br>");
                out.print("<input type='submit' value='Update'>");
                out.print("</form>");
                out.print("</body>");
                out.print("</html>");
            } catch (NumberFormatException e) {
                // Handle invalid or missing ID parameter
                out.println("Invalid student ID.");
            }
        } else {
            out.println("Student ID is missing.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String course = request.getParameter("course");
        int year = Integer.parseInt(request.getParameter("year"));

        // Perform update operation in the database
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "UPDATE user SET name=?, email=?, course=?, year=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, course);
                preparedStatement.setInt(4, year);
                preparedStatement.setInt(5, id);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    response.sendRedirect("/print"); // Redirect to success page after update
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
