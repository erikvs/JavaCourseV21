package no.hiof.erikvs;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.rendering.vue.VueComponent;
import no.hiof.erikvs.controller.PlanetSystemController;
import no.hiof.erikvs.repository.UniverseDataRepository;
import org.jetbrains.annotations.NotNull; //@NotNull lets you know if the context is null - is not needed to function

public class Application {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start();

    /**2.2 Hello World in Javalin**/
     /* app.get("/", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception { //@NotNull lets you know if the context is null - is not needed to function
                ctx.result("Hello World in Javalin!");
            }
        });*/

       app.config.enableWebjars();

        UniverseDataRepository universeRepository = new UniverseDataRepository();
        PlanetSystemController planetSystemController = new PlanetSystemController(universeRepository);


        /** All planet systems **/
        app.get("/", new VueComponent("planet-system-overview"));
        app.get("/api/planet-system/", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetSystemController.getAllPlanetSystems(ctx);
            }
        });

        /** A given planet system **/
        app.get("/planet-system/:planet-system-id", new VueComponent("planet-system-detail"));
        app.get("/api/planet-system/:planet-system-id", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetSystemController.getPlanetSystem(ctx);
            }
        });

        /** All planets **/
        app.get("/api/planet-system/:planet-system-id/planets", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetSystemController.getAllPlanets(ctx);

            }
        });

        /** A given planet **/
        app.get("/planet-system/:planet-system-id/planets/:planet-id", new VueComponent("planet-detail"));
        app.get("/api/planet-system/:planet-system-id/planets/:planet-id", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetSystemController.getSinglePlanet(ctx);
            }
        });
    }
}
