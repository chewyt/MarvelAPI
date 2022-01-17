package chewyt.Template;

public class Constants {
    
    //API URLS
    public static final String URL_MARVEL = "https://gateway.marvel.com:443/v1/public/characters";
    public static final String URL_COUNTRY = "https://flagcdn.com/en/codes.json";
    public static final String REDIS_KEY = "MARVEL_API_";
    

    //Storing in Config VARS of Heroku
    public static final String ENV_MARVELAPIKEY_PUBLIC = System.getenv("ENV_MARVEL_API"); 
    public static final String ENV_MARVELAPIKEY_HASH = System.getenv("ENV_MARVEL_API_HASH");
    public static final String ENV_REDISCLOUD = System.getenv("ENV_REDISCLOUD");
    
    //Storing in ENV path locally
    // public static final String ENV_MARVELAPIKEY_PUBLIC = System.getenv("MARVEL_API"); // Change WEATHERAPI to new variable stored in local ENV system
    // public static final String ENV_MARVELAPIKEY_HASH = System.getenv("MARVEL_API_HASH"); // Change WEATHERAPI to new variable stored in local ENV system
    // public static final String ENV_REDISCLOUD = System.getenv("REDIS_PW");

}
