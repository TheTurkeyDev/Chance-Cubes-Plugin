package io.github.vertex101.chancecubes;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
        id = "chancecubes",
        name = "ChanceCubes",
        description = "A block that gives you a random event that can be good or bad",
        authors = {
                "Vertex101",
                "Turkey2349"
        }
)
public class ChanceCubes {

    @Inject
    private Logger logger;

    @Listener
    public void onPreInit(GamePreInitializationEvent event) {
    }
    @Listener
    public void onPreInit(GameInitializationEvent event) {
    }
    @Listener
    public void onPreInit(GamePostInitializationEvent event) {
    }
}
