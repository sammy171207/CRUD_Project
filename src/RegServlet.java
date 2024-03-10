import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Reg")
public class RegServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String course = request.getParameter("course");
        String year = request.getParameter("year");

        String user = "root";
        String password = "abc123";
        String url = "jdbc:mysql://localhost:3306/studentdb"; // Removed "user" from here

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            String query = "INSERT INTO user (name, email, course, year) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, course);
            ps.setString(4, year);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                RequestDispatcher rd=request.getRequestDispatcher("/print");
                rd.forward(request, response);
                out.println("<h2>Data inserted successfully</h2>");
                out.println("</body></html>");
            } else {
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h2>Failed to insert data</h2>");
                out.println("</body></html>");
            }
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Handle this more gracefully in production
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h2>Internal Server Error</h2>");
            out.println("</body></html>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
