//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.gamma02.mossfix;

import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.VegetationPatchFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class MossVegetationPatchFeature extends Feature<VegetationPatchFeatureConfig> {
    public MossVegetationPatchFeature(Codec<VegetationPatchFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<VegetationPatchFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        VegetationPatchFeatureConfig vegetationPatchFeatureConfig = (VegetationPatchFeatureConfig)context.getConfig();
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        Predicate<BlockState> predicate = getReplaceablePredicate(vegetationPatchFeatureConfig);
        int i = vegetationPatchFeatureConfig.horizontalRadius.get(random) + 1;
        int j = vegetationPatchFeatureConfig.horizontalRadius.get(random) + 1;
        Set<BlockPos> set = this.placeGroundAndGetPositions(structureWorldAccess, vegetationPatchFeatureConfig, random, blockPos, predicate, i, j);
        this.generateVegetation(context, structureWorldAccess, vegetationPatchFeatureConfig, random, set, i, j);
        return !set.isEmpty();
    }

    protected Set<BlockPos> placeGroundAndGetPositions(StructureWorldAccess world, VegetationPatchFeatureConfig config, Random random, BlockPos pos, Predicate<BlockState> replaceable, int radiusX, int radiusZ) {
        Mutable mutable = pos.mutableCopy();
        Mutable mutable2 = mutable.mutableCopy();
        Direction direction = config.surface.getDirection(); System.out.print(direction);
        Direction direction2 = direction.getOpposite(); System.out.println(direction2);
        Set<BlockPos> set = new HashSet();

        for(int i = -radiusX; i <= radiusX; ++i) {
            boolean bl = i == -radiusX || i == radiusX;

            for(int j = -radiusZ; j <= radiusZ; ++j) {
                boolean bl2 = j == -radiusZ || j == radiusZ;
                boolean bl3 = bl || bl2;
                boolean bl4 = bl && bl2;
                boolean bl5 = bl3 && !bl4;
                if (!bl4 && (!bl5 || config.extraEdgeColumnChance != 0.0F && !(random.nextFloat() > config.extraEdgeColumnChance))) {
                    mutable.set(pos, i, 0, j);

//                    int k;
//                    for(k = 0; world.testBlockState(mutable, AbstractBlockState::isAir) && k < config.verticalRange; ++k) {
//                        mutable.move(direction);
//                    }
//
//                    for(k = 0; world.testBlockState(mutable, (blockStatex) -> {
//                        return !blockStatex.isAir();
//                    }) && k < config.verticalRange; ++k) {
//                        mutable.move(direction2);
//                    }
                    //REMEMBER: k < config.verticalRange!!!!
                    int k;
                    for(k=0; world.testBlockState(mutable, MossVegetationPatchFeature::airSubstitute) && k < config.verticalRange; k++){//
                        mutable.move(direction);
                    }
                    for(k=0; world.testBlockState(mutable, blockState -> !airSubstitute(blockState)) && k < config.verticalRange; k++){
                        mutable.move(direction2);
                    }


                    mutable2.set(mutable, config.surface.getDirection());
                    BlockState blockState = world.getBlockState(mutable2);
                    BlockState firstState = world.getBlockState(mutable);
                    if (firstState.isIn(BlockTags.FLOWERS) || firstState.isOf(Blocks.GRASS) || firstState.isOf(Blocks.TALL_GRASS) || world.isAir(mutable) || firstState.isOf(Blocks.BIG_DRIPLEAF) || firstState.isOf(Blocks.BIG_DRIPLEAF_STEM) || firstState.isOf(Blocks.SMALL_DRIPLEAF)) {
                        int l = config.depth.get(random) + (config.extraBottomBlockChance > 0.0F && random.nextFloat() < config.extraBottomBlockChance ? 1 : 0);
                        BlockPos blockPos = mutable2.toImmutable();
                        boolean bl6 = this.placeGround(world, config, replaceable, random, mutable2, l);
                        if (bl6) {
                            set.add(blockPos);
                        }
                    }
                }
            }
        }

        return set;
    }

    protected void generateVegetation(FeatureContext<VegetationPatchFeatureConfig> context, StructureWorldAccess world, VegetationPatchFeatureConfig config, Random random, Set<BlockPos> positions, int radiusX, int radiusZ) {
        Iterator var8 = positions.iterator();

        while(var8.hasNext()) {
            BlockPos blockPos = (BlockPos)var8.next();
            if (config.vegetationChance > 0.0F && random.nextFloat() < config.vegetationChance) {
                this.generateVegetationFeature(world, config, context.getGenerator(), random, blockPos);
            }
        }

    }

    protected boolean generateVegetationFeature(StructureWorldAccess world, VegetationPatchFeatureConfig config, ChunkGenerator generator, Random random, BlockPos pos) {
        BlockState toReplace = world.getBlockState(pos);
        if(toReplace.isIn(BlockTags.FLOWERS) || toReplace.isOf(Blocks.GRASS) || toReplace.isOf(Blocks.TALL_GRASS) || toReplace.isOf(Blocks.BIG_DRIPLEAF) || toReplace.isOf(Blocks.BIG_DRIPLEAF_STEM) || toReplace.isOf(Blocks.SMALL_DRIPLEAF)) {
            return false;
        }else {
            return (config.vegetationFeature.get()).generate(world, generator, random, pos.offset(config.surface.getDirection().getOpposite()));
        }
    }

    protected boolean placeGround(StructureWorldAccess world, VegetationPatchFeatureConfig config, Predicate<BlockState> replaceable, Random random, Mutable pos, int depth) {
        for(int i = 0; i < depth; ++i) {
            BlockState blockState = config.groundState.getBlockState(random, pos);
            BlockState blockState2 = world.getBlockState(pos);
            if (!blockState.isOf(blockState2.getBlock())) {
                if (!replaceable.test(blockState2)) {
                    return i != 0;
                }

                world.setBlockState(pos, blockState, 2);
                pos.move(config.surface.getDirection());
            }
        }

        return true;
    }

    private static Predicate<BlockState> getReplaceablePredicate(VegetationPatchFeatureConfig config) {
        Tag<Block> tag = BlockTags.getTagGroup().getTag(config.replaceable);
        return tag == null ? (state) -> {
            return true;
        } : (state) -> {
            return state.isIn(tag);
        };
    }

    public static boolean airSubstitute(BlockState state){
        if(state.isIn(BlockTags.FLOWERS) || state.isOf(Blocks.GRASS) || state.isOf(Blocks.TALL_GRASS) || state.isAir() || state.isOf(Blocks.BIG_DRIPLEAF) || state.isOf(Blocks.BIG_DRIPLEAF_STEM) || state.isOf(Blocks.SMALL_DRIPLEAF)){
            return true;
        }else {
            return false;
        }
    }

}
