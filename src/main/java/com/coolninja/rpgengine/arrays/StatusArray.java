package com.coolninja.rpgengine.arrays;

import com.coolninja.rpgengine.enums.StatusArrayPosition;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class StatusArray {

    public ArrayList<Object> statuses = new ArrayList<>();

    public StatusArray() {

    }

    public StatusArray modify(StatusArrayPosition pos, Object value) {
        statuses.set(pos.actualPos, value);
        return this;
    }

    public Object get(StatusArrayPosition index) {
        return statuses.get(index.actualPos);
    }

}
