package com.radostin.view;

import com.radostin.model.type.EngineType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TruckView extends JFrame {

    private JPanel panel;
    private JScrollPane scrollPane;
    private JTextField truckBrandField;
    private JTextField truckHorsepowerField;
    private JTextField truckKMField;
    private JCheckBox truckHasTrailerCheckBox;
    private JComboBox<EngineType> engineTypeJComboBox;
    private JTable table;
    private JButton insertButton, modifyButton, deleteButton, saveButton, manageDriversButton;

    public TruckView() {
        // Initialize components
        truckBrandField = new JTextField(10);
        truckHorsepowerField = new JTextField(10);
        truckKMField = new JTextField(10);
        truckHasTrailerCheckBox = new JCheckBox("Has Trailer");
        engineTypeJComboBox = new JComboBox<>(EngineType.values());

        // Initialize table
        table = new JTable(new DefaultTableModel(new Object[]{"ID", "Brand", "Horsepower", "Kilometers", "Has Trailer", "Engine Type"}, 0));

        // The mouse listener for the table
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1){
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    truckBrandField.setText(model.getValueAt(selectedRow, 1).toString());
                    truckHorsepowerField.setText(model.getValueAt(selectedRow, 2).toString());
                    truckKMField.setText(model.getValueAt(selectedRow, 3).toString());
                    truckHasTrailerCheckBox.setSelected(Boolean.parseBoolean(model.getValueAt(selectedRow, 4).toString()));
                    engineTypeJComboBox.setSelectedItem(EngineType.valueOf(model.getValueAt(selectedRow, 5).toString()));
                }
            }
        });

        // Initialize buttons
        insertButton = new JButton("Add");
        modifyButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        saveButton = new JButton("Save");

        // Layout setup
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Brand:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(truckBrandField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Horsepower:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(truckHorsepowerField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Kilometers:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(truckKMField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Has Trailer:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(truckHasTrailerCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Engine Type:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(engineTypeJComboBox, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(insertButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Frame setup
        setTitle("Truck Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
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

    public JCheckBox getTruckHasTrailerCheckBox() {
        return truckHasTrailerCheckBox;
    }

    public JComboBox getEngineTypeJComboBox() {
        return engineTypeJComboBox;
    }

    public JTable getTable() {
        return table;
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
}

/* Working GUI, but ugly
public class TruckView extends JFrame {
    private JPanel panel;
    private JScrollPane scrollPane;
    private JTable table;
    private JTextField truckBrandField;
    private JTextField truckHorsepowerField;
    private JTextField truckKMField;
    private JCheckBox truckHasTrailerCheckBox;
    private JComboBox<EngineType> engineTypeJComboBox;
    private JButton insertButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton manageDriversButton;


    public TruckView() {
        setTitle("Truck Database");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        table = new JTable(new DefaultTableModel(new Object[]{"ID", "Brand", "Horsepower", "KM", "Has Trailer?", "Type"}, 0));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        truckBrandField = new JTextField();
        panel.add(truckBrandField);

        truckHorsepowerField = new JTextField();
        panel.add(truckHorsepowerField);

        truckKMField = new JTextField();
        panel.add(truckKMField);

        truckHasTrailerCheckBox = new JCheckBox();
        panel.add(truckHasTrailerCheckBox);

        engineTypeJComboBox = new JComboBox<>(EngineType.values());
        panel.add(engineTypeJComboBox);

        JPanel buttonPanel = new JPanel();
        insertButton = new JButton("Add");
        modifyButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        saveButton = new JButton("Save");
        manageDriversButton = new JButton("Manage Drivers");

        buttonPanel.add(insertButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(manageDriversButton);

        panel.add(buttonPanel);
        add(panel, BorderLayout.SOUTH);
    }

    public JTable getTable() {
        return table;
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

    public JCheckBox getTruckHasTrailerCheckBox() {
        return truckHasTrailerCheckBox;
    }


    public JComboBox<EngineType> getEngineTypeJComboBox() {
        return engineTypeJComboBox;
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
}
 */
