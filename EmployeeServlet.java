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

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/yourdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "yourpassword";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Employee List</title></head><body>");
        out.println("<h2>Employee List</h2>");
        String empId = request.getParameter("empId");
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if (empId != null && !empId.trim().isEmpty()) {
                String searchQuery = "SELECT id, name, department FROM employees WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(searchQuery)) {
                    pstmt.setInt(1, Integer.parseInt(empId));
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        out.println("<h3>Employee Details:</h3>");
                        out.println("<p>ID: " + rs.getInt("id") + "</p>");
                        out.println("<p>Name: " + rs.getString("name") + "</p>");
                        out.println("<p>Department: " + rs.getString("department") + "</p>");
                    } else {
                        out.println("<p>No employee found with ID: " + empId + "</p>");
                    }
                }
            } else {
                String query = "SELECT id, name, department FROM employees";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    ResultSet rs = pstmt.executeQuery();
                    out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Department</th></tr>");
                    while (rs.next()) {
                        out.println("<tr>");
                        out.println("<td>" + rs.getInt("id") + "</td>");
                        out.println("<td>" + rs.getString("name") + "</td>");
                        out.println("<td>" + rs.getString("department") + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error connecting to the database: " + e.getMessage() + "</p>");
        }
        out.println("<h3>Search Employee by ID:</h3>");
        out.println("<form method='get' action='EmployeeServlet'>");
        out.println("Employee ID: <input type='text' name='empId'>");
        out.println("<input type='submit' value='Search'>");
        out.println("</form>");
        out.println("</body></html>");
    }
}