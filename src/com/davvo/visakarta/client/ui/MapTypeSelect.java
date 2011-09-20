package com.davvo.visakarta.client.ui;

import java.util.HashMap;
import java.util.Map;

import com.davvo.visakarta.shared.MapType;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RadioButton;

public class MapTypeSelect extends FlowPanel implements HasValue<MapType> {

    private MapType value;
    private Map<MapType, RadioButton> radioButtons;
    
    public MapTypeSelect() {
        radioButtons = new HashMap<MapType, RadioButton>();
        for (final MapType mapType: MapType.values()) {
            RadioButton rb = new RadioButton("mapType", mapType.toString());
            rb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

                @Override
                public void onValueChange(ValueChangeEvent<Boolean> event) {
                    if (event.getValue()) {
                        setValue(mapType, true);
                    }
                }
            });

            radioButtons.put(mapType, rb);
            add(rb);
        }
    }
    
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<MapType> handler) {
        return addHandler(handler,ValueChangeEvent.getType());
    }

    @Override
    public MapType getValue() {
        return value;
    }

    @Override
    public void setValue(MapType value) {
        this.value = value;
        radioButtons.get(value).setValue(true);
    }

    @Override
    public void setValue(MapType value, boolean fireEvents) {
        MapType oldValue = getValue();
        setValue(value);
        if (fireEvents) {
            ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
        }
    }

}
