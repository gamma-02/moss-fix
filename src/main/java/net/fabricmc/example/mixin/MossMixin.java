package net.fabricmc.example.mixin;



import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.MossVegetationPatchFeature;
import net.minecraft.block.BlockState;
import net.minecraft.block.MossBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.VegetationPatchFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Optional;
import java.util.Random;
@Mixin(MossBlock.class)
public class MossMixin {

    /**
     * @author
     */

    @Overwrite
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient){
        System.out.println("eeeeeee");
        return true;
    }

    /**
     * @author
     */
    @Overwrite
    /*I have to overwrite because I don't know any other way lol*/
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state){
        ExampleMod.MOSS_PATCH_FEATURE.generate(new FeatureContext(Optional.empty(), world, world.getChunkManager().getChunkGenerator(), random, pos.up(), ConfiguredFeatures.MOSS_PATCH_BONEMEAL.getConfig()));
        System.out.println("generated");
    }

}