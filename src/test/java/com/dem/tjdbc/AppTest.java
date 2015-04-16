package com.dem.tjdbc;


import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;


public class AppTest extends DBTestCase
{
    private IDatabaseTester databaseTester;

    public AppTest(String name)
    {
        super( name );
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost/emp" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "root" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "emp" );
    }
    @Before
    protected void setUp() throws Exception
    {
        databaseTester = new JdbcDatabaseTester("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost/emp", "root", "root");

        // initialize your dataset here
        IDataSet dataSet = getDataSet();

        databaseTester.setDataSet( dataSet );
        // will call default setUpOperation
        databaseTester.onSetup();
    }

    @Test
    public void testSave() throws Exception {
        System.out.println("intest");
    }


    protected void tearDown() throws Exception
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
