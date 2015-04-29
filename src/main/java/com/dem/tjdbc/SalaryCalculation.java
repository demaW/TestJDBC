package com.dem.tjdbc;

import java.sql.*;

public class SalaryCalculation {
    private String dbURL = "jdbc:mysql://localhost/emp";
    private String user = "root";
    private String pass = "root";

    private static String SELECT_STATEMENT="select * from salary_details where empID='";

    private Connection getConnection() throws SQLException {
       return DriverManager.getConnection(dbURL, user,pass);
    }

    public double calculateSalary(String empID) throws SQLException {
        Statement statement = getConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(SELECT_STATEMENT + empID + "'");

        double salary = 0.00;
        double bonus = 0.00;
        double increase = 0.00;

        while (resultSet.next()){
            salary = resultSet.getDouble("Salary");
            bonus = resultSet.getDouble("Bonus");
            increase = resultSet.getDouble("increase");
        }
        return (salary+bonus+increase);
    }
}
