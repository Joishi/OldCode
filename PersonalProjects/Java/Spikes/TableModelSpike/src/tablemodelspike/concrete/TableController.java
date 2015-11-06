/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodelspike.concrete;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import tablemodelspike.iterface.TableControllerI;
import tablemodelspike.iterface.TableEditViewI;
import tablemodelspike.iterface.TableListViewI;
import tablemodelspike.iterface.TableModelI;
import tablemodelspike.iterface.TablePreviewViewI;

/**
 *
 * @author boydj
 */
public class TableController implements TableControllerI {
    
    private final TableModelI tableModel;
    private final TableListViewI tableListView;
    private final TablePreviewViewI tablePreviewView;
    private final TableEditViewI tableEditView;
    
    public TableController() {
        tableModel = new TableModel();
        
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel listPane = new JPanel();
        tableListView = new TableListView(listPane, tableModel);
        
        JPanel previewPane = new JPanel();
        tablePreviewView = new TableObjectPreviewView(previewPane);
        JPanel editPane = new JPanel();
        tableEditView = new TableObjectEditView(editPane);
        
        JPanel leftPane = new JPanel();
        leftPane.add(listPane);
        
        JPanel rightPane = new JPanel(new GridLayout(3, 1));
        rightPane.add(previewPane);
        rightPane.add(Box.createVerticalGlue());
        rightPane.add(editPane);
        
        JPanel contentPane = new JPanel(new GridLayout(1, 2));
        contentPane.add(leftPane);
        contentPane.add(rightPane);
        
        tableModel.addListener(tableListView);
        tableModel.addListener(tablePreviewView);
        tableModel.addListener(tableEditView);
        tableListView.addListener(this);
        tableEditView.addListener(this);
        
        frame.add(contentPane, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        tableRowSelected();
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    private void tableRowSelected() {
        int rowIndex = tableListView.getSelectedRow();
        tableModel.setSelectedRow(rowIndex);
//        Object rowObject = tableModel.getRowObject(rowIndex);
//        if (rowObject instanceof TableObject) {
//            
//        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object actionSource = e.getSource();
        if (actionSource instanceof JButton) {
            JButton sourceButton = (JButton) actionSource;
            String actionCommand = sourceButton.getActionCommand();
            if (actionCommand.equals("NEW")) {
                tableModel.saveNewObject(tableEditView.getNewObject());
            } else if (actionCommand.equals("SAVE")) {
                
            }
        }
    }
    
}
