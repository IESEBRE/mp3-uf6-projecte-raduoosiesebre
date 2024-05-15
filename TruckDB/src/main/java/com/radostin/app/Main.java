package com.radostin.app;

import com.radostin.controller.Controller;
import com.radostin.model.impls.TruckDAO_JDBC_Oracle;
import com.radostin.view.DriverView;

import javax.swing.*;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Definim la cultura de la nostra aplicació
                Locale.setDefault(new Locale("en","US"));
                new Controller(new TruckDAO_JDBC_Oracle(), new DriverView());
            }
        });
    }
}
