package org.radostin.mvc.controller;

import org.radostin.mvc.app.MyException;
import org.radostin.mvc.model.Model;
import org.radostin.mvc.model.SuperCollection;
import org.radostin.mvc.model.Truck;
import org.radostin.mvc.model.Truck.Driver;
import org.radostin.mvc.view.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static org.radostin.mvc.model.Files.FILE;
import static org.radostin.mvc.view.View.disposeOnClose;

public class Controller implements PropertyChangeListener {

    public static final String P_Exception = "exception";
    private MyException exception;

    public MyException getException(){
        return exception;
    }

    public void settException(MyException exception){
        MyException oldValue = this.exception;
        this.exception = exception;
        changes.firePropertyChange(P_Exception, oldValue, exception);
    }

    PropertyChangeSupport changes = new PropertyChangeSupport(this);

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        MyException received = (MyException) evt.getNewValue();
        JTextField TruckBrandField = view.getTruckBrandField();
        JTextField TruckHorsepowerField = view.getTruckHorsepowerField();
        JTextField TruckKMField = view.getTruckKMField();
        JCheckBox IsHybridCB = view.getIsHybridCB();

        try {
            throw received;
        } catch (MyException e){
            switch (evt.getPropertyName()){
                case P_Exception:
                    if (received.getCode() == 1){
                        JOptionPane.showConfirmDialog(null, received.getMessage());
                    } else if (received.getCode() == 2) {
                        JOptionPane.showMessageDialog(null, received.getMessage());
                        TruckBrandField.setSelectionStart(0);
                        TruckBrandField.setSelectionEnd(TruckBrandField.getText().length());
                        //TruckBrandField.requestFocus();
                        TruckHorsepowerField.setSelectionStart(0);
                        TruckHorsepowerField.setSelectionStart(TruckHorsepowerField.getText().length());
                        //TruckHorsepowerField.requestFocus();
                        TruckKMField.setSelectionStart(0);
                        TruckKMField.setSelectionStart(TruckKMField.getText().length());
                        //TruckKMField.requestFocus();
                    }
            }
        }
    }

    private Model model;
    private View view;

    private SuperCollection superCollection(){
        return null;
    }

    public Controller(Model model, View view) throws MyException{
        this.model = model;
        this.view = view;

        //Method for the view and the model
        viewModel();

        //Assignation of the code of listeners
        assignCodeListeners();

        //Collections
        SuperCollection<Truck> superCollection = new SuperCollection<>(SuperCollection.Type.ONE);

        //Changes
        changes.addPropertyChangeListener(this);
    }

    //Add truck to collection method
    public void addTruckToCollection(String TruckBrandField, double TruckHorsepowerField, int TruckKMField, boolean isHybridCB){
        Truck truck = new Truck(TruckBrandField, TruckHorsepowerField, TruckKMField, isHybridCB);
        superCollection().addTruck(truck);
    }

    //Remove truck from collection method
    public void removeTruckFromCollection(Truck truck){
        superCollection().removeTruck(truck);
    }

    private void assignCodeListeners() throws MyException{
        Model modelo = this.model;
        DefaultTableModel model = this.model.getModel();

        JButton InsertButton = view.getInsertButton();
        JButton DeleteButton = view.getDeleteButton();
        JButton ModifyButton = view.getModifyButton();
        JButton SaveButton = view.getSaveButton();

        JTextField TruckBrandField = view.getTruckBrandField();
        JTextField TruckHorsepowerField = view.getTruckHorsepowerField();
        JTextField TruckKMField = view.getTruckKMField();
        JCheckBox isHybridCB = view.getIsHybridCB();

        JTable table = view.getTable();


        //Insert Button function.
        InsertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TruckBrandField.getText().isBlank() || TruckHorsepowerField.getText().isBlank() || TruckKMField.getText().isBlank()){
                    JOptionPane.showMessageDialog(null, "Some information is missing.");
                } else {
                    try{
                        // We parse for the double values of the horsepower.
                        NumberFormat num = NumberFormat.getNumberInstance(Locale.getDefault());
                        double Horsepower = num.parse(TruckHorsepowerField.getText().trim()).doubleValue();

                        // If the horsepower is less than 200 and more than 10.000, throw an exception.
                        if (Horsepower<200.00 || Horsepower>10000.00) {
                            throw new ParseException("", 0);
                        }

                        // Checking for duplicates.
                        boolean isDuplicate = false;
                        for (int i = 0; i < model.getRowCount(); i++) {
                            String Brand = (String) model.getValueAt(i, 0);
                            double HorsePower = (double) model.getValueAt(i, 1);
                            int km = (int) model.getValueAt(i, 2);
                            boolean isHybrid = (boolean) model.getValueAt(i, 3);

                            if (TruckBrandField.getText().equals(Brand)
                                    && HorsePower == Horsepower
                                    && Integer.parseInt(TruckKMField.getText()) == km
                                    && isHybridCB.isSelected() == isHybrid){

                                JOptionPane.showMessageDialog(null, "Duplicate trucks found!");
                                isDuplicate = true;
                                throw new RuntimeException("Throw");


                            }
                        }

                        // If is not duplicate, allow inserting to the table.
                        if (!isDuplicate){
                            model.addRow(new Object[]{
                                    TruckBrandField.getText(),
                                    Horsepower,
                                    Integer.parseInt(TruckKMField.getText()),
                                    isHybridCB.isSelected()
                            });
                        }

                        //AutoRefill the fields later
                        TruckBrandField.setText("Mercedes");
                        TruckBrandField.setSelectionStart(0);
                        TruckBrandField.setSelectionEnd(TruckBrandField.getText().length());
                        TruckHorsepowerField.setText("1250");
                        TruckBrandField.requestFocus();
                    } catch (ParseException ex){
                        //Exception throws if the user types invalid horsepower.
                        JOptionPane.showMessageDialog(null, "You have to enter a valid horsepower bigger than 200 and less than 10.000!");
                        TruckHorsepowerField.setSelectionStart(0);
                        TruckHorsepowerField.setSelectionEnd(TruckHorsepowerField.getText().length());
                        TruckHorsepowerField.requestFocus();
                    } catch (RuntimeException ex){
                        //Exception thrown if duplicate trucks found
                        System.out.println("Caught RuntimeException: " + ex.getMessage());
                        JOptionPane.showMessageDialog(null, "Duplicate trucks found!");
                    }
                }
            }
        });

        //The modify button.
        ModifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //We look if we have selected a row in the table
                int rowSelect = table.getSelectedRow();

                if (rowSelect != -1) {
                    //Before to modify, we check that we have the fields filled.
                    //We check that all the fields contain information
                    if (TruckBrandField.getText().isBlank() || TruckHorsepowerField.getText().isBlank() || TruckKMField.getText().isBlank()) {
                        JOptionPane.showMessageDialog(null, "Some information is missing!!");
                    } else {
                        try{
                            int TruckKM = Integer.parseInt(TruckKMField.getText());
                            double TruckHorsepower = Double.parseDouble(TruckHorsepowerField.getText());

                            //We check for duplicates.
                            boolean isDuplicate = false;
                            for (int i = 0; i < model.getRowCount(); i++) {
                                if (i != rowSelect) { // Skip the currently selected row
                                    String brand = (String) model.getValueAt(i, 0);
                                    double horsepower = (double) model.getValueAt(i, 1);
                                    int km = (int) model.getValueAt(i, 2);
                                    boolean isHybrid = (boolean) model.getValueAt(i, 3);

                                    // If the data is duplicated.
                                    if (TruckBrandField.getText().equals(brand)
                                            && TruckHorsepower == horsepower
                                            && TruckKM == km
                                            && isHybridCB.isSelected() == isHybrid) {
                                        JOptionPane.showMessageDialog(null, "Duplicate trucks found!");
                                        isDuplicate = true;
                                        break;
                                    }
                                }
                            }

                            //If is not duplicated, allow the modifying.
                            if (!isDuplicate){
                                model.removeRow(rowSelect);
                                model.insertRow(rowSelect, new Object[]{
                                        TruckBrandField.getText(),
                                        TruckHorsepower,
                                        TruckKM,
                                        isHybridCB.isSelected()
                                });

                                //Setting the fields blanks finally.
                                TruckBrandField.setText("");
                                TruckHorsepowerField.setText("");
                                TruckKMField.setText("");
                            } else {
                                JOptionPane.showMessageDialog(null, "You cannot modify a duplicate entry.");
                            }
                        } catch (NumberFormatException ex){
                            //Exception thrown if the user types another data type different from numbers.
                            JOptionPane.showMessageDialog(null, "The values of horsepower and kilometers have to be numbers");
                        } catch (RuntimeException ex){
                            //Exception thrown for the duplication.
                            System.out.println("Caught RuntimeException: " + ex.getMessage());
                            JOptionPane.showMessageDialog(null, "Duplicate trucks found!");
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "To modify one row you have to select it on the table");
                }
            }
        });

        //The delete button
        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //We look if we have a row selected on our table
                int rowSelect = table.getSelectedRow();

                if (rowSelect!=-1){
                    model.removeRow(rowSelect);
                    //We will put the fields in blank
                    TruckBrandField.setText("");
                    TruckHorsepowerField.setText("");
                    TruckKMField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "To delete a row you have to select it on the table.");
                }
            }
        });

        //The save button
        SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
            private void saveToFile(){
                try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(FILE)))) {
                    for (int i = 0; i < model.getRowCount(); i++) {
                        writer.println(model.getValueAt(i, 0) + "," +
                                model.getValueAt(i, 1) + "," +
                                model.getValueAt(i, 2) + "," +
                                model.getValueAt(i, 3));
                    }
                    JOptionPane.showMessageDialog(null, "Data saved successfully.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Function to insert to the table from the fields.
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int rowSelect = table.getSelectedRow();

                if (rowSelect!=-1){
                    TruckBrandField.setText(model.getValueAt(rowSelect,0).toString());
                    TruckHorsepowerField.setText(model.getValueAt(rowSelect,1).toString());
                    TruckKMField.setText(model.getValueAt(rowSelect,2).toString());
                    isHybridCB.setSelected((Boolean)model.getValueAt(rowSelect,3));
                } else {
                    TruckBrandField.setText("");
                    TruckHorsepowerField.setText("");
                    TruckKMField.setText("");
                }
            }
        });

        //The field for inserting the name of truck
        TruckBrandField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e){
                super.focusLost(e);

                String regex1 = "^[A-Z][a-z]+$",
                        regex2 = "^[a-z][A-Z]+$";
                //setException(new MyException(2, "The name has to begin with a big letter"));


                if (TruckBrandField.getText().isBlank() || (!TruckBrandField.getText().matches(regex1) && !TruckBrandField.getText().matches(regex2))) {
                    settException(new MyException(2, "The truck name may start with capital letter or not finish with it."));

                    TruckBrandField.setSelectionStart(0);
                    TruckBrandField.setSelectionEnd(TruckBrandField.getText().length());
                }
            }
        });

        //The field for inserting the horsepower
        TruckHorsepowerField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                try {
                    NumberFormat num = NumberFormat.getNumberInstance(Locale.getDefault());
                    double horsepower = num.parse(TruckHorsepowerField.getText().trim()).doubleValue();
                    if (horsepower < 200 || horsepower > 10000) {
                        throw new ParseException("", 0);
                    }
                } catch (ParseException ex) {
                    int answer = JOptionPane.showConfirmDialog(null, "You have to insert a correct horsepower");

                    if (answer == JOptionPane.YES_OPTION){
                        disposeOnClose();
                    } else if (answer == JOptionPane.CLOSED_OPTION) {
                        disposeOnClose();
                    }
                    settException(new MyException(3, "You have to insert a correct horsepower"));

                    TruckHorsepowerField.setSelectionStart(0);
                    TruckHorsepowerField.setSelectionEnd(TruckHorsepowerField.getText().length());
                    //TruckHorsepowerField.requestFocus();
                }
            }
        });

        //On Window-Close asking the user if he wants to save the changes or not.
        this.view.addWindowListener(new WindowAdapter() {

            public void saveToFile() {
                //Here we have to save the data of the table to the file.
                File file = new File(FILE);

                ObjectOutputStream output = null;
                try{
                    output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
                    for (int i = 0; i < model.getRowCount(); i++) {
                        Truck truck = new Truck(
                                model.getValueAt(i, 0).toString(),
                                (Double) model.getValueAt(i, 1),
                                Integer.parseInt(model.getValueAt(i, 2).toString()),
                                (Boolean) model.getValueAt(i, 3)
                        );
                        output.writeObject(truck);
                    }
                } catch (FileNotFoundException ex) {
                    //Exception thrown if the directory is not valid or existing.
                    int selection = JOptionPane.showConfirmDialog(null,
                            "The file cannot be saved because this directory doesn't exist!",
                            "Error to save the file", JOptionPane.NO_OPTION);

                    //If the user selects yes, the app will close
                    //If the user select no, the app will close but with a message telling him that he have to change the directory.
                    if (selection == JOptionPane.YES_OPTION){
                        disposeOnClose();
                    } else if (selection == JOptionPane.NO_OPTION){
                        JOptionPane.showMessageDialog(null, "You have to change the directory.");
                        disposeOnClose();
                    }

                    System.out.println("That directory doesn't exist.");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }finally {
                    //Exception appears if the program fails to try to close the file of read.
                    try{
                        if (output!=null){
                            output.close();
                        }
                    } catch (IOException ex){
                        System.out.println("Error to try to close the file of read");
                    }
                }

                System.out.println("I am exiting!");
            }

            //On-Closing Pop-up Window asking the user if he wants to save the changes or not.
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Do you want to save changes?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    saveToFile();
                    super.windowClosing(e);
                } else if (choice == JOptionPane.NO_OPTION) {
                    super.windowClosing(e);
                }
            }
        });
    }

    //Changes today
    private static void fillDriver(Truck truck, DefaultTableModel modelDriver){
        modelDriver.setRowCount(0);
        for (Driver driver : truck.getDrivers()){
            modelDriver.addRow(new Object[]{
                    driver.getBrandEnum()
            });
        }
    }

    private void viewModel(){
        JTable table = view.getTable();

        DefaultTableModel model2 = this.model.getModel();

        table.setModel(model2);

        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}