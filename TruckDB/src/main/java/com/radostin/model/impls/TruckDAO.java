package com.radostin.model.impls;

import com.radostin.model.entities.Truck;
import com.radostin.model.exceptions.DAOException;
import com.radostin.model.type.EngineType;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TruckDAO {
    private Connection connection;

    public TruckDAO() throws DAOException {
        try {
            Properties props = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
            if (input == null) {
                throw new DAOException("Unable to find db.properties");
            }
            props.load(input);

            String driver = props.getProperty("db.driver");
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new DAOException("Failed to connect to database", e);
        }
    }

    public List<Truck> getAllTrucks() throws DAOException {
        List<Truck> trucks = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT id, brand, horsepower, kilometers, hasTrailer, engineType FROM trucks"
            );
            while (rs.next()) {
                trucks.add(new Truck(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getDouble("horsepower"),
                        rs.getInt("kilometers"),
                        rs.getBoolean("hasTrailer"),
                        EngineType.valueOf(rs.getString("engineType"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Failed to retrieve trucks", e);
        }
        return trucks;
    }

    public void addTruck(String brand, Double horsepower, Integer kilometers, Boolean hasTrailer, EngineType type) throws DAOException {
        try {
            PreparedStatement pstmt = connection.prepareStatement("{call insert_truck(?, ?, ?, ?, ?)}");
            pstmt.setString(1, brand);
            pstmt.setDouble(2, horsepower);
            pstmt.setInt(3, kilometers);
            pstmt.setBoolean(4, hasTrailer);
            pstmt.setString(5, type.name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Failed to add truck", e);
        }
    }

    public void updateTruck(int id, String brand, Double horsepower, Integer kilometers, Boolean hasTrailer, EngineType type) throws DAOException {
        try {
            PreparedStatement pstmt = connection.prepareStatement("CALL update_truck(?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, id);
            pstmt.setString(2, brand);
            pstmt.setDouble(3, horsepower);
            pstmt.setInt(4, kilometers);
            pstmt.setBoolean(5, hasTrailer);
            pstmt.setString(6, type.name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Failed to update truck", e);
        }
    }

    public void deleteTruck(int id) throws DAOException {
        try {
            PreparedStatement pstmt = connection.prepareStatement("CALL delete_truck(?)");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Failed to delete truck", e);
        }
    }
}
