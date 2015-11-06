/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodelspike.iterface;

import tablemodelspike.concrete.TableObject;

/**
 *
 * @author boydj
 */
public interface TableEditViewI extends TableModelListenerI {
    
    public void addListener(TableEditViewListenerI listener);
    public void removeListener(TableEditViewListenerI listener);
    
    public TableObject getNewObject();
    
}
