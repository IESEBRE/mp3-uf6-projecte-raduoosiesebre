package org.radostin.mvc.view;

import javax.swing.*;

public class View extends JFrame{

    //Dispose option allows to close the popping windows
    public static void disposeOnClose(){
        SwingUtilities.getWindowAncestor(null).dispose();
    }

    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTable driversTable;
    private JLabel driverNameLabel;
    private JLabel driverSurnameLabel;
    private JTextField driverNameField;
    private JTextField driverSurnameField;
    private JComboBox trucksToOwn;
    private JComboBox truckSelectionForDriver;
    private JCheckBox driverIsCertificated;

    private JTable truckTable;
    private JLabel truckBrandLabel;
    private JLabel truckHorsepowerLabel;
    private JLabel truckKMLabel;
    private JLabel truckHybridLabel;
    private JCheckBox isHybridCB;
    private JTextField truckBrandField;
    private JTextField truckHorsepowerField;
    private JTextField truckKMField;
    private JButton truckInsertButton;
    private JButton truckModifyButton;
    private JButton truckDeleteButton;
    private JButton truckSaveButton;

    private JPanel TruckDB;
    private JPanel DriversDB;

    //The TruckDB methods
        public JTable getTable(){
            return truckTable;
        }

        public JButton getInsertButton(){
            return truckInsertButton;
        }

        public JTextField getTruckBrandField(){
            return truckBrandField;
        }

        public JTextField getTruckHorsepowerField(){
            return truckHorsepowerField;
        }

        public JTextField getTruckKMField(){
            return truckKMField;
        }

        public JCheckBox getIsHybridCB(){
            return isHybridCB;
        }

        public JButton getModifyButton(){
            return truckModifyButton;
        }

        public JButton getDeleteButton(){
            return truckDeleteButton;
        }

        public JButton getSaveButton(){
            return truckSaveButton;
        }

        public JTabbedPane getTabbedPane1(){
            return tabbedPane1;
        }


    //The DriversDB methods
        public JTable getDriversTable() {
            return driversTable;
        }

        public JTextField getDriverNameField() {
            return driverNameField;
        }

        public JTextField getDriverSurnameField() {
            return driverSurnameField;
        }

        public JCheckBox getDriverIsCertificated() {
            return driverIsCertificated;
        }

        public JComboBox getTrucksToOwn(){
            return trucksToOwn;
        }

        public JComboBox getTruckSelectionForDriver() {
            return truckSelectionForDriver;
        }

    public View(){
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
