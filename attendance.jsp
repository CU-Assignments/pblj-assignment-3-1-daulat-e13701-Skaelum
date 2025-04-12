<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Student Attendance Portal</title>
</head>
<body>
  <h2>Enter Attendance Details</h2>
  <form action="AttendanceServlet" method="post">
    Student ID: <input type="text" name="studentId" required><br>
    Date (YYYY-MM-DD): <input type="text" name="attendanceDate" required><br>
    Status: 
    <select name="status">
      <option value="present">Present</option>
      <option value="absent">Absent</option>
    </select><br>
    <input type="submit" value="Submit Attendance">
  </form>
</body>
</html>