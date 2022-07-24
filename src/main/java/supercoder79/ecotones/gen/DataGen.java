package supercoder79.ecotones.gen;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public final class DataGen {
    public static final Logger LOGGER = LogManager.getLogger("ecotones-datagen");
    public static final DataGenData DATA = new DataGenData();

    private static final boolean RUN = true;
    public static void run() {
        if (RUN && FabricLoader.getInstance().isDevelopmentEnvironment()) {
            try {
                LOGGER.info("Hello! Spooling up datagen...");

                long start = System.currentTimeMillis();

                LangFileGen.init();

                runDataGen();

                LangFileGen.commit();

                LOGGER.info("Datagen finished in " + (System.currentTimeMillis() - start) + "ms.");
                DATA.log();
                LOGGER.info("That's all from me, have a nice day!");
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        }
    }

    private static void runDataGen() throws IOException {
        crossBlock("flame_lily", "Flame Lily");

        RecipeGen.shapeless("flame_lily_to_dye", "minecraft:red_dye", 1, "ecotones:flame_lily");
    }

    private static void crossBlock(String name, String localizedName) throws IOException {
        BlockstateGen.simple(name);
        BlockModelGen.cross(name);
        ItemModelGen.flat(name);

        LangFileGen.addBlock(name, localizedName);
        LangFileGen.addItemblock(name, localizedName);

        LootTableGen.dropsSelf(name);
    }

    public static class DataGenData {
        public int blockstates = 0;
        public int blockModels = 0;
        public int itemModels = 0;
        public int langs = 0;
        public int loottables = 0;
        public int recipes = 0;

        public void log() {
            LOGGER.info("Created " + blockstates + " blockstate jsons.");
            LOGGER.info("Created " + blockModels + " block model jsons.");
            LOGGER.info("Created " + itemModels + " item model jsons.");
            LOGGER.info("Updated " + langs + " lang entries.");
            LOGGER.info("Created " + loottables + " loot table jsons.");
            LOGGER.info("Created " + recipes + " recipe jsons.");
            LOGGER.info("That's a total of " + (blockstates + blockModels + itemModels + loottables + recipes) + " jsons.");
        }
    }
}
