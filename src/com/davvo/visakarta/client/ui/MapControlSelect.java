package com.davvo.visakarta.client.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.davvo.visakarta.shared.MapControl;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;

public class MapControlSelect extends FlowPanel implements HasValue<Collection<MapControl>> {

    private Map<MapControl, CheckBox> checkBoxes;
    private Set<MapControl> active = new HashSet<MapControl>();
    
    public MapControlSelect() {
        checkBoxes = new HashMap<MapControl, CheckBox>();
        
        for (final MapControl control: MapControl.values()) {
            CheckBox cb = new CheckBox(control.getLabel());
            cb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

                @Override
                public void onValueChange(ValueChangeEvent<Boolean> event) {
                    setValue(control, event.getValue());
                }
            });
            
            checkBoxes.put(control, cb);
            add(cb);
        }
    }
    
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Collection<MapControl>> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    @Override
    public Collection<MapControl> getValue() {
        return new HashSet<MapControl>(active);
    }

    @Override
    public void setValue(Collection<MapControl> value) {
        active.clear();
        active.addAll(value);
        for (Map.Entry<MapControl, CheckBox> e: checkBoxes.entrySet()) {
            e.getValue().setValue(value.contains(e.getKey()));
        }
    }

    @Override
    public void setValue(Collection<MapControl> value, boolean fireEvents) {
        Collection<MapControl> oldValue = getValue();
        setValue(value);
        if (fireEvents && !equals(oldValue, value)) {
            ValueChangeEvent.fire(this, value);
        }
    }
    
    private void setValue(MapControl control, boolean enabled) {
        Collection<MapControl> controls = getValue();
        if (enabled) {
            controls.add(control);
        } else {
            controls.remove(control);
        }
        setValue(controls, true);
    }
    
    private boolean equals(Collection<MapControl> a, Collection<MapControl> b) {
        return a.size() == b.size() && a.containsAll(b) && b.containsAll(a);
    }
    
        
}
