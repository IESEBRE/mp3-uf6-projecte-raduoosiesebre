package org.radostin.mvc.model;

import org.radostin.mvc.view.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

import static org.radostin.mvc.view.View.disposeOnClose;

public class Files {
    View view = new View();

    //Constant method to define the name of the file one time inside the app.
    public static final String FILE = "./src/main/resources/data.dat";

    //Method to load the data when launching the application.
    protected static void loadData(DefaultTableModel model) {

        //For the files
        File file = new File(FILE);

        //We look for the file if exists
        if (file.exists()) {
            System.out.println("The file exists");
            ObjectInputStream input = null;

            try {
                //Object that allows to read the objects of the file
                input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(FILE)));

                while (true) {
                    try {
                        Truck t = (Truck) input.readObject();

                        //Insert to the table
                        model.addRow(new Object[]{
                                t.getTruckBrand(),
                                t.getTruckHorsepower(),
                                t.getTruckKM(),
                                t.getIsHybrid()
                        });
                    } catch (ClassNotFoundException | IOException e) {
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    System.out.println("Error to try to close the file of read");
                }
            }
        }
    }

    public static void saveToFile(DefaultTableModel model) {
        //Here we have to save the data of the table to the file.
        File file = new File(FILE);

        ObjectOutputStream output = null;
        try {
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
            if (selection == JOptionPane.YES_OPTION) {
                disposeOnClose();
            } else if (selection == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(null, "You have to change the directory.");
                disposeOnClose();
            }

            System.out.println("That directory doesn't exist.");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            //Exception appears if the program fails to try to close the file of read.
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException ex) {
                System.out.println("Error to try to close the file of read");
            }
        }

        System.out.println("I am exiting!");
    }
}
