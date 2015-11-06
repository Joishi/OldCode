/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodelspike.iterface;

import javax.swing.event.TableModelListener;

/**
 *
 * @author boydj
 */
public interface TableModelListenerI extends TableModelListener {
    
    public void selectedRowChanged(TableModelI tableModel);
    
}
