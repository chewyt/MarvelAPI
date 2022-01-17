// package chewyt.Template.controllers;

// import java.util.Collections;
// import java.util.List;
// import java.util.Optional;
// import java.util.logging.Logger;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import chewyt.Template.*;
// import chewyt.Template.services.*;
// import static chewyt.Template.Constants.*;


// import jakarta.json.Json;
// import jakarta.json.JsonArrayBuilder;

// @RestController
// @RequestMapping(path = "/Object", produces = MediaType.APPLICATION_JSON_VALUE)
// public class RestyController {

//     @Autowired
//     mainService service;

//     @Autowired
//     cacheService cacheService;

//     private final Logger logger = Logger.getLogger(RestyController.class.getName());
    
//     @GetMapping(path = "/{something}")
//     public ResponseEntity<String> getObjectPath(@PathVariable String something){
        
//         logger.info(">>>>>>>>Use Get Mapping Path variable");
//         logger.info("something: %s".formatted(something));

//         if (something.equals("")) {
//             return null; //TODO: return empty JSONOBject
//         }

//         Optional<List<Object>> opt = cacheService.get(something);
//         List<Object> Object = Collections.emptyList();
        
//         if (opt.isPresent()) {
//             logger.info("Cache hit for %s".formatted(something));
//             Object=opt.get();
//         }
//         else{
//             try {
//                 logger.info(">>>>>>>>Try catch for using ObjectService");
                
//                 Object=service.getObject(something);
//                 logger.info("Is Object List empty: %s".formatted(Object.isEmpty()));
//                 if (Object.size()>0){
//                     cacheService.save(something, Object);
//                 }
//             } catch (Exception e) {
//                 logger.warning("Warning: %s".formatted(e.getMessage()));
//                 return null;
//             } 
//         }
//         JsonArrayBuilder arrBuildr = Json.createArrayBuilder();
//          Object.stream()
//                 .forEach(v->arrBuildr.add(v.toJson()));
        
//         return ResponseEntity.ok(arrBuildr.build().toString());
                
//     }
// }
