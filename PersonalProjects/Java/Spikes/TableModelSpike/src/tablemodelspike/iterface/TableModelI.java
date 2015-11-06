/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodelspike.iterface;

import javax.swing.table.TableModel;
import tablemodelspike.concrete.TableObject;

/**
 *
 * @author boydj
 */
public interface TableModelI extends TableModel {
    
    public void addListener(TableModelListenerI listener);
    public void removeListener(TableModelListenerI listener);
    
    public void setSelectedRow(int selectedRowIndex);
//    public Object getRowObject(int rowIndex);
    public Object getSelectedObject();
    
    public void saveNewObject(TableObject newObject);
    
}
