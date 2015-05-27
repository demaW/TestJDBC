package com.dem.tjdbc;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import utils.PropertyReader;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class SalaryTest extends DatabaseTestCase {
    private static String ORACLEPROP = "propertiesOracle";
    private static String MYSQLPROP = "propertiesMySql";

    private SalaryCalculation salaryCalculation;
    private Connection jdbcConnection;
    private FlatXmlDataSet dataSet;

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        // read properties from file
        HashMap<String, String> prop =  PropertyReader.getProperties(ORACLEPROP);

        Class.forName(prop.get("dbClass"));
        jdbcConnection = DriverManager.getConnection(prop.get("dbURL"), prop.get("user"), prop.get("pass"));
        if (prop.get("schema") != null && !prop.get("schema").isEmpty())
            return new DatabaseConnection(jdbcConnection, prop.get("schema"));
        else
            return new DatabaseConnection(jdbcConnection);
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(new File("src\\test\\resources\\salary_dataset.xml"));
        return dataSet;
    }

    /**
     * Test case for calculator *negative scenario---InValid Employee
     */
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
