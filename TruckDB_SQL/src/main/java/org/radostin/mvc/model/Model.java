package org.radostin.mvc.model;

import javax.swing.table.DefaultTableModel;

public class Model {

    private DefaultTableModel model;

    public DefaultTableModel getModel(){
        return model;
    }

    public Model(){
        model = new DefaultTableModel(new Object[]{"Brand", "Horsepower", "KM", "Is Hybrid?"}, 0){

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 1) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Double.class;
                    case 2:
                        return Integer.class;
                    case 3:
                        return Boolean.class;
                    default:
                        return Object.class;
                }
            }
        };

        Files.loadData(model);
    }

    public void writeDataFile(){
        Files.saveToFile(model);
    }
}
