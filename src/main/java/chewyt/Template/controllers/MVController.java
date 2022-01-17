package chewyt.Template.controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import chewyt.Template.TemplateApplication;
import chewyt.Template.models.Hero;
import chewyt.Template.*;
import chewyt.Template.services.*;
import static chewyt.Template.Constants.*;

@Controller
@RequestMapping(path = "/hero")
public class MVController {

    private final Logger logger = Logger.getLogger(MVController.class.getName());

    @Autowired
    mainService service;

    @Autowired
    cacheService cacheService;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String getHero(@RequestBody MultiValueMap<String, String> form, Model model) {

        String hero = form.getFirst("hero");
        logger.info("Hero search term : %s".formatted(hero));

        if (hero.equals("")) {
            model.addAttribute("error", "Empty field. Please try again.");
            return "index";
        }

        // Check if resource is available in Redis cache
        // Initialize optional object

        Optional<List<Hero>> opt = cacheService.get(hero);
        List<Hero> searchList = Collections.emptyList();

        if (opt.isPresent()) {
            logger.info("Cache hit for %s".formatted(hero));
            // Loading from cache service
            searchList = opt.get();
        } else {
            try {
                // Running from primary service
                logger.info(">>>>>Running try catch for mainService");
                searchList = service.getHero(hero);
                logger.info("Is Hero List empty: %s".formatted(searchList.isEmpty()));
                if (searchList.size() > 0) {
                    // Saving to cache
                    cacheService.save(hero, searchList);
                }
            } catch (Exception e) {
                logger.warning("Warning: %s".formatted(e.getMessage()));
                model.addAttribute("error", "Search term found no results. Please try again.");
                return "index";
            }
        }

        logger.log(Level.INFO, "Data: %s".formatted(searchList));
        model.addAttribute("heroes", searchList);
        
        return "results";
    }

}
