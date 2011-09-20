package com.davvo.visakarta.client.ui;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public class HasValueImpl<T> implements HasValue<T> {

    HandlerManager manager = new HandlerManager(this);
    T value;
    
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
        return manager.addHandler(ValueChangeEvent.getType(), handler);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        manager.fireEvent(event);
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
       setValue(value, true);
    }

    @Override
    public void setValue(T value, boolean fireEvents) {
        T oldValue = getValue();
        this.value = value;
        if (fireEvents) {
            ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
        }
    }

}
