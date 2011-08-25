package com.davvo.visakarta.client.view;

import java.util.ArrayList;

import java.util.List;

import com.davvo.visakarta.client.presenter.MarkersPresenter.Display;
import com.davvo.visakarta.shared.VKMarker;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MarkersView implements Display {

    private DialogBox dialogBox;

    private Button addButton;
    private Button deleteButton;    
    private FlexTable markersTable;
    private final FlexTable contentTable;
    
    public MarkersView() {
        
        contentTable = new FlexTable();
        contentTable.setWidth("100%");
        contentTable.getCellFormatter().setWidth(0, 0, "100%");
        contentTable.getFlexCellFormatter().setVerticalAlignment(0, 0, DockPanel.ALIGN_TOP);
        
        // Create the menu
        //
        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.setBorderWidth(0);
        hPanel.setSpacing(0);
        hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
        addButton = new Button("Add");
        hPanel.add(addButton);
        deleteButton = new Button("Delete");
        hPanel.add(deleteButton);
        contentTable.setWidget(0, 0, hPanel);
        
        // Create the contacts list
        //
        markersTable = new FlexTable();
        markersTable.setCellSpacing(0);
        markersTable.setCellPadding(0);
        markersTable.setWidth("100%");
        markersTable.getColumnFormatter().setWidth(0, "15px");
        contentTable.setWidget(1, 0, markersTable);
        
        dialogBox = new DialogBox(false, false);
        dialogBox.setText("Markers");
        dialogBox.setWidget(contentTable);        
    }
    
    @Override
    public HasClickHandlers getAddButton() {
        return addButton;
    }

    @Override
    public HasClickHandlers getDeleteButton() {
        return deleteButton;
    }

    @Override
    public List<Integer> getSelectedRows() {
        List<Integer> selectedRows = new ArrayList<Integer>();
        
        for (int i = 0; i < markersTable.getRowCount(); ++i) {
          CheckBox checkBox = (CheckBox) markersTable.getWidget(i, 0);
          if (checkBox.getValue()) {
            selectedRows.add(i);
          }
        }
        
        return selectedRows;
    }

    public void setData(List<VKMarker> data) {
        markersTable.removeAllRows();
        
        for (int i = 0; i < data.size(); ++i) {
          markersTable.setWidget(i, 0, new CheckBox());
          markersTable.setText(i, 1, "Marker " + i);
        }

        deleteButton.setEnabled(!data.isEmpty());
      }
    
    
    @Override
    public void show() {
        dialogBox.center();        
    }

    @Override
    public void hide() {
        dialogBox.hide();        
    }
    
    @Override
    public Widget asWidget() {
        return dialogBox;
    }

}
