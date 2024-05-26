package com.radostin.app;

import com.radostin.controller.TruckController;
import com.radostin.model.exceptions.DAOException;
import com.radostin.model.impls.TruckDAO;
import com.radostin.view.TruckView;

public class Main {
    public static void main(String[] args) {
        try {
            TruckDAO truckDAO = new TruckDAO();
            TruckView truckView = new TruckView();
            new TruckController(truckDAO, truckView);

            truckView.setVisible(true);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}