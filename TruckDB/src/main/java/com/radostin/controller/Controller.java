package com.radostin.controller;

import com.radostin.model.entities.Truck;
import com.radostin.model.exceptions.DAOException;
import com.radostin.model.impls.TruckDAO_JDBC_Oracle;
import com.radostin.view.DriverView;
import com.radostin.view.ModelComponentsVisuals;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

public class Controller implements PropertyChangeListener { //1. Implementació de interfície PropertyChangeListener

    private ModelComponentsVisuals modelComponentsVisuals =new ModelComponentsVisuals();
    private TruckDAO_JDBC_Oracle trucksData;
    private DriverView view;

    public Controller(TruckDAO_JDBC_Oracle trucksData, DriverView view) {
        this.trucksData = trucksData;
        this.view = view;

        //5. Necessari per a que Controller reaccione davant de canvis a les propietats lligades
        changeSupport.addPropertyChangeListener(this);

        lligaVistaModel();

        addListeners();

        //Si no hem tingut cap poroblema amb la BD, mostrem la finestra
        view.setVisible(true);

    }

    private void lligaVistaModel() {

        //Carreguem la taula d'alumnes en les dades de la BD
        try {
            setModelTrucksTable(modelComponentsVisuals.getTableModelTruck(), trucksData.getAll());
        } catch (DAOException e) {
            this.setExcepcio(e);
        }

        //Fixem el model de la taula dels alumnes
        JTable taula = view.getTruckTable();
        taula.setModel(this.modelComponentsVisuals.getTableModelTruck());
        //Amago la columna que conté l'objecte alumne
        taula.getColumnModel().getColumn(3).setMinWidth(0);
        taula.getColumnModel().getColumn(3).setMaxWidth(0);
        taula.getColumnModel().getColumn(3).setPreferredWidth(0);

        //Fixem el model de la taula de matrícules
        JTable taulaMat = view.getDriverTable();
        taulaMat.setModel(this.modelComponentsVisuals.getTableModelDriver());

        //Posem valor a el combo d'MPs
        view.getComboTrucks().setModel(modelComponentsVisuals.getComboBoxModel());

        //Desactivem la pestanya de la matrícula
        view.getMyWindows().setEnabledAt(1, false);
        view.getMyWindows().setTitleAt(1, "Matrícula de ...");

        //5. Necessari per a que Controller reaccione davant de canvis a les propietats lligades
        changeSupport.addPropertyChangeListener(this);
    }

    private void setModelTrucksTable(DefaultTableModel modelTrucksTable, List<Truck> all) {

        // Fill the table model with data from the collection
        for (Truck truck : all){
            modelTrucksTable.addRow(new Object[]{
                truck.getTruckBrand(),
                    truck.getTruckHorsepower(),
                    true,
                    truck.isTruckIsElectric()
            });
        }

        /*
        for (Alumne estudiant : all) {
            modelTrucksTable.addRow(new Object[]{estudiant.getNom(), estudiant.getPes(), true, estudiant});
        }
         */
    }

    private void addListeners() {

        ModelComponentsVisuals modelo = this.modelComponentsVisuals;

        DefaultTableModel truckModel = modelo.getTableModelTruck();
        DefaultTableModel driverModel = modelo.getTableModelDriver();

        JTable truckTable = view.getTruckTable();
        JTable driverTable = view.getDriverTable();

        JButton insertButton = view.getInsertButton();
        JButton modifyButton = view.getModifyButton();
        JButton deleteButton = view.getDeleteButton();
        JButton saveButton = view.getSaveButton();

        JTextField truckBrandField = view.getTruckBrandField();
        JTextField truckHorsepowerField = view.getTruckHorsepowerField();
        JTextField truckKMField = view.getTruckKMField();
        JCheckBox truckIsElectric = view.getTruckIsElectric();

        JTabbedPane windows = view.getMyWindows();


        //The insert button.
        view.getInsertButton().addActionListener(
                new ActionListener() {
                    /**
                     * Invoked when an action occurs.
                     *
                     * @param e the event to be processed
                     */
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTextField truckBrandField = view.getTruckBrandField();
                        JTextField truckHorsepowerField = view.getTruckHorsepowerField();
                        JTextField truckKMField = view.getTruckKMField();
                        JCheckBox truckIsElectric = view.getTruckIsElectric();


                        if (windows.getSelectedIndex() == 0) {        //Si estem a la pestanya de l'alumne
                            //Comprovem que totes les caselles continguen informació
                            if (truckBrandField.getText().isBlank() || truckHorsepowerField.getText().isBlank()) {
                                JOptionPane.showMessageDialog(null, "Falta omplir alguna dada!!");
                            } else {
                                try {
                                    NumberFormat num = NumberFormat.getNumberInstance(Locale.getDefault());   //Creem un número que entèn la cultura que utilitza l'aplicació
                                    double hp = num.parse(truckHorsepowerField.getText().trim()).doubleValue();  //intentem convertir el text a double
                                    if (hp < 200 || hp > 3000) throw new ParseException("", 0);
                                    Truck truck = new Truck(truckBrandField.getText(), hp, (Integer.parseInt(truckKMField.getText())), truckIsElectric.isSelected(), new TreeSet<Truck.Driver>());
                                    truckModel.addRow(new Object[]{
                                            truckBrandField.getText(),
                                            hp,
                                            truckKMField.getText(),
                                            truckIsElectric.isSelected(),
                                    });
                                    truckBrandField.setText("Pepe Gotera Ibáñez");
                                    truckBrandField.setSelectionStart(0);
                                    truckBrandField.setSelectionEnd(truckBrandField.getText().length());
                                    truckHorsepowerField.setText("75");
                                    truckBrandField.requestFocus();         //intentem que el foco vaigue al camp del nom
                                } catch (ParseException ex) {
                                    setExcepcio(new DAOException(3));
//                                    JOptionPane.showMessageDialog(null, "Has d'introduir un pes correcte (>=1 i <=800!!");
                                    truckHorsepowerField.setSelectionStart(0);
                                    truckHorsepowerField.setSelectionEnd(truckHorsepowerField.getText().length());
                                    truckHorsepowerField.requestFocus();
                                }
                            }
                        } else {         //Si estem a la pestanya de la matricula
                            //Obtinc l'alumne de la columna que conté l'objecte
                            Truck truck = (Truck) truckModel.getValueAt(truckTable.getSelectedRow(), 3);
                            Truck.Driver driver = new Truck.Driver((Truck.Driver.TruckENUM) view.getComboTrucks().getSelectedItem(), view.getLicenseNumberField().getText());
                            truck.getDrivers().add(driver);
                            fillDriver(truck, truckModel);
                            /*
                            al.getMatricules().add(m);
                            ompliMatricula(al, modelMat);
                             */
                        }


                    }
                }
        );

        truckTable.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                //Obtenim el número de la fila seleccionada
                int filaSel = truckTable.getSelectedRow();

                if (filaSel != -1) {        //Tenim una fila seleccionada
                    //Posem els valors de la fila seleccionada als camps respectius
                    truckBrandField.setText(truckModel.getValueAt(filaSel, 0).toString());
                    truckHorsepowerField.setText(truckModel.getValueAt(filaSel, 1).toString().replaceAll("\\.", ","));
                    truckKMField.setText(truckModel.getValueAt(filaSel, 1).toString().replaceAll("\\.", ","));
                    truckIsElectric.setSelected((Boolean) truckModel.getValueAt(filaSel, 3));

                    //Activem la pestanya de la matrícula de l'alumne seleccionat
                    view.getMyWindows().setEnabledAt(1, true);
                    view.getMyWindows().setTitleAt(1, "Driver of " + truckBrandField.getText());

                    //Posem valor a el combo d'MPs
                    //view.getComboMP().setModel(modelo.getComboBoxModel());
                    fillDriver((Truck) truckModel.getValueAt(filaSel, 3),driverModel);
                } else { //Hem desseleccionat una fila
                    //Posem els camps de text en blanc
                    truckBrandField.setText("");
                    truckHorsepowerField.setText("");
                    truckKMField.setText("");
                    
                    //Desactivem pestanyes
                    view.getMyWindows().setEnabledAt(1, false);
                    view.getMyWindows().setTitleAt(1, "Matrícula de ...");
                }
            }
        });

        truckBrandField.addFocusListener(new FocusAdapter() {
            /**
             * Invoked when a component loses the keyboard focus.
             *
             * @param e
             */
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                String regex1="",
                        regex2="";;
                //String regex="[À-ú]";
                //Pattern pattern = Pattern.compile(regex);
                if(truckBrandField.getText().isBlank() || (!truckBrandField.getText().matches(regex1) && !truckBrandField.getText().matches(regex2))){
                    /*
                    setExcepcio(new DAOException(2));
                     */
                }
            }
        });
        //throw new LaMeuaExcepcio(1,"Ha petat la base de dades");
    }



    private static void fillDriver(Truck truck,DefaultTableModel driverModel) {
        //Omplim el model de la taula de matrícula de l'alumne seleccionat
        driverModel.setRowCount(0);
        // Fill the table model with data from the collection
        for (Truck.Driver driver : truck.getDrivers()) {
            driverModel.addRow(new Object[]{
                    driver.getTruckEnum(), 
                    driver.getLicenseNumber()
            });
        }
    }


    //TRACTAMENT D'EXCEPCIONS

    //2. Propietat lligada per controlar quan genero una excepció
    public static final String PROP_EXCEPCIO="excepcio";
    private DAOException excepcio;

    public DAOException getExcepcio() {
        return excepcio;
    }

    public void setExcepcio(DAOException excepcio) {
        DAOException valorVell=this.excepcio;
        this.excepcio = excepcio;
        changeSupport.firePropertyChange(PROP_EXCEPCIO, valorVell,excepcio);
    }


    //3. Propietat PropertyChangesupport necessària per poder controlar les propietats lligades
    PropertyChangeSupport changeSupport =new PropertyChangeSupport(this);


    //4. Mètode on posarem el codi de tractament de les excepcions --> generat per la interfície PropertyChangeListener
    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        DAOException received =(DAOException)evt.getNewValue();

        try {
            throw received;
        } catch (DAOException e) {
            //Aquí farem ele tractament de les excepcions de l'aplicació
            switch(evt.getPropertyName()){
                case PROP_EXCEPCIO:

                    switch(received.getType()){
                        case 0:
                            JOptionPane.showMessageDialog(null, received.getMessage());
                            System.exit(1);
                            break;
                        case 1:
                            JOptionPane.showMessageDialog(null, received.getMessage());
                            break;
                        case 2:
                            JOptionPane.showMessageDialog(null, received.getMessage());
                            //this.view.getCampNom().setText(received.getMissatge());
                            this.view.getTruckBrandField().setSelectionStart(0);
                            this.view.getTruckBrandField().setSelectionEnd(this.view.getTruckBrandField().getText().length());
                            this.view.getTruckBrandField().requestFocus();

                            break;
                    }


            }
        }
    }

}