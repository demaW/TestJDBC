package com.dem.tjdbc;

import junit.framework.TestCase;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;


public class DBTest {

    private IDatabaseTester databaseTester;

    @Before
    public void setUp() throws Exception
    {
        databaseTester = new JdbcDatabaseTester("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@localhost:1521:emp", "system","181992", "SYSTEM");

        // databaseTester = new JdbcDatabaseTester("com.mysql.jdbc.Driver",
        //        "jdbc:oracle:thin:@://localhost/emp", "root", "root");

        // initialize your dataset here
        IDataSet dataSet = getDataSet();

        databaseTester.setDataSet( dataSet );
        // will call default setUpOperation
        databaseTester.onSetup();
    }

    @Test
    public void testSave() throws Exception {
        // Fetch database data after executing your code
        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("employees2");
        SortedTable sortedActualTable = new SortedTable(actualTable, new String[]{"ID"});

        // Load expected data from an XML dataset
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src\\test\\resources\\dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable("employees");


        // Assert actual database table match expected table
        Assertion.assertEquals(expectedTable, sortedActualTable);
    }


    @After
    public void tearDown() throws Exception
    {
        // will call default tearDownOperation
        databaseTester.onTearDown();
    }


    protected IDataSet getDataSet() throws Exception
    {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("src\\test\\resources\\dataset.xml"));
    }

    protected DatabaseOperation getSetUpOperation() throws Exception
    {
        return DatabaseOperation.CLEAN_INSERT;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception
    {
        return DatabaseOperation.NONE;
    }
}
