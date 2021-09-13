package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.VegetationPatchFeatureConfig;

public class ExampleMod implements ModInitializer {
	public static final Feature<VegetationPatchFeatureConfig> MOSS_PATCH = new MossVegetationPatchFeature(VegetationPatchFeatureConfig.CODEC);
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");
		Registry.register(Registry.FEATURE, new Identifier("moss_fix", "moss_patch_feature"), MOSS_PATCH);

	}
}
