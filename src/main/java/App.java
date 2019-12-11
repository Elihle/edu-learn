import spark.Filter;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static spark.Spark.*;

public class App {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }



    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getHerokuAssignedPort());
        Map<String, Object> dataMap = new HashMap<>();

        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
        });

        get("/", (req, res) -> {
            return new ModelAndView(new HashMap<>(), "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/Home", (req,res) -> {
            String name = req.queryParams("firstName").substring(0,1).toUpperCase() + req.queryParams("firstName").substring(1).toLowerCase();
            dataMap.put("userName", name);
            return new ModelAndView(dataMap, "home.hbs");
        }, new HandlebarsTemplateEngine());

        get("/Wifi", (req,res) -> {

            return new ModelAndView(new HashMap<>(), "hotspots.hbs");
        }, new HandlebarsTemplateEngine());

        get("/Material", (req,res) -> {
            return new ModelAndView(new HashMap<>(), "study.hbs");
        }, new HandlebarsTemplateEngine());

        get("/Tutor", (req,res) -> {
            return new ModelAndView(new HashMap<>(), "tutor.hbs");
        }, new HandlebarsTemplateEngine());
    }

}
