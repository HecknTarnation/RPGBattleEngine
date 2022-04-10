package com.heckntarnation.rpgbattleengine.handlers;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.arrays.StatusArray;
import com.heckntarnation.rpgbattleengine.cons.Battle.Graphic;
import com.heckntarnation.rpgbattleengine.cons.Battle.Move;
import com.heckntarnation.rpgbattleengine.cons.Battle.StatusEffect;
import com.heckntarnation.rpgbattleengine.cons.Battle.Weakness;
import com.heckntarnation.rpgbattleengine.cons.Characters.Companion;
import com.heckntarnation.rpgbattleengine.cons.Characters.Enemy;
import com.heckntarnation.rpgbattleengine.cons.Characters.Player;
import com.heckntarnation.rpgbattleengine.cons.Items.Drop;
import com.heckntarnation.rpgbattleengine.cons.Items.Equipment;
import com.heckntarnation.rpgbattleengine.cons.Items.Item;
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
 * The handler for loading JSON files. (This class is currently not supported, I
 * am having trouble finding an effective way to do this. You are free to
 * override this handler with a working version in your game/engine)
 *
 * TODO: make this work
 *
 * TODO: make it use the itemList HashMap in the BattleEngine class.
 *
 * @author Ben
 */
public class JSONHandler {

    public final String ITEM = "item";
    public final String ENEMY = "enemy";
    public final String DROP = "drop";
    public final String GRAPHIC = "graphic";
    public final String EQUIPMENT = "equipment";
    public final String COMPANION = "companion";
    public final String PLAYER = "player";
    public final String MOVE = "move";
    public final String STATUS_EFFECT = "status_effect";
    public final String WEAKNESS = "weakness";

    public TypedObject getObject(String key) {
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

    public TypedObject[] fromJSON(File[] files) throws ObjectAlreadyLoadedException {
        JSONObject[] jsonObjs = new JSONObject[files.length];
        JSONParser parser = new JSONParser();
        for (int i = 0; i < files.length; i++) {
            try {
                jsonObjs[i] = (JSONObject) parser.parse(new FileReader(files[i]));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JSONHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ParseException ex) {
                Logger.getLogger(JSONHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fromJSON(jsonObjs);
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
        if (getObject(namespace + ":" + id) != null) {
            throw new ObjectAlreadyLoadedException(namespace + ":" + id);
        }
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
                Enemy en = new Enemy((String) file.get("name"), (int) file.get("expVal"));
                en.namespace = namespace;
                en.id = id;
                en.setStats(StatusArray.fromJSONObj(file, en));
                JSONArray drops = (JSONArray) file.get("drops");
                if (drops != null) {
                    String[] dropIds = new String[drops.size()];
                    dropIds = (String[]) drops.toArray(dropIds);
                    Drop[] dr = new Drop[dropIds.length];
                    for (int i = 0; i < dr.length; i++) {
                        Drop d = (Drop) loadedObjects.get(dropIds[i]).object;
                        if (d == null) {
                            return LOAD_LATER;
                        }
                        dr[i] = d;
                    }
                    en.drops = dr;
                }
                en.weaknesses = Weakness.fromJSONforCharacter((JSONObject) file.get("stats"));
                obj = en;
                break;
            }
            case DROP: {
                Item it = (Item) loadedObjects.get((String) file.get("item")).object;
                if (it == null) {
                    return LOAD_LATER;
                }
                Drop drop = new Drop(it, (String) file.get("chance"));
                drop.namespace = namespace;
                drop.id = id;
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
                gr.namespace = namespace;
                gr.id = id;
                obj = gr;
                break;
            }
            case EQUIPMENT: {
                EquipSlot slot = EquipSlot.getFromString((String) file.get("slot"));
                Equipment equip = new Equipment((String) file.get("name"), slot);
                equip.namespace = namespace;
                equip.id = id;
                obj = equip;
                break;
            }
            case COMPANION: {
                Companion comp = new Companion((String) file.get("name"), (int) file.get("baseExp"), (int) file.get("expMod"));
                comp = (Companion) loadPlayer(comp, file);
                if (comp == LOAD_LATER.object) {
                    return LOAD_LATER;
                }
                comp.namespace = namespace;
                comp.id = id;
                obj = comp;
                break;
            }
            case PLAYER: {
                Player plr = new Player((String) file.get("name"), (int) file.get("baseExp"), (int) file.get("expMod"));
                plr = loadPlayer(plr, file);
                if (plr == LOAD_LATER.object) {
                    return LOAD_LATER;
                }
                plr.namespace = namespace;
                plr.id = id;
                obj = plr;
                break;
            }
            case STATUS_EFFECT:
                StatusEffect effect = new StatusEffect((Object[]) file.get("properties"));
                effect.tickScript = (String) file.get("tickScript");
                effect.namespace = namespace;
                effect.id = id;
                obj = effect;
                break;
            case WEAKNESS:
                Weakness weak = new Weakness((String) file.get("type"), (float) file.get("effectiveness"));
                weak.namespace = namespace;
                weak.id = id;
                obj = weak;
                break;
        }
        TypedObject tObj = new TypedObject(type, obj);
        tObj.namespace = namespace;
        tObj.id = id;
        loadedObjects.put(namespace + ":" + id, tObj);
        return tObj;
    }

    private Player loadPlayer(Player obj, JSONObject file) {
        obj.level = (int) file.get("level");
        obj.growthRates = (double[]) file.get("growthRates");
        obj.health = (int) file.get("health");
        obj.maxHealth = (int) file.get("maxHealth");
        obj.mana = (int) file.get("mana");
        obj.attack = (int) file.get("attack");
        obj.mAttack = (int) file.get("mAttack");
        obj.luck = (int) file.get("luck");
        obj.defense = (int) file.get("defense");
        obj.mDefense = (int) file.get("mDefense");
        obj.exp = (int) file.get("exp");
        obj.expToNextLevel = (int) file.get("expToNextLevel");
        obj.evasion = (float) file.get("evasion");
        JSONArray moveIds = (JSONArray) file.get("moves");
        ArrayList<Move> moves = new ArrayList<>();
        for (Object o : moveIds) {
            String id = (String) o;
            Move m = (Move) getObject(id).object;
            if (m == null) {
                return (Player) LOAD_LATER.object;
            }
            moves.add(m);
        }
        obj.moves = moves;
        JSONArray equipmentIds = (JSONArray) file.get("equipment");
        ArrayList<Equipment> equipment = new ArrayList<>();
        for (int i = 0; i < equipmentIds.size(); i++) {
            String id = (String) equipmentIds.get(i);
            Equipment e = (Equipment) getObject(id).object;
            if (e == null) {
                return (Player) LOAD_LATER.object;
            }
            equipment.add(e);
        }
        obj.equipment = equipment.toArray(obj.equipment);
        JSONArray statusEffects = (JSONArray) file.get("statusEffects");
        ArrayList<StatusEffect> effects = StatusEffect.fromJSON(statusEffects, obj);
        obj.statusEffects = effects;
        //TODO: inv
        //TODO: weaknesses
        return obj;
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
