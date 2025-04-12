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

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/yourdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "yourpassword";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String attendanceDate = request.getParameter("attendanceDate");
        String status = request.getParameter("status");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String insertSQL = "INSERT INTO attendance (student_id, attendance_date, status) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, studentId);
            pstmt.setString(2, attendanceDate);
            pstmt.setString(3, status);
            int rowCount = pstmt.executeUpdate();
            if (rowCount > 0) {
                out.println("<html><head><title>Attendance Saved</title></head><body>");
                out.println("<h2>Attendance record added successfully!</h2>");
                out.println("<a href='attendance.jsp'>Enter Another Record</a>");
                out.println("</body></html>");
            } else {
                out.println("Error: Unable to save attendance record.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error connecting to the database: " + e.getMessage() + "</p>");
        }
    }
}