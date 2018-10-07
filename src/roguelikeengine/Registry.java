/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine;

import roguelikeengine.item.MaterialItem;
import roguelikeengine.item.ItemDefinition;
import roguelikeengine.item.ItemScript;
import roguelikeengine.item.MaterialDefinition;
import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import roguelikeengine.area.TerrainDefinition;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.item.DamageScript;
import roguelikeengine.item.EquipmentProfile;
import roguelikeengine.largeobjects.BiologyScript;
import roguelikeengine.largeobjects.BodyDefinition;
import roguelikeengine.largeobjects.MeleeAttack;
import stat.StatContainer;
import util.RawReader;

/**
 *
 * @author Greg
 */
public class Registry extends RawReader {
    
    public HashMap<String, MaterialDefinition> materials;
    public HashMap<String, ItemDefinition> items;
    public HashMap<String, BodyDefinition> bodyTypes;
    public HashMap<String, TerrainDefinition> terrainTypes;
    
    public Registry() {
        super();
        items = new HashMap<>();
        materials = new HashMap<>();
        bodyTypes = new HashMap<>();
        terrainTypes =  new HashMap<>();
    }
    
    public MaterialDefinition readJSONMaterial(JSONObject m) {
    	String name = (String) m.get("name");
        Color c = readJSONColor((JSONArray) m.get("color"));
        
        StatContainer stats = readJSONStats((JSONArray) m.get("stats"));
        
        String scriptFile = (String) m.get("script");
        
        MaterialDefinition mat = new MaterialDefinition(name, c, stats, 
        (DamageScript) readGroovyScript(new File(scriptFile)));
        return mat;
    }
    
    public void readJSONMaterials(File file) {
        JSONParser parser = new JSONParser();
	try {
		JSONArray matdefs = (JSONArray) parser.parse(new FileReader(file));
                for (Object e : matdefs) {
                    MaterialDefinition mat = readJSONMaterial((JSONObject) e);
                    materials.put(mat.getName(), mat);
                }
 
	} catch (IOException | ParseException e) {
		Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, e);
	} 
    }
    
    private DisplayChar readJSONDisplayChar(JSONObject jo) {
         JSONArray ja = (JSONArray) jo.get("color");
         return new DisplayChar(((String) jo.get("symbol")).charAt(0), 
                            readJSONColor(ja));
    }
    
    private DisplayChar readJSONDisplayChar(JSONArray ja) {
         return new DisplayChar(((String) ja.get(0)).charAt(0), readJSONColor((JSONArray) ja.get(1)));
    }
    
    public String[] readJSONNames(JSONObject jsonObject) {
    	String names[] = new String[3];
        names[0] = (String) jsonObject.get("singular");
        names[1] = (String) jsonObject.get("plural");
        names[2] = (String) jsonObject.get("adjective");
        
        return names;
    }
    
    public ItemDefinition readJSONItemDef(JSONObject jsonItem) {
        String names[] = readJSONNames(jsonItem);
        DisplayChar symbol = readJSONDisplayChar(jsonItem);
        MaterialDefinition defmat;
        if (jsonItem.containsKey("defmat"))
        defmat = materials.get((String) jsonItem.get("defmat"));
        else defmat = null;
        
        ItemDefinition[] components = null;
        if (jsonItem.containsKey("components")) {
            JSONArray componentsJSON = (JSONArray) jsonItem.get("components");
            components = new ItemDefinition[componentsJSON.size()];
            int i = 0;
            for (Object o : componentsJSON) {
                String component = (String) o;
                components[i] = items.get(component);
                i++;
            }
        }
        
        StatContainer stats = readJSONStats((JSONArray) jsonItem.get("stats"));
        ItemDefinition itemdef;
        if (jsonItem.containsKey("script")) {
            ItemScript use = (ItemScript) readGroovyScript(new File((String) jsonItem.get("script")));
            itemdef = new ItemDefinition(symbol, names, defmat, stats, use, components);
        } else {
            itemdef = new ItemDefinition(symbol, names, defmat, stats, null, components);
        }
        
        JSONArray attacks = (JSONArray) jsonItem.get("attacks");
        for (Object o : attacks) {
            JSONArray attack = (JSONArray) o;
            String attackName = (String) attack.get(0);
            StatContainer  attackStats = readJSONStats((JSONArray) attack.get(1));
            itemdef.addAttack(new MeleeAttack(attackName, attackStats));
        }
        
        if (jsonItem.containsKey("equipment")) {
            JSONArray equipmentSlots = (JSONArray) jsonItem.get("equipment");
            for (Object o : equipmentSlots) {
                JSONArray equipmentSlot = (JSONArray) o;
                String [] slots = new String[equipmentSlot.size()];
                int i = 0;
                for (Object obj : equipmentSlot) {
                    String slot = (String) obj;
                    slots[i] = slot;
                    i++;
                }
                itemdef.equipmentSlots.add(new EquipmentProfile(slots));
            }
        }
        return itemdef;
    }
    
    public void readJSONItemDefs(File file) {
        JSONParser parser = new JSONParser();
	try {
		JSONArray itemdefs = (JSONArray) parser.parse(new FileReader(file));
                for (Object e : itemdefs) {
                    ItemDefinition itemdef = readJSONItemDef((JSONObject) e);
                    
                    items.put(itemdef.getName(0), itemdef);
                }
 
	} catch (IOException | ParseException e) {
		Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, e);
	} 
    }
    
    public MaterialItem readJSONMaterialItem(JSONArray item) {
        MaterialDefinition matdef = materials.get((String) item.get(0));
        ItemDefinition itemdef = items.get((String) item.get(1));
        return new MaterialItem(matdef, itemdef);
    }
    
//    public CompositeItem readJSONCompositeItem(JSONArray item) {
//        String name = (String) item.get(0);
//        DisplayChar display = readJSONDisplayChar((JSONArray) item.get(1));
//        StatContainer stats = readJSONStats((JSONArray) item.get(2));
//        CompositeItem ret;
//        if (item.size() == 5) {
//            ItemScript use = (ItemScript) readGroovyScript(new File((String) item.get(4)));
//            ret = new CompositeItem(name, display, stats, use);
//        } else {
//            ret = new CompositeItem(name, display, stats, null);
//        }
//                    
//        for (Object o : (JSONArray) item.get(3)) {
//            ret.addPart(readJSONItem((JSONArray) o));
//            System.out.println(readJSONItem((JSONArray) o).getName());
//        }
//        return ret;
//    }
//    
//    public Item readJSONItem(JSONArray item) {
//        if (item.size() == 4) return readJSONCompositeItem(item);
//        else if (item.size() == 2) return readJSONSimpleItem(item);
//        return null;
//    }
    
    public BodyDefinition readJSONBodyDef(JSONObject m) {
    	String name = (String) m.get("name");
        
        DisplayChar d = readJSONDisplayChar(m);
        
        StatContainer stats = readJSONStats((JSONArray) m.get("stats"));
        
        BodyDefinition bodydef;
        BiologyScript script = (BiologyScript) readGroovyScript(new File((String) m.get("script")));
        if (m.containsKey("bodytemplate")) {
            ItemDefinition bodyTemplate = items.get((String) m.get("bodytemplate"));
            bodydef = new BodyDefinition(name, d, stats, script, bodyTemplate);
        } else bodydef = new BodyDefinition(name, d, stats, script, null);
        return bodydef;
    }
    
    public void readJSONBodyDefs(File file) {
        JSONParser parser = new JSONParser();
	try {
		JSONArray itemdefs = (JSONArray) parser.parse(new FileReader(file));
                for (Object e : itemdefs) {
                    
                    
                    BodyDefinition bodydef = readJSONBodyDef((JSONObject) e);
                    
                    bodyTypes.put(bodydef.getName(), bodydef);
                }
 
	} catch (IOException | ParseException e) {
		Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, e);
	} 
    }
    
    public TerrainDefinition readJSONTerrainDef(JSONObject m) {
    	String name = (String) m.get("Name");
        DisplayChar symbol = readJSONDisplayChar(m);
        MaterialDefinition mat;
        mat = materials.get((String) m.get("Material"));
        
        StatContainer stats = readJSONStats((JSONArray) m.get("Stats"));
        
        TerrainDefinition terrain = new TerrainDefinition(name, symbol, mat, stats);
        
        return terrain;
    }
    
    public void readJSONTerrainDefs(File file) {
        JSONParser parser = new JSONParser();
	try {
		JSONArray terrainDefs = (JSONArray) parser.parse(new FileReader(file));
                for (Object e : terrainDefs) {
                    TerrainDefinition terrain = readJSONTerrainDef((JSONObject) e);
                    terrainTypes.put(terrain.getName(), terrain);
                }
 
	} catch (IOException | ParseException e) {
		Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, e);
	} 
    }

}
