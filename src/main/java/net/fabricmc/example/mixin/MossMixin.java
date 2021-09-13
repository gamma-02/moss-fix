package net.fabricmc.example.mixin;


import net.fabricmc.example.ExampleMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.MossBlock;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(MossBlock.class)
public class MossMixin {
    @Inject(at = @At("HEAD"), method = "isFertilizable", cancellable = true)
    public void isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient, CallbackInfoReturnable cir){
        cir.setReturnValue(true);
    }
    @Redirect(method = "grow", at = @At("HEAD"))
    public void growMixin(ServerWorld world, Random random, BlockPos pos, BlockState state){
        ExampleMod.MOSS_PATCH.generate(new FeatureContext(world, world.getChunkManager().getChunkGenerator(), random, pos.up(), ConfiguredFeatures.MOSS_PATCH_BONEMEAL.getConfig()));

    }

}