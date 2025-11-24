package top.mcocet.orefarm.gen;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import top.mcocet.orefarm.init.BlockInit;

public class OreFarmOreGen implements IWorldGenerator {
    private WorldGenerator poor_diamond_ore;

    public OreFarmOreGen() {
        this.poor_diamond_ore = new WorldGenMinable(BlockInit.poor_diamond_ore.getDefaultState(), 9);
    }

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
            case 0:
                this.runGenerator(this.poor_diamond_ore, world, random, chunkX, chunkZ, 16, 0, 48);
            case -1:
            case 1:
            default:
        }
    }

    private void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int change, int minHight, int maxHight) {
        if (minHight <= maxHight && minHight >= 0 && maxHight <= 256) {
            int hightDiff = maxHight - minHight + 1;

            for(int i = 0; i < change; ++i) {
                int x = chunkX * 16 + rand.nextInt(16);
                int y = minHight + rand.nextInt(hightDiff);
                int z = chunkZ * 16 + rand.nextInt(16);
                gen.generate(world, rand, new BlockPos(x, y, z));
            }

        } else {
            throw new IllegalArgumentException("Ore generator out of bounds");
        }
    }
}