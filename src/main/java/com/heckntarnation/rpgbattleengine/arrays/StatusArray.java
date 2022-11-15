package com.heckntarnation.rpgbattleengine.arrays;

import com.heckntarnation.rpgbattleengine.enums.StatusArrayPosition;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class StatusArray implements Serializable {

    public ArrayList<Object> statuses;

    public StatusArray() {
        this.statuses = new ArrayList<>(10);
        for (int i = 0; i < 20; i++) {
            statuses.add(null);
        }
    }

    public StatusArray put(StatusArrayPosition pos, Object value) {
        this.statuses.set(pos.actualPos, value);
        return this;
    }

    public Object get(StatusArrayPosition index) {
        return this.statuses.get(index.actualPos);
    }

}
