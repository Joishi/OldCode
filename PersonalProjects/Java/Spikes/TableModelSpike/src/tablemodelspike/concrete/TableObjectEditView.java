/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodelspike.concrete;

import java.awt.GridLayout;
import java.sql.Date;
import java.util.Calendar;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import tablemodelspike.iterface.TableEditViewI;
import tablemodelspike.iterface.TableEditViewListenerI;
import tablemodelspike.iterface.TableModelI;

/**
 *
 * @author boydj
 */
public class TableObjectEditView implements TableEditViewI {
    
    private TableObject unalteredObject;
    
    private final JLabel lab1;
    private final JLabel lab2;
    private final JLabel lab3;
    private final JTextField val1;
    private final JTextField val2;
    private final JComboBox<Integer> val3Y;
    private final JComboBox<Integer> val3M;
    private final JComboBox<Integer> val3D;
    
    private final JButton newButton;
    private final JButton saveButton;
    
    public TableObjectEditView(JPanel container) {
        String sizeString = "THISSIZE";
        lab1 = new JLabel("LongVal:");
        lab2 = new JLabel("StringVal:");
        lab3 = new JLabel("DateVal:");
        val1 = new JTextField(sizeString); // ignored because of gridlayout ...
        val2 = new JTextField(sizeString); // ignored because of gridlayout ...
        val1.setText("");
        val2.setText("");
        val3Y = new JComboBox<>();
        val3M = new JComboBox<>();
        val3D = new JComboBox<>();
        
        for (int i = 2015; i > 1970; i--) {
            val3Y.addItem(i);
        }
        for (int i = 1; i <= 12; i++) {
            val3M.addItem(i);
        }
        for (int i = 1; i <= 31; i++) {
            val3D.addItem(i);
        }
        
        newButton = new JButton("NEW RECORD");
        newButton.setActionCommand("NEW");
        saveButton = new JButton("SAVE RECORD");
        saveButton.setActionCommand("SAVE");
        
        container.setLayout(new GridLayout(3, 4));
        container.add(lab1);
        container.add(val1);
        container.add(lab2);
        container.add(val2);
        container.add(lab3);
        container.add(val3Y);
        container.add(val3M);
        container.add(val3D);
        container.add(Box.createHorizontalGlue());
        container.add(newButton);
        container.add(saveButton);
        container.add(Box.createHorizontalGlue());
    }
    
    @Override
    public void addListener(TableEditViewListenerI listener) {
        newButton.addActionListener(listener);
        saveButton.addActionListener(listener);
    }
    
    @Override
    public void removeListener(TableEditViewListenerI listener) {
        newButton.removeActionListener(listener);
        saveButton.removeActionListener(listener);
    }
    
    @Override
    public void selectedRowChanged(TableModelI tableModel) {
        Object o = tableModel.getSelectedObject();
        if (o instanceof TableObject) {
            unalteredObject = (TableObject)o;
            setObjectViewFields();
        }
    }
    
    private void setObjectViewFields() {
        if (unalteredObject != null) {
            val1.setText(unalteredObject.getLongVal().toString());
            val2.setText(unalteredObject.getStringVal());
            Calendar time = new Calendar.Builder().setInstant(unalteredObject.getDateVal().getTime()).build();
            int year = time.get(Calendar.YEAR);
            int month = time.get(Calendar.MONTH);
            int day = time.get(Calendar.DAY_OF_MONTH);
            val3Y.setSelectedItem(year);
            val3M.setSelectedItem(month);
            val3D.setSelectedItem(day);
        } else {
            val1.setText("");
            val2.setText("");
        }
    }
    
    @Override
    public TableObject getNewObject() {
        Long longVal = Long.parseLong(val1.getText());
        String stringVal = val2.getText();
        int year = (Integer)val3Y.getSelectedItem();
        int month = (Integer)val3M.getSelectedItem();
        int day = (Integer)val3D.getSelectedItem();
        Date dateVal = new Date(new Calendar.Builder().setDate(year, month, day).build().getTimeInMillis());
        return new TableObject(longVal, stringVal, dateVal);
    }
    
    @Override
    public void tableChanged(TableModelEvent e) {
        // IGNORE
    }
    
}
