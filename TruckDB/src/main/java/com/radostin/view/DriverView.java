package com.radostin.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DriverView extends JFrame{
    private JTabbedPane myWindows;

    private JTable truckTable;

    private JScrollPane scrollPane1;

    private JButton insertButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton saveButton;

    private JTextField truckBrandField;
    private JTextField truckHorsepowerField;
    private JTextField truckKMField;
    private JCheckBox truckIsElectric;

    private JPanel panel;

    private JTable driverTable;
    private JComboBox comboTrucks;
    private JTextField licenseNumberField;
    private JCheckBox driverIsCertified;

    //private JTabbedPane PanelPestanya;

    //Getters

    public JTabbedPane getMyWindows() {
        return myWindows;
    }

    public JTable getTruckTable() {
        return truckTable;
    }

    public JScrollPane getScrollPane1() {
        return scrollPane1;
    }

    public JButton getInsertButton() {
        return insertButton;
    }

    public JButton getModifyButton() {
        return modifyButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JTextField getTruckBrandField() {
        return truckBrandField;
    }

    public JTextField getTruckHorsepowerField() {
        return truckHorsepowerField;
    }

    public JTextField getTruckKMField() {
        return truckKMField;
    }

    public JCheckBox getTruckIsElectric() {
        return truckIsElectric;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTable getDriverTable() {
        return driverTable;
    }

    public JComboBox getComboTrucks() {
        return comboTrucks;
    }

    public JTextField getLicenseNumberField() {
        return licenseNumberField;
    }

    public JCheckBox getDriverIsCertified() {
        return driverIsCertified;
    }

    //Constructor de la classe
    public DriverView() {
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(false);
    }

        private void createUIComponents() {
        // TODO: place custom component creation code here
        scrollPane1 = new JScrollPane();
        truckTable = new JTable();
        myWindows = new JTabbedPane();
        truckTable.setModel(new DefaultTableModel());
        truckTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane1.setViewportView(truckTable);
    }
}
