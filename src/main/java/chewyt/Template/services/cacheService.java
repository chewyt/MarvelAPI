package chewyt.Template.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chewyt.Template.models.Hero;
import chewyt.Template.repositories.standardRepo;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;


@Service
public class cacheService {
    
    @Autowired
    standardRepo repo;

    private final Logger logger = Logger.getLogger(cacheService.class.getName());

    public Optional<List<Hero>> get(String jsonString) {

        Optional<String> opt  = repo.get(jsonString);
        if (opt.isEmpty()) {
            logger.info("Search term \" %s \"is not found".formatted(jsonString));
            return Optional.empty();
        }
        else{
            //converting Json string back to JSon Array
            //opt.get ==> Json String, which can be unstringify to JsonArray or JsonObject
            JsonArray jsonarray  = parseJsonArray(opt.get());
            // JsonObject jsonObject = parseJsonObject(opt.get());
            
            
            //For Arrays
            //converting into a list of Weather objects
            List<Hero> thatList  = jsonarray.stream()
                .map(v->(JsonObject)v)     //cast as a stream of Json Objects
                .map(Hero::createfromJsonArray) //cast as a stream of Weather objects
                .collect(Collectors.toList()); //collect as a Collection List of Weather Objects
            return Optional.of(thatList);

            //For Object
            //converting into a list of Weather objects
            
            // return Optional.of(Hero.create());

        }
    }

    private JsonArray parseJsonArray(String jsonString){
        try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
            JsonReader reader = Json.createReader(is);
            return reader.readArray();            
        } catch (Exception e) {
            //Log errors
        }
        return Json.createArrayBuilder().build();
    }
    
    private JsonObject parseJsonObject(String jsonString){
        try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
            JsonReader reader = Json.createReader(is);
            return reader.readObject();            
        } catch (Exception e) {
            //Log errors
        }
        return Json.createObjectBuilder().build();
    }

    public void save(String hero, List<Hero> searchList) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        searchList.stream()
            .forEach(v->arrayBuilder.add(v.toJson()));
        repo.save(hero, arrayBuilder.build().toString());
    
    }

    //List weater convert to Json array back to Json string
    // public void save(String cityName, List<Weather> weather) {
    //     JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    //     weather.stream()
    //         .forEach(v->arrayBuilder.add(v.toJson()));
    //     repo.save(cityName, arrayBuilder.build().toString());
            
    // }
    

}
