package net.gamma02.mossfix;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.VegetationPatchFeatureConfig;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

import static net.minecraft.world.gen.feature.ConfiguredFeatures.MOSS_VEGETATION;

public class MossFix implements DedicatedServerModInitializer, ModInitializer {
	public static final Feature<VegetationPatchFeatureConfig> MOSS_PATCH_FEATURE = new MossVegetationPatchFeature(VegetationPatchFeatureConfig.CODEC);
	public static final ConfiguredFeature<VegetationPatchFeatureConfig, ?> MOSS_PATCH = MOSS_PATCH_FEATURE.configure(new VegetationPatchFeatureConfig(BlockTags.MOSS_REPLACEABLE.getId(), new SimpleBlockStateProvider(Blocks.MOSS_BLOCK.getDefaultState()), () -> {
		return MOSS_VEGETATION;
	}, VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0.0F, 5, 0.8F, UniformIntProvider.create(4, 7), 0.3F));

	@Override
	public void onInitializeServer() {
		Registry.register(Registry.FEATURE, new Identifier("moss_fix", "moss_patch_feature"), MOSS_PATCH_FEATURE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier("moss_fix", "moss_patch_configured_feature"), MOSS_PATCH);
	}
	public void onInitialize(){

	}

}
