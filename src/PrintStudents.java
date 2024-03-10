import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
    private static final String URL = "jdbc:mysql://localhost:3306/StudentDB";
    private static final String USER = "root";
    private static final String PASSWORD = "abc123";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT * FROM user";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            out.println("<html><head><title>Student List</title>");
            out.println("<style>");
            out.println("table { width: 100%; border-collapse: collapse; }");
            out.println("th, td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println("tr:hover { background-color: #f5f5f5; }");
            out.println(".edit, .delete { padding: 5px 10px; background-color: #007bff; color: white; border: none; cursor: pointer; margin-right: 5px; }");
            out.println("</style></head><body>");
            out.println("<h2>Student List</h2>");
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Course</th><th>Year</th><th>Edit</th><th>Delete</th></tr>");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String course = resultSet.getString("course");
                int year = resultSet.getInt("year");
                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("<td>" + course + "</td>");
                out.println("<td>" + year + "</td>");
                out.println("<td><button class='edit' onclick='editStudent(" + id + ")'>Edit</button></td>");
                out.println("<td><button class='delete' onclick='deleteStudent(" + id + ")'>Delete</button></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<script>");
            out.println("function editStudent(id) { window.location.href = 'edit?id=' + id; }");
            out.println("function deleteStudent(id) { if (confirm('Are you sure you want to delete this student?')) window.location.href = 'delete?id=' + id; }");
            out.println("</script>");
            out.println("</body></html>");
        } catch (SQLException ex) {
            ex.printStackTrace();
            out.println("An error occurred while fetching student data.");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
