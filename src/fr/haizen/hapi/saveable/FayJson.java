package fr.haizen.hapi.saveable;

import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
	
public class FayJson {

    @Getter private JSONObject jsonObject;
    @Getter public final JSONParser jsonParser;
    @Getter private final File file;

    /**
     *
     * @param file
     */
    public FayJson(File file) {
        this.jsonObject = new JSONObject();
        this.jsonParser = new JSONParser();
        this.file = file;
    }

    /**
     *
     * @param key
     * @return
     */
    public Object get(Object key){
        return jsonObject.get(key);
    }

    /**
     *
     * @return
     */
    public FayJson read(){
        try {
            FileReader fileReader = new FileReader(file);
            this.jsonObject = (JSONObject) jsonParser.parse(fileReader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return this;

    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public FayJson set(Object key, Object value){
        jsonObject.put(key, value);
        return this;
    }

    /**
     *
     * @param key
     * @return
     */
    public FayJson remove(Object key){
        jsonObject.remove(key);
        return this;
    }

    /**
     *
     * @return
     */
    public FayJson write(){
        try {
            @SuppressWarnings("resource")
			FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }


}
