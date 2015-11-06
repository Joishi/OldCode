/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodelspike.concrete;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import tablemodelspike.iterface.TableModelI;
import tablemodelspike.iterface.TableModelListenerI;

/**
 *
 * @author boydj
 */
public class TableModel extends AbstractTableModel implements TableModelI {
    
    protected ArrayList<TableModelListenerI> listeners;
    
    protected HashMap<Integer, TableObject> objects;
    protected final int MIN_ROW_COUNT = 25;
    
    private int selectedRowIndex;
    
    public TableModel() {
        listeners = new ArrayList<>();
        objects = new HashMap<>();
        populateObjects();
    }
    
    @Override
    public void addListener(TableModelListenerI listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    @Override
    public void removeListener(TableModelListenerI listener) {
        listeners.remove(listener);
    }
    
    @Override
    public int getRowCount() {
        return objects.size() < MIN_ROW_COUNT ? MIN_ROW_COUNT : objects.size();
    }
    
    @Override
    public int getColumnCount() {
        return 3;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        String columnName = "??";
        switch (columnIndex) {
            case 0: columnName = "Long"; break;
            case 1: columnName = "String"; break;
            case 2: columnName = "Date"; break;
        }
        return columnName;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return Long.class;
            case 1: return String.class;
            case 2: return Date.class;
            default: return null;
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object returnVal = null;
        if (rowIndex < objects.size()) {
            TableObject object = objects.get(rowIndex);
            switch (columnIndex) {
                case 0: returnVal = object.getLongVal(); break;
                case 1: returnVal = object.getStringVal(); break;
                case 2: returnVal = object.getDateVal(); break;
            }
        }
        return returnVal;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    @Override
    public void setSelectedRow(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
        notifyListenersSelectedRowChanged();
    }
    
//    @Override
//    public Object getRowObject(int rowIndex) {
//        return objects.get(rowIndex);
//    }
    
    @Override
    public Object getSelectedObject() {
        return objects.get(selectedRowIndex);
    }
    
    private void populateObjects() {
        long msPerYear = 31557600000l;
        for (int i = 0; i < 15; i++) {
            objects.put(i, new TableObject(new Long(i), "" + i, new Date(msPerYear * i)));
        }
    }
    
    @Override
    public void saveNewObject(TableObject newObject) {
        int index = objects.size();
        objects.put(index, newObject);
        notifyListenersNewObjectAdded(index);
    }
    
    private void notifyListenersSelectedRowChanged() {
        Iterator<TableModelListenerI> listenerIterator = listeners.iterator();
        while (listenerIterator.hasNext()) {
            listenerIterator.next().selectedRowChanged(this);
        }
    }
    
    private void notifyListenersNewObjectAdded(int rowIndex) {
        Iterator<TableModelListenerI> listenerIterator = listeners.iterator();
        while (listenerIterator.hasNext()) {
            listenerIterator.next().tableChanged(new TableModelEvent(this, rowIndex));
        }
    }
    
}
