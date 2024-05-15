package com.radostin.view;

import com.radostin.model.entities.Truck;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ModelComponentsVisuals {

    private DefaultTableModel tableModelTruck;
    private DefaultTableModel tableModelDriver;
    private ComboBoxModel<Truck.Driver.TruckENUM> comboBoxModel;

    //Getters

    public ComboBoxModel<Truck.Driver.TruckENUM> getComboBoxModel() {
        return comboBoxModel;
    }

    public DefaultTableModel getTableModelTruck() {
        return tableModelTruck;
    }

    public DefaultTableModel getTableModelDriver() {
        return tableModelDriver;
    }

    public ModelComponentsVisuals() {


        //Anem a definir l'estructura de la taula dels alumnes
        tableModelTruck =new DefaultTableModel(new Object[]{
                "Brand","Horsepower",
                "KM",
                "És alumne?",
                "Object"
        },0){
            /**
             * Returns true regardless of parameter values.
             *
             * @param row    the row whose value is to be queried
             * @param column the column whose value is to be queried
             * @return true
             * @see #setValueAt
             */
            @Override
            public boolean isCellEditable(int row, int column) {

                //Fem que TOTES les cel·les de la columna 1 de la taula es puguen editar
                //if(column==1) return true;
                return false;
            }



            //Permet definir el tipo de cada columna
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Double.class;
                    case 2:
                        return Boolean.class;
                    default:
                        return Object.class;
                }
            }
        };




        //Anem a definir l'estructura de la taula de les matrícules
        tableModelDriver =new DefaultTableModel(new Object[]{"MP","Nota"},0){
            /**
             * Returns true regardless of parameter values.
             *
             * @param row    the row whose value is to be queried
             * @param column the column whose value is to be queried
             * @return true
             * @see #setValueAt
             */
            @Override
            public boolean isCellEditable(int row, int column) {

                //Fem que TOTES les cel·les de la columna 1 de la taula es puguen editar
                //if(column==1) return true;
                return false;
            }

            //Permet definir el tipo de cada columna
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Truck.Driver.TruckENUM.class;
                    case 1:
                        return Integer.class;
                    default:
                        return Object.class;
                }
            }
        };



        //Estructura del comboBox
        comboBoxModel=new DefaultComboBoxModel<>(Truck.Driver.TruckENUM.values());



    }
}
