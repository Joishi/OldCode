/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodelspike.concrete;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import tablemodelspike.iterface.TableModelI;
import tablemodelspike.iterface.TablePreviewViewI;

/**
 *
 * @author boydj
 */
public class TableObjectPreviewView implements TablePreviewViewI {
    
    private TableObject object;
    
    private final JLabel lab1;
    private final JLabel lab2;
    private final JLabel lab3;
    private final JLabel val1;
    private final JLabel val2;
    private final JLabel val3;
    
    public TableObjectPreviewView(JPanel container) {
        lab1 = new JLabel("LongVal:");
        lab2 = new JLabel("StringVal:");
        lab3 = new JLabel("DateVal:");
        val1 = new JLabel("");
        val2 = new JLabel("");
        val3 = new JLabel("");
        
        container.setLayout(new GridLayout(3, 2));
        container.add(lab1);
        container.add(val1);
        container.add(lab2);
        container.add(val2);
        container.add(lab3);
        container.add(val3);
    }
    
    @Override
    public void selectedRowChanged(TableModelI tableModel) {
        Object o = tableModel.getSelectedObject();
        if (o instanceof TableObject) {
            object = (TableObject)o;
            setObjectViewFields();
        }
    }
    
    private void setObjectViewFields() {
        if (object != null) {
            val1.setText(object.getLongVal().toString());
            val2.setText(object.getStringVal());
            val3.setText(object.getDateVal().toString());
        } else {
            val1.setText("");
            val2.setText("");
            val3.setText("");
        }
    }
    
    @Override
    public void tableChanged(TableModelEvent e) {
        // IGNORE
    }
    
}
