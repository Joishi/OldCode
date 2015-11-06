/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodelspike.concrete;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import tablemodelspike.iterface.TableListViewI;
import tablemodelspike.iterface.TableListViewListenerI;
import tablemodelspike.iterface.TableModelI;

/**
 *
 * @author boydj
 */
public class TableListView implements TableListViewI {
    
    private JTable table;
    
    public TableListView(JPanel container, TableModelI tableModel) {
        table = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().add(table);
        
        container.add(scrollPane);
    }
    
    @Override
    public void addListener(TableListViewListenerI listener) {
        table.addMouseListener(listener);
    }
    
    @Override
    public void removeListener(TableListViewListenerI listener) {
        table.removeMouseListener(listener);
    }
    
    @Override
    public int getSelectedRow() {
        return table.getSelectedRow();
    }
    
    @Override
    public void selectedRowChanged(TableModelI tableModel) {
        // IGNORE .. THIS RAISED THE EVENT TO BEGIN WITH
    }
    
    @Override
    public void tableChanged(TableModelEvent e) {
        table.tableChanged(e);
    }
    
}
