/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodelspike.iterface;

/**
 *
 * @author boydj
 */
public interface TableListViewI extends TableModelListenerI {
    
    public void addListener(TableListViewListenerI listener);
    public void removeListener(TableListViewListenerI listener);
    
    public int getSelectedRow();
    
}
