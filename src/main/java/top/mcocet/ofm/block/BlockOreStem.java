package top.mcocet.ofm.block;

import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import top.mcocet.ofm.OreFarm;

/**
 * Ore stem block, a melon/pumpkin-like plant.
 *
 * Behaviour ported from the reference mod "Plants-To-Ores" (1.7.10):
 * - Grows on farmland through age 0..7 (same random tick growth chance as vanilla stems).
 * - Once fully mature (age 7) it periodically places the matching ORE block in a random
 *   adjacent empty spot whose ground can sustain a plant (or is dirt/grass), just like a
 *   melon/pumpkin appears next to its stem.
 * - The stem itself drops nothing when broken (matches the reference mod).
 */
public class BlockOreStem extends BlockBush implements IGrowable
{
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);

    /**
     * Direction the mature vine bends toward its adjacent ore block. Only meaningful at
     * age 7; derived at render time by {@link #getActualState} (never persisted in meta).
     */
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    /**
     * Produces the ore block state to place next to a mature stem. Lazy so that
     * compat ores (e.g. IC2's meta-based resource block) can be resolved at runtime
     * instead of at block-registration time.
     */
    private final Supplier<IBlockState> fruitState;

    /** Tests whether a world block state counts as "this stem's ore" (meta-aware). */
    private final Predicate<IBlockState> fruitMatcher;

    /** Fixed vine tint (RGB 0xRRGGBB) matching the corresponding ore, like the PTO mod. */
    private final int tintColor;

    public BlockOreStem(String name, Block fruitBlock, int tintColor)
    {
        this(name, fruitBlock::getDefaultState, s -> s.getBlock() == fruitBlock, tintColor);
    }

    public BlockOreStem(String name, Supplier<IBlockState> fruitState,
                        Predicate<IBlockState> fruitMatcher, int tintColor)
    {
        super(Material.PLANTS);
        this.fruitState = fruitState;
        this.fruitMatcher = fruitMatcher;
        this.tintColor = tintColor;
        this.setUnlocalizedName(OreFarm.MODID + "." + name + "_stem");
        this.setRegistryName(new ResourceLocation(OreFarm.MODID, name + "_stem"));
        this.setCreativeTab(null);
        this.setTickRandomly(true);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)).withProperty(FACING, EnumFacing.UP));
    }

    public int getTintColor()
    {
        return this.tintColor;
    }

    // ----- blockstate / meta -----

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AGE, FACING);
    }

    /**
     * Derive the vine's bend direction: when fully grown (age 7) and this stem's ore block
     * sits on a horizontal side, point FACING that way so the blockstate renders the
     * {@code stem_fruit} model with a connector toward the ore. Immature stems stay UP.
     */
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        if (((Integer) state.getValue(AGE)).intValue() == 7)
        {
            if (this.fruitMatcher.test(worldIn.getBlockState(pos.west())))  return state.withProperty(FACING, EnumFacing.WEST);
            if (this.fruitMatcher.test(worldIn.getBlockState(pos.east())))  return state.withProperty(FACING, EnumFacing.EAST);
            if (this.fruitMatcher.test(worldIn.getBlockState(pos.north()))) return state.withProperty(FACING, EnumFacing.NORTH);
            if (this.fruitMatcher.test(worldIn.getBlockState(pos.south()))) return state.withProperty(FACING, EnumFacing.SOUTH);
        }
        return state.withProperty(FACING, EnumFacing.UP);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((Integer) state.getValue(AGE)).intValue();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        int age = meta;
        if (age < 0) age = 0;
        if (age > 7) age = 7;
        return this.getDefaultState().withProperty(AGE, Integer.valueOf(age));
    }

    // ----- planting support -----

    @Override
    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() == Blocks.FARMLAND;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Crop;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    // ----- growth & fruit generation -----

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);

        if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
        {
            int age = ((Integer) state.getValue(AGE)).intValue();
            float chance = getGrowthChance(worldIn, pos);

            if (rand.nextInt((int) (25.0F / chance) + 1) == 0)
            {
                if (age < 7)
                {
                    worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(age + 1)), 2);
                }
                else
                {
                    this.generateOre(worldIn, pos, rand);
                }
            }
        }
    }

    /**
     * Place the ore block next to a fully grown stem (port of the reference mod's
     * generateFruit branch). Refuses when an ore block is already adjacent on any side.
     */
    private void generateOre(World worldIn, BlockPos pos, Random rand)
    {
        if (this.fruitMatcher.test(worldIn.getBlockState(pos.west())))  return;
        if (this.fruitMatcher.test(worldIn.getBlockState(pos.east())))  return;
        if (this.fruitMatcher.test(worldIn.getBlockState(pos.north()))) return;
        if (this.fruitMatcher.test(worldIn.getBlockState(pos.south()))) return;

        BlockPos target;
        switch (rand.nextInt(4))
        {
            case 0: target = pos.west(); break;
            case 1: target = pos.east(); break;
            case 2: target = pos.north(); break;
            default: target = pos.south(); break;
        }

        BlockPos groundPos = target.down();
        IBlockState groundState = worldIn.getBlockState(groundPos);
        Block ground = groundState.getBlock();

        IBlockState fruit = this.fruitState.get();
        if (fruit != null
                && worldIn.isAirBlock(target)
                && (ground.canSustainPlant(groundState, worldIn, groundPos, EnumFacing.UP, this)
                || ground == Blocks.DIRT || ground == Blocks.GRASS))
        {
            worldIn.setBlockState(target, fruit, 3);
            // Re-query the stem's actual state so the vine renders its horizontal connector
            // toward the freshly placed ore block without waiting for the next chunk update.
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    /** Growth chance per random tick, ported from the reference mod (vanilla stem logic). */
    private float getGrowthChance(World worldIn, BlockPos pos)
    {
        float f = 1.0F;
        BlockPos down = pos.down();

        for (int dx = -1; dx <= 1; ++dx)
        {
            for (int dz = -1; dz <= 1; ++dz)
            {
                float f1 = 0.0F;
                BlockPos soilPos = down.add(dx, 0, dz);
                IBlockState soilState = worldIn.getBlockState(soilPos);
                Block soil = soilState.getBlock();

                if (soil.canSustainPlant(soilState, worldIn, soilPos, EnumFacing.UP, this))
                {
                    f1 = 1.0F;
                    if (soil.isFertile(worldIn, soilPos))
                    {
                        f1 = 3.0F;
                    }
                }

                if (dx != 0 || dz != 0)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        boolean flag = worldIn.getBlockState(pos.west()).getBlock() == this
                || worldIn.getBlockState(pos.east()).getBlock() == this;
        boolean flag1 = worldIn.getBlockState(pos.north()).getBlock() == this
                || worldIn.getBlockState(pos.south()).getBlock() == this;
        boolean flag2 = worldIn.getBlockState(pos.north().west()).getBlock() == this
                || worldIn.getBlockState(pos.north().east()).getBlock() == this
                || worldIn.getBlockState(pos.south().west()).getBlock() == this
                || worldIn.getBlockState(pos.south().east()).getBlock() == this;

        if (flag2 || (flag && flag1))
        {
            f /= 2.0F;
        }

        return f;
    }

    // ----- drops (stem drops nothing, like the reference mod) -----

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }

    // ----- IGrowable (bone meal) -----

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return ((Integer) state.getValue(AGE)).intValue() != 7;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        int age = ((Integer) state.getValue(AGE)).intValue() + 2 + rand.nextInt(4);
        if (age > 7) age = 7;
        worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(age)), 2);
    }
}
