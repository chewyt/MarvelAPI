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
import chewyt.Template.*;
import chewyt.Template.services.*;
import static chewyt.Template.Constants.*;

@Controller
@RequestMapping(path = "/something")
public class MVController {

    private final Logger logger = Logger.getLogger(MVController.class.getName());

    @Autowired
    mainService service;

    @Autowired
    cacheService cacheService;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String getSomething(@RequestBody MultiValueMap<String, String> form, Model model) {

        String something = form.getFirst("something");
        logger.info("Something: %s".formatted(something));

        if (something.equals("")) {
            // model.addAttribute("error", "Empty field. Please try again.");
            return "index";
        }

        // Check if resource is available in Redis cache
        // Initialize optional object

        Optional<List<Object>> opt = cacheService.get(something);
        List<Object> thatList = Collections.emptyList();

        if (opt.isPresent()) {
            logger.info("Cache hit for %s".formatted(something));
            // Loading from cache service
            thatList = opt.get();
        } else {
            try {
                // Running from primary service
                thatList = service.getWeather(something);
                logger.info("Is Weather List empty: %s".formatted(thatList.isEmpty()));
                if (thatList.size() > 0) {
                    // Saving to cache
                    // cacheService.save(city, weather);
                }
            } catch (Exception e) {
                logger.warning("Warning: %s".formatted(e.getMessage()));
                // model.addAttribute("error", "City not found. Please try again.");
                return "index";
            }
        }

        // String cityNameforDisplay = weather.get(0).getCityNameInSentenceCase();
        // String countryNameforDisplay = weather.get(0).getCountryName();
        // String countryCodeforDisplay = weather.get(0).getCountryCode();

        logger.log(Level.INFO, "Data: %s".formatted(thatList));
        // model.addAttribute("city", cityNameforDisplay);
        // model.addAttribute("country", countryNameforDisplay);
        // model.addAttribute("countryCode", countryCodeforDisplay);
        // model.addAttribute("data", weather);
        return "weather";
    }

}
