package com.davvo.visakarta.client.view;

import java.util.ArrayList;


import java.util.List;

import com.davvo.visakarta.client.presenter.MarkersPresenter.Display;
import com.davvo.visakarta.shared.VKMarker;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class MarkersView implements Display {

    private DialogBox dialogBox;

    private Button addButton;
    private Button deleteButton;
    private Button closeButton;
    
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
        
        deleteButton = new Button("Delete");
        deleteButton.setEnabled(false);
        
        closeButton = new Button("Close");
        
        hPanel.add(addButton);
        hPanel.add(deleteButton);
        hPanel.add(closeButton);
        
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
    public HasClickHandlers getCloseButton() {
        return closeButton;
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

    public void setData(List<VKMarker> markers) {
        markersTable.removeAllRows();
        
        for (int i = 0; i < markers.size(); ++i) {
          markersTable.setWidget(i, 0, new CheckBox());
          markersTable.setText(i, 1, "Marker " + markers.get(i).getId());
        }

        deleteButton.setEnabled(!markers.isEmpty());
      }
    
    
    @Override
    public void show() {
        dialogBox.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            
            @Override
            public void setPosition(int offsetWidth, int offsetHeight) {
                dialogBox.setPopupPosition(RootPanel.get().getOffsetWidth() - offsetWidth - 10, 210);
            }
            
        });
    }

    @Override
    public void hide() {
        dialogBox.hide();        
    }
    
    @Override
    public Widget asWidget() {
        return dialogBox;
    }

    @Override
    public HasClickHandlers getList() {
        return markersTable;
    }

    @Override
    public int getClickedRow(ClickEvent event) {
        int selectedRow = -1;
        HTMLTable.Cell cell = markersTable.getCellForEvent(event);
        
        if (cell != null) {
          // Suppress clicks if the user is actually selecting the 
          //  check box
          //
          if (cell.getCellIndex() > 0) {
            selectedRow = cell.getRowIndex();
          }
        }
        
        return selectedRow;
      }

}
