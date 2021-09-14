package com.coolninja.rpgengine.handlers;

import com.coolninja.rpgengine.Cons.*;
import com.coolninja.rpgengine.arrays.StatusArray;
import com.coolninja.rpgengine.enums.StatusArrayPosition;
import org.json.simple.*;

/**
 *
 * @author Ben
 */
//TODO: write
public class JSONHandler {

    public JSONObject toJson(Object obj) {
        return null;
    }

    public Object fromJson(JSONObject file) {
        String type = (String) file.get("type");
        type = type.toLowerCase();
        String name = (String) file.get("name");
        switch (type) {
            case "enemy":
                int expVal = file.get("expVal") != null ? (int) file.get("expVal") : 0;
                StatusArray arr = StatusArray.fromJSONArr((JSONObject) file.get("stats"));
                Drop[] drops = null;
                return new Enemy(name, (int) arr.get(StatusArrayPosition.Health), expVal).setStats(arr).setDrop(drops);
        }
        return null;
    }

}
