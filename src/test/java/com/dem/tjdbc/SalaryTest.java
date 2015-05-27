package com.dem.tjdbc;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SalaryTest extends DatabaseTestCase {
    private String dbURLOracle = "jdbc:oracle:thin:@localhost:1521:emp";
    private String userOracle = "system";
    private String passOracle = "181992";
    private String dbClassOracle = "oracle.jdbc.driver.OracleDriver";

    private String dbURL = "jdbc:mysql://localhost/emp";
    private String user = "root";
    private String pass = "root";
    private String dbClass = "com.mysql.jdbc.Driver";
    private SalaryCalculation salaryCalculation;
    private Connection jdbcConnection;
    private FlatXmlDataSet dataSet;

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        Class.forName(dbClass);
        jdbcConnection = DriverManager.getConnection(dbURLOracle, userOracle, passOracle);
        return new DatabaseConnection(jdbcConnection,"SYSTEM");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(new File("src\\test\\resources\\salary_dataset.xml"));
        return dataSet;
    }

    /** *Test case for calculator *negative scenario---InValid Employee */
    @Test
    public void testCalculation() {
        salaryCalculation = new SalaryCalculation();
        double salary = 0;
        try {
            salary = salaryCalculation.calculateSalary("2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(20253.4, salary);
    }

    @Test
    public void testCalculationNegative() {
        salaryCalculation = new SalaryCalculation();
        double salary = 0;
        try {
            salary = salaryCalculation.calculateSalary("226");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(0.00, salary);
    }
}
