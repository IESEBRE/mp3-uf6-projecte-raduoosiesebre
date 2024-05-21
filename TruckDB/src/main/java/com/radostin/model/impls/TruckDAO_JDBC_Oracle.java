package com.radostin.model.impls;

import com.radostin.model.daos.DAO;
import com.radostin.model.entities.Truck;
import com.radostin.model.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class TruckDAO_JDBC_Oracle implements DAO<Truck> {
    @Override
    public Truck get(Long id) throws DAOException {

        //Declaració de variables del mètode
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        Truck truck = null;

        //Accés a la BD usant l'API JDBC
        try {

            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//elrado.ddns.net:1521/xe",
                    "C##HR",
                    "HR"
            );
//            st = con.prepareStatement("SELECT * FROM truck WHERE id=?;");
            st = con.createStatement();
//            st = con.prepareStatement("SELECT * FROM truck WHERE id=?;");
//            st.setLong(1, id);
            rs = st.executeQuery("SELECT * FROM TRUCKS;");
//            truck = new Alumne(rs.getLong(1), rs.getString(2));
//            st.close();
            if (rs.next()) {
                truck = new Truck(Long.valueOf(rs.getString(1)), rs.getString(2));
            }
        } catch (SQLException throwables) {
            throw new DAOException(1);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new DAOException(1);
            }

        }
        return truck;
    }

    @Override
    public List<Truck> getAll() throws DAOException {
        //Declaració de variables del mètode
        List<Truck> trucks = new ArrayList<>();

        //Accés a la BD usant l'API JDBC
        try (Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@//elrado.ddns.net:1521/xe",
                "C##HR",
                "HR"
        );
             PreparedStatement st = con.prepareStatement("SELECT * FROM TRUCKS");
             ResultSet rs = st.executeQuery();
        ) {

            while (rs.next()) {
                trucks.add(new Truck(rs.getLong("id"), rs.getString("brand"),rs.getDouble("horsepower"), rs.getInt("km"),
                        new TreeSet<Truck.Driver>()));
            }
        } catch (SQLException throwables) {
            int tipoError = throwables.getErrorCode();
            //System.out.println(tipoError+" "+throwables.getMessage());
            switch(throwables.getErrorCode()){
                case 17002: //l'he obtingut posant un sout en el throwables.getErrorCode()
                    tipoError = 0;
                    break;
                default:
                    tipoError = 1;  //error desconegut
            }
            throw new DAOException(tipoError);
        }
        return trucks;
    }

    @Override
    public void save(Truck obj) throws DAOException {

    }
}
