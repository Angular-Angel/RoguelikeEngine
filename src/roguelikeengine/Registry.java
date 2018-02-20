/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine;

import roguelikeengine.item.MaterialItem;
import roguelikeengine.item.ItemDefinition;
import roguelikeengine.item.Item;
import roguelikeengine.item.ItemScript;
import roguelikeengine.item.MaterialDefinition;
import roguelikeengine.item.CompositeItem;
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
import roguelikeengine.largeobjects.BiologyScript;
import roguelikeengine.largeobjects.BodyDefinition;
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
        items = new HashMap<>();
        materials = new HashMap<>();
        bodyTypes = new HashMap<>();
        terrainTypes =  new HashMap<>();
    }
    
    public void readJSONMaterials(File file) {
        JSONParser parser = new JSONParser();
	try {
		JSONArray matdefs = (JSONArray) parser.parse(new FileReader(file));
                for (Object e : matdefs) {
                    JSONObject m = (JSONObject) e;
                    String name = (String) m.get("name");
                    Color c = readJSONColor((JSONArray) m.get("color"));
                    
                    StatContainer stats = readJSONStats((JSONArray) m.get("stats"));
                    
                    String scriptFile = (String) m.get("script");
                    
                    MaterialDefinition mat = new MaterialDefinition(name, c, stats, 
                    (DamageScript) readGroovyScript(new File(scriptFile)));
                    materials.put(name, mat);
                    
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
         JSONArray jcolor = (JSONArray) ja.get(1);
         return new DisplayChar(((String) ja.get(0)).charAt(0), 
                            readJSONColor(jcolor));
    }
    
    public void readJSONItemDefs(File file) {
        JSONParser parser = new JSONParser();
	try {
		JSONArray itemdefs = (JSONArray) parser.parse(new FileReader(file));
                for (Object e : itemdefs) {
                    JSONObject m = (JSONObject) e;
                    String names[] = new String[3];
                    names[0] = (String) m.get("singular");
                    names[1] = (String) m.get("plural");
                    names[2] = (String) m.get("adjective");
                    DisplayChar symbol = readJSONDisplayChar(m);
                    MaterialDefinition defmat;
                    if (m.containsKey("defmat"))
                    defmat = materials.get((String) m.get("defmat"));
                    else defmat = null;
                    
                    StatContainer stats = readJSONStats((JSONArray) m.get("stats"));
                    ItemDefinition itemdef;
                    if (m.containsKey("script")) {
                        ItemScript use = (ItemScript) readGroovyScript(new File((String) m.get("script")));
                        itemdef = new ItemDefinition(symbol, names, defmat, stats, use);
                    } else {
                        itemdef = new ItemDefinition(symbol, names, defmat, stats, null);
                    }
                    
                    JSONArray attacks = (JSONArray) m.get("attacks");
                    for (Object o : attacks) {
                        JSONArray attack = (JSONArray) o;
                        //String attackName = (String) attack.get(0);
                        
                        //StatContainer  attackStats = readJSONStats((JSONArray) attack.get(1));
                        //itemdef.addAttack(new Melee(attackName, attackStats));
                    }
                    
                    items.put(names[0], itemdef);
                }
 
	} catch (IOException | ParseException e) {
		Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, e);
	} 
    }
    
    public MaterialItem readJSONSimpleItem(JSONArray item) {
        MaterialDefinition matdef = materials.get((String) item.get(0));
        ItemDefinition itemdef = items.get((String) item.get(1));
        return new MaterialItem(matdef, itemdef);
    }
    
    public CompositeItem readJSONCompositeItem(JSONArray item) {
        String name = (String) item.get(0);
        DisplayChar display = readJSONDisplayChar((JSONArray) item.get(1));
        StatContainer stats = readJSONStats((JSONArray) item.get(2));
        CompositeItem ret;
        if (item.size() == 5) {
            ItemScript use = (ItemScript) readGroovyScript(new File((String) item.get(4)));
            ret = new CompositeItem(name, display, stats, use);
        } else {
            ret = new CompositeItem(name, display, stats, null);
        }
                    
        for (Object o : (JSONArray) item.get(3)) {
            ret.addPart(readJSONItem((JSONArray) o));
        }
        return ret;
    }
    
    public Item readJSONItem(JSONArray item) {
        if (item.size() == 4) return readJSONCompositeItem(item);
        else if (item.size() == 2) return readJSONSimpleItem(item);
        return null;
    }
    
    public void readJSONBodyDefs(File file) {
        JSONParser parser = new JSONParser();
	try {
		JSONArray itemdefs = (JSONArray) parser.parse(new FileReader(file));
                for (Object e : itemdefs) {
                    JSONObject m = (JSONObject) e;
                    String name = (String) m.get("name");
                    
                    DisplayChar d = readJSONDisplayChar(m);
                    
                    StatContainer stats = readJSONStats((JSONArray) m.get("stats"));
                    
                    //JSONArray bodyparts = (JSONArray) m.get("bodyparts");
                    BodyDefinition bodydef;
                    BiologyScript script = (BiologyScript) readGroovyScript(new File((String) m.get("script")));
                    bodydef = new BodyDefinition(name, d, stats, script);
                     
//                    for (Object o : bodyparts) {
//                        bodydef.bodyTemplate.addPart(readJSONItem((JSONArray) o));
//                    }
                    bodyTypes.put(name, bodydef);
                }
 
	} catch (IOException | ParseException e) {
		Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, e);
	} 
    }
    
    public void readJSONTerrainDefs(File file) {
        JSONParser parser = new JSONParser();
	try {
		JSONArray itemdefs = (JSONArray) parser.parse(new FileReader(file));
                for (Object e : itemdefs) {
                    JSONObject m = (JSONObject) e;
                    String name = (String) m.get("Name");
                    DisplayChar symbol = readJSONDisplayChar(m);
                    MaterialDefinition mat;
                    mat = materials.get((String) m.get("Material"));
                    
                    StatContainer stats = readJSONStats((JSONArray) m.get("Stats"));
                    
                    TerrainDefinition terrain = new TerrainDefinition(symbol, mat, stats);
                    
                    terrainTypes.put(name, terrain);
                }
 
	} catch (IOException | ParseException e) {
		Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, e);
	} 
    }
}
