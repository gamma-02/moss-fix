package net.gamma02.mossfix.mixin;



import net.gamma02.mossfix.MossFix;
import net.minecraft.block.BlockState;
import net.minecraft.block.MossBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;


import java.util.Random;
@Mixin(MossBlock.class)
public class MossMixin {

    @Overwrite
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient){
        System.out.println("eeeeeee");
        return true;
    }
    @Overwrite
    /**I have to overwrite because I don't know any other way lol**/
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state){
        MossFix.MOSS_PATCH_FEATURE.generate(new FeatureContext(world, world.getChunkManager().getChunkGenerator(), random, pos.up(), ConfiguredFeatures.MOSS_PATCH_BONEMEAL.getConfig()));
        System.out.println("generated");
    }

}