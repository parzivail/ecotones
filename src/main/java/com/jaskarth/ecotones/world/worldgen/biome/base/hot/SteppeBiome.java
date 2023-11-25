package com.jaskarth.ecotones.world.worldgen.biome.base.hot;

import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import com.jaskarth.ecotones.world.worldgen.decorator.*;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.RockFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.surface.EcotonesSurfaces;

public class SteppeBiome extends EcotonesBiomeBuilder {

    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("steppe", new SteppeBiome(0.5f, 0.075f, 2.8, 0.88, false));
        Biome thicket = EarlyBiomeRegistry.register("steppe_thicket", new SteppeBiome(0.5f, 0.075f, 3.2, 0.98, true));

        BiomeRegistries.registerBiomeVariantChance(biome, 3);
        BiomeRegistries.registerBiomeVariants(biome, thicket);

        Climate.HOT_DRY.add(biome, 1);
    }

    protected SteppeBiome(float depth, float scale, double hilliness, double volatility, boolean thicket) {
        this.surfaceBuilder(EcotonesSurfaces.COARSE_DIRT_BANDS, SurfaceBuilder.GRASS_CONFIG);
        this.precipitation(Biome.Precipitation.NONE);
        this.depth(depth);
        this.scale(scale);
        this.temperature(1.8F);
        this.downfall(0.2F);

        this.hilliness(hilliness);
        this.volatility(volatility);
        this.associate(BiomeAssociations.SAVANNA_LIKE);

        BiomeDecorator.addAcaciaShrubs(this, 3, 0.05);

        BiomeDecorator.addRock(this, Blocks.COBBLESTONE.getDefaultState(), 1, 8);
        BiomeDecorator.addSurfaceRocks(this);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BARREN_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(1.15))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DESERTIFY_SOIL.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(2, 0.25f, 3)))
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(2, 0.5f, 2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BIG_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.ABOVE_QUALITY.configure()));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BIG_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.05))));

        if (thicket) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.BIG_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                            .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(2.0))));
        }

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.075))));

        BiomeDecorator.addPatchRepeatedChance(this, FeatureConfigHolder.ONLY_TALL_GRASS_CONFIG, 2, 3);

        BiomeDecorator.addGrass(this, FeatureConfigHolder.SCRUBLAND_CONFIG, 12);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.ROSEMARY.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.ROSEMARY.configure(new ShrubDecoratorConfig(0.1))));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.SMALL_ROCK.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(10));

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);
    }
}