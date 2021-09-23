package com.heckntarnation.rpgbattleengine.handlers;

import com.heckntarnation.rpgbattleengine.Cons.*;
import com.heckntarnation.rpgbattleengine.arrays.StatusArray;
import com.heckntarnation.rpgbattleengine.exceptions.ObjectAlreadyLoadedException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.*;

/**
 *
 * @author Ben
 */
//TODO: write
public class JSONHandler {

    public Object getObject(String key) {
        return loadedObjects.get(key);
    }

    HashMap<String, TypedObject> loadedObjects = new HashMap<>();

    public HashMap<String, TypedObject> getLoadedObjects() {
        return loadedObjects;
    }

    public void clearLoadedObjects() {
        loadedObjects.clear();
    }

    public JSONObject toJson(Object obj) {
        return null;
    }

    private final Object LOAD_LATER = new Object();

    private ArrayList<JSONObject> loadLater = new ArrayList<>();

    public Object[] fromJSON(JSONObject[] files) throws ObjectAlreadyLoadedException {
        ArrayList<Object> objs = new ArrayList<>();

        for (JSONObject file : files) {
            Object o = fromJSON(file);
            if (o == LOAD_LATER) {
                loadLater.add(file);
                continue;
            }
            objs.add(o);
        }
        int index = 0;
        while (!loadLater.isEmpty()) {
            Object o = fromJSON(loadLater.get(index));
            if (o == LOAD_LATER) {
                loadLater.add(loadLater.get(index));
                index++;
                continue;
            }
            loadLater.remove(index);
            index++;
            objs.add(o);
            if (index > loadLater.size()) {
                index = 0;
            }
        }

        return objs.toArray();
    }

    public Object fromJSON(JSONObject file) throws ObjectAlreadyLoadedException {
        Object obj = null;
        String namespace, id;
        namespace = (String) file.get("namespace");
        id = (String) file.get("id");
        String type = (String) file.get("type");
        switch (type) {
            case "enemy": {
                Enemy en = new Enemy((String) file.get("name"), (int) file.get("expVal"));
                en.namespace = namespace;
                en.id = id;
                en.setStats(StatusArray.fromJSONObj((JSONObject) file.get("statusArray")));
                en.drops = Drop.fromJSON((JSONArray) file.get("drops"));
                obj = en;
            }
            case "item": {

            }
        }
        if (getObject(namespace + ":" + id) != null) {
            throw new ObjectAlreadyLoadedException(namespace + ":" + id);
        }
        loadedObjects.put(namespace + ":" + id, new TypedObject(type, obj));
        return obj;
    }

    public class TypedObject {

        public String type;
        public Object object;

        public TypedObject(String type, Object obj) {
            this.type = type;
            this.object = obj;
        }

        public String getType() {
            return this.type;
        }

        public Object getObject() {
            return this.object;
        }
    }
}
