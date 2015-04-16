package com.dem.tjdbc;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

/**
 * Hello world!
 */
public class App {
    public static Connection connection = null;
    public static void main(String[] args) {
        String cityName;
        String companyName;

        System.out.println("Hello World!");

        String dbURL = "jdbc:mysql://localhost/beer_storage";
        String dbClass = "com.mysql.jdbc.Driver";
        String user = "root";
        String pass = "root";
        String querySelect = "SELECT * FROM beer_storage.beer_suplier;";
        try {
            Class.forName(dbClass);
            connection = DriverManager.getConnection(dbURL, user, pass);
              BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("enter company name, please: ");
            companyName = bufferedReader.readLine();
            System.out.println("enter city");
            cityName = bufferedReader.readLine();

            //insertCompany(connection, companyName, cityName);

            ResultSet resultSet = PrintSuppliersTable(querySelect);

            resultSet.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static ResultSet PrintSuppliersTable(String querySelect) throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(querySelect);

        while (resultSet.next()) {
            String ID = resultSet.getString(1);
            String name = resultSet.getString(2);
            String city = resultSet.getString(3);

            System.out.print("ID : " + ID + " | ");
            System.out.print("name: " + name + " | ");
            System.out.print("city: " + city + " | ");
            System.out.println();

        }
        return resultSet;
    }

    private static void insertCompany(Connection connection, String company, String city) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT INTO beer_suplier (NAME, CITY) values( ?, ?)");
        preparedStatement.setString(1, company);
        preparedStatement.setString(2, city);
        preparedStatement.execute();
    }
}
