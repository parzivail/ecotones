package supercoder79.ecotones.util.compat;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.world.gen.BiomeGenData;
import supercoder79.ecotones.world.surface.system.ConfiguredSurfaceBuilder;
import supercoder79.ecotones.world.surface.system.ForwardSurfaceConfig;
import supercoder79.ecotones.world.surface.system.SurfaceBuilder;

public class YttrCompat {
    private static Identifier id(String path) {
        return new Identifier("yttr", path);
    }

    private static RegistryKey<Biome> key(String path) {
        return RegistryKey.of(Registry.BIOME_KEY, id(path));
    }

    public static void associateGenData() {
        BiomeGenData.LOOKUP.put(key("wasteland"), new BiomeGenData( 0.2, 0.15, 0.95, 2.4,
                new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new ForwardSurfaceConfig(
                        () -> Registry.BLOCK.get(id("wasteland_dirt")).getDefaultState(),
                        () -> Registry.BLOCK.get(id("wasteland_dirt")).getDefaultState(),
                        () -> Registry.BLOCK.get(id("wasteland_dirt")).getDefaultState()
                ))));

    }

    public static void init() {
        Biome wasteland = Ecotones.REGISTRY.get(id("wasteland"));
        Climate.HOT_VERY_DRY.add(wasteland, 0.3);
        Climate.HOT_DRY.add(wasteland, 0.3);
        Climate.WARM_VERY_DRY.add(wasteland, 0.3);
        BiomeRegistries.registerNoBeachBiome(wasteland);
    }
}
