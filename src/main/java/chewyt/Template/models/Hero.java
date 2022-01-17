package chewyt.Template.models;

import java.io.Serializable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Hero implements Serializable {
   
    private String name;
    private String imgUrl;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return this.toJson().toString();
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("name", name)
                .add("imgUrl", imgUrl)
                .build();
    }

    public static Hero create(JsonObject o) {
        Hero h = new Hero();
        h.setName(o.getString("name"));
        h.setImgUrl(o.getJsonObject("thumbnail").getString("path"));
        return h;
    }
    
    public static Hero createfromJsonArray(JsonObject o) {
        Hero h = new Hero();
        h.setName(o.getString("name"));
        h.setImgUrl(o.getString("imgUrl"));
        return h;
    }

    public static Hero create(String jsonString) {
        try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
            final JsonReader reader = Json.createReader(is);
            return create(reader.readObject());

        } catch (Exception e) {
            return new Hero();
        }
    }

}
