package com.heckntarnation.rpgbattleengine.handlers;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.Cons.Item;
import com.heckntarnation.rpgbattleengine.exceptions.ObjectAlreadyLoadedException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Ben
 */
//TODO: write
public class JSONHandler {

    public final String ITEM = "item";
    public final String ENEMY = "enemy";

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

    public final Object LOAD_LATER = new Object();

    private final ArrayList<JSONObject> loadLater = new ArrayList<>();

    public Object[] fromJSON(File[] files) throws ObjectAlreadyLoadedException {
        ArrayList<Object> objs = new ArrayList<>();
        JSONParser parser = new JSONParser();
        for (File f : files) {
            try {
                objs.add(fromJSON((JSONObject) parser.parse(new FileReader(f))));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JSONHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JSONHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(JSONHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return objs.toArray();
    }

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
                loadLater.remove(index);
                index++;
                if (index > loadLater.size()) {
                    index = 0;
                }
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

    public Object fromJSON(File file) throws ObjectAlreadyLoadedException {
        JSONParser parser = new JSONParser();
        JSONObject f = null;
        try {
            f = (JSONObject) parser.parse(new FileReader(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JSONHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JSONHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(JSONHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fromJSON(f);
    }

    public Object fromJSON(JSONObject file) throws ObjectAlreadyLoadedException {
        Object obj = null;
        String namespace, id;
        namespace = (String) file.get("namespace");
        id = (String) file.get("id");
        String type = (String) file.get("type");
        switch (type) {
            case ITEM: {
                Item item = new Item((String) file.get("name")) {
                    public String useScriptPath = new File((String) file.get("useScript")).getAbsolutePath();

                    @Override
                    public void Use() {
                        BattleEngine.luaHandler.runscript(useScriptPath);
                    }
                };
                item.namespace = namespace;
                item.id = id;
                item.desc = (String) file.get("desc");
                obj = item;
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
