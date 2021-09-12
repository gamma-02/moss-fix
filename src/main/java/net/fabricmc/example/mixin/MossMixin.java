package net.fabricmc.example.mixin;


import net.minecraft.block.BlockState;
import net.minecraft.block.MossBlock;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MossBlock.class)
public class MossMixin {
    @Inject(at = @At("HEAD"), method = "isFertilizable", cancellable = true)
    public void isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient, CallbackInfoReturnable cir){
        cir.setReturnValue(true);
    }
}