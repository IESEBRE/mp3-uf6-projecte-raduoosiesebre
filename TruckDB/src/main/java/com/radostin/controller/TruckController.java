package com.radostin.controller;

import com.radostin.model.entities.Truck;
import com.radostin.model.exceptions.DAOException;
import com.radostin.model.impls.TruckDAO;
import com.radostin.model.type.EngineType;
import com.radostin.view.TruckView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;

public class TruckController {
    private TruckDAO truckDAO;
    private TruckView truckView;
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z ]{1,100}$");

    public TruckController(TruckDAO truckDAO, TruckView truckView) {
        this.truckDAO = truckDAO;
        this.truckView = truckView;

        refreshTable();

        truckView.getInsertButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTruck();
            }
        });

        truckView.getModifyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTruck();
            }
        });

        truckView.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTruck();
            }
        });

        truckView.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTruck();
            }
        });
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) truckView.getTable().getModel();
        model.setRowCount(0);
        try {
            List<Truck> trucks = truckDAO.getAllTrucks();
            for (Truck truck : trucks) {
                model.addRow(new Object[]{truck.getId(), truck.getBrand(), truck.getHorsepower(), truck.getKilometers(), truck.getTruckHasTrailerCheckBox(), truck.getEngineType()});
            }
        } catch (DAOException e) {
            e.printStackTrace();
            showError(e.getMessage());
        }
    }

    private void addTruck() {
        String brand = truckView.getTruckBrandField().getText();
        String horsepower = truckView.getTruckHorsepowerField().getText();
        String km = truckView.getTruckKMField().getText();
        Boolean hasTrailer = truckView.getTruckHasTrailerCheckBox().isSelected();
        EngineType engineType = (EngineType) truckView.getEngineTypeJComboBox().getSelectedItem();

        if (validateName(brand)) {
            try {
                truckDAO.addTruck(brand, Double.parseDouble(horsepower), Integer.parseInt(km), hasTrailer, engineType);
                refreshTable();
            } catch (DAOException e) {
                e.printStackTrace();
                showError(e.getMessage());
            }
        } else {
            showError("Invalid brand format");
        }
    }

    private void updateTruck() {
        int selectedRow = truckView.getTable().getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) truckView.getTable().getValueAt(selectedRow, 0);
            String name = truckView.getTruckBrandField().getText();
            String horsepower = truckView.getTruckHorsepowerField().getText();
            String km = truckView.getTruckKMField().getText();
            Boolean hasTrailer = truckView.getTruckHasTrailerCheckBox().isSelected();
            EngineType engineType = (EngineType) truckView.getEngineTypeJComboBox().getSelectedItem();

            if (validateName(name)) {
                try {
                    truckDAO.updateTruck(id, name, Double.parseDouble(horsepower), Integer.parseInt(km), hasTrailer, engineType);
                    refreshTable();
                } catch (DAOException e) {
                    e.printStackTrace();
                    showError(e.getMessage());
                }
            } else {
                showError("Invalid name format");
            }
        }
    }

    private void deleteTruck() {
        int selectedRow = truckView.getTable().getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) truckView.getTable().getValueAt(selectedRow, 0);
            try {
                truckDAO.deleteTruck(id);
                refreshTable();
            } catch (DAOException e) {
                e.printStackTrace();
                showError(e.getMessage());
            }
        }
    }

    private void saveTruck() {
        // Implement saving logic if needed
        refreshTable();
    }

    private boolean validateName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(truckView, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}