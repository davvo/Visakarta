package com.davvo.visakarta.client.ui;

import com.google.gwt.event.dom.client.ChangeEvent;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;

public class IntegerListBox extends ListBox implements HasValue<Integer> {

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Integer> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    public IntegerListBox(int min, int max) {
        for (int i = min; i <= max; ++i) {
            addItem(String.valueOf(i));
        }
        
        addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                ValueChangeEvent.fire(IntegerListBox.this, getValue());
            }
        });
    }
    
    @Override
    public Integer getValue() {
        return getSelectedIndex();
    }

    @Override
    public void setValue(Integer value) {
        setValue(value, false);
    }

    @Override
    public void setValue(Integer value, boolean fireEvents) {
        int oldValue = getValue();
        setSelectedIndex(value);
        if (fireEvents) {
            ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
        }
    }

}
