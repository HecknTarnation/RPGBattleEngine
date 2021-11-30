package com.heckntarnation.rpgbattleengine.handlers;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.Cons.*;
import com.heckntarnation.rpgbattleengine.arrays.StatusArray;
import com.heckntarnation.rpgbattleengine.enums.EquipSlot;
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
    public final String DROP = "drop";
    public final String GRAPHIC = "graphic";
    public final String EQUIPMENT = "equipment";

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

    public final TypedObject LOAD_LATER = new TypedObject("builtin:load_later", null);

    private final ArrayList<JSONObject> loadLater = new ArrayList<>();

    public Object[] fromJSON(File[] files) throws ObjectAlreadyLoadedException {
        ArrayList<Object> objs = new ArrayList<>();
        JSONParser parser = new JSONParser();
        for (File f : files) {
            try {
                objs.add(fromJSON((JSONObject) parser.parse(new FileReader(f))));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JSONHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ParseException ex) {
                Logger.getLogger(JSONHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return objs.toArray();
    }

    public TypedObject[] fromJSON(JSONObject[] files) throws ObjectAlreadyLoadedException {
        ArrayList<TypedObject> objs = new ArrayList<>();

        for (JSONObject file : files) {
            TypedObject o = fromJSON(file);
            if (o == LOAD_LATER) {
                loadLater.add(file);
                continue;
            }
            objs.add(o);
        }
        int index = 0;
        while (!loadLater.isEmpty()) {
            TypedObject o = fromJSON(loadLater.get(index));
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
        TypedObject[] to = new TypedObject[objs.size()];
        return objs.toArray(to);
    }

    public TypedObject fromJSON(File file) throws ObjectAlreadyLoadedException {
        JSONParser parser = new JSONParser();
        JSONObject f = null;
        try {
            f = (JSONObject) parser.parse(new FileReader(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JSONHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(JSONHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fromJSON(f);
    }

    public TypedObject fromJSON(JSONObject file) throws ObjectAlreadyLoadedException {
        Object obj = null;
        String namespace, id;
        String[] namespacedid = ((String) file.get("id")).split(":");
        namespace = namespacedid[0];
        id = namespacedid[1];
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
                break;
            }
            case ENEMY: {
                Enemy en = new Enemy("", (int) file.get("expVal"));
                en.namespace = namespace;
                en.id = id;
                en.setStats(StatusArray.fromJSONObj(file));
                obj = en;
                break;
            }
            case DROP: {
                Item it = (Item) loadedObjects.get((String) file.get("item")).object;
                if (it == null) {
                    return LOAD_LATER;
                }
                Drop drop = new Drop(it, (String) file.get("chance"));
                obj = drop;
                break;
            }
            case GRAPHIC: {
                Graphic gr = new Graphic();
                gr.setTime((int) file.get("time"));
                JSONArray jsonArr = (JSONArray) file.get("frames");
                String[] frameArray = new String[jsonArr.size()];
                frameArray = (String[]) jsonArr.toArray(frameArray);
                gr.setFrames(frameArray);
                obj = gr;
                break;
            }
            case EQUIPMENT: {
                EquipSlot slot = EquipSlot.getFromString((String) file.get("slot"));
                Equipment equip = new Equipment((String) file.get("name"), slot);
                obj = equip;
                break;
            }
        }
        if (getObject(namespace + ":" + id) != null) {
            throw new ObjectAlreadyLoadedException(namespace + ":" + id);
        }
        TypedObject tObj = new TypedObject(type, obj);
        tObj.namespace = namespace;
        tObj.id = id;
        loadedObjects.put(namespace + ":" + id, tObj);
        return tObj;
    }

    public class TypedObject {

        public String type;
        public Object object;

        public String namespace;
        public String id;

        public TypedObject(String type, Object obj) {
            this.type = type;
            this.object = obj;
        }

        public TypedObject(String id, String type, Object obj) {
            this.namespace = id.split(":")[0];
            this.id = id.split(":")[1];
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
