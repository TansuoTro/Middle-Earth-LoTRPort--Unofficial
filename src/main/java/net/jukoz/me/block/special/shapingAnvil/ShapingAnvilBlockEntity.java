package net.jukoz.me.block.special.shapingAnvil;

import net.jukoz.me.compat.MEArmorTrim;
import net.jukoz.me.item.MEComponents;
import net.jukoz.me.item.MEDurability;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.ArmorTrimMaterial;
import net.minecraft.world.item.armortrim.ArmorTrimPattern;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.core.Holder;
import net.jukoz.me.compat.nbt.ReadView;
import net.jukoz.me.compat.nbt.WriteView;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.block.special.forge.MetalTypes;
import net.jukoz.me.enchantments.EnchantmentsME;
import net.jukoz.me.gui.shapinganvil.ShapingAnvilScreenHandler;
import net.jukoz.me.item.ResourceItemsME;
import net.jukoz.me.item.dataComponents.TemperatureDataComponent;
import net.jukoz.me.particles.ModParticleTypes;
import net.jukoz.me.recipe.AnvilShapingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.jukoz.me.compat.SingleStackRecipeInput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.RegistryWrapper;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import net.jukoz.me.compat.MEEnchants;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;

public class ShapingAnvilBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, WorldlyContainer {
    private static final String ID = "shaping_anvil";

    public int outputIndex = 0;
    public int maxOutputIndex = 0;

    public final NonNullList<ItemStack> inventory =
            NonNullList.ofSize(1, ItemStack.EMPTY);

    protected final ContainerData propertyDelegate;

    //TODO make work in creative somehow

    public ShapingAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TREATED_ANVIL, pos, state);

        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ShapingAnvilBlockEntity.this.outputIndex;
                    case 1 -> ShapingAnvilBlockEntity.this.maxOutputIndex;
                    default -> throw new IllegalStateException("Unexpected value: " + index);
                };
            }
            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ShapingAnvilBlockEntity.this.outputIndex = value;
                    case 1 -> ShapingAnvilBlockEntity.this.maxOutputIndex = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public ShapingAnvilBlockEntity(BlockEntityType type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ShapingAnvilBlockEntity.this.outputIndex;
                    case 1 -> ShapingAnvilBlockEntity.this.maxOutputIndex;
                    default -> throw new IllegalStateException("Unexpected value: " + index);
                };
            }
            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ShapingAnvilBlockEntity.this.outputIndex = value;
                    case 1 -> ShapingAnvilBlockEntity.this.maxOutputIndex = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public static void updateIndex(int index, Vec3 coords, ServerPlayer player){
        BlockPos pos = new BlockPos((int) coords.getX(), (int) coords.getY(), (int) coords.getZ());
        BlockEntity shapingAnvilBlockEntity = player.getWorld().getBlockEntity(pos);

        if(shapingAnvilBlockEntity instanceof ShapingAnvilBlockEntity entity){
            if(index >= 0) {
                entity.outputIndex = index;
                entity.update();
            }
        }
    }

    public ItemStack getRenderStack(ShapingAnvilBlockEntity entity) {
        return entity.getStack(0);
    }

    public void bonk(ShapingAnvilBlockEntity entity, ServerLevel world, ItemStack tool){
        ItemStack input = entity.getStack(0);

        RecipeManager serverRecipeManager = (RecipeManager)entity.getWorld().getRecipeManager();
        List<AnvilShapingRecipe> match = serverRecipeManager.getAllMatches(AnvilShapingRecipe.Type.INSTANCE, new SingleStackRecipeInput(input), entity.getWorld());

        if (!match.isEmpty() && hasShapingRecipe(entity)){
            int temperature = 0;
            if(MEComponents.get(input, MEComponents.TEMPERATURE_DATA) != null) {
                temperature = MEComponents.get(input, MEComponents.TEMPERATURE_DATA).temperature();
            }
            entity.getWorld().playSound(null, pos, SoundEvents.BLOCK_ANVIL_LAND, SoundSource.BLOCKS, 1.5f, 1.0f - (float) temperature / 1000);

            int minRandProgress = 7;
            int maxRandProgress = 14;
            if(temperature <= 0) {
                minRandProgress = maxRandProgress = 1;
            } else {
                Holder<Enchantment> enchantmentRegistryEntry = world.getRegistryManager().get(Registries.ENCHANTMENT).getEntry(EnchantmentsME.AULE_BLESSING).orElseThrow();
                boolean hasEnchant = MEEnchants.has(tool, enchantmentRegistryEntry);
                if (hasEnchant) {
                    int level = EnchantmentHelper.getLevel(enchantmentRegistryEntry.value(), tool);
                    minRandProgress = 7 + level;
                    maxRandProgress = 14 + (2 * level);
                }
            }

            if (input.getMaxDamage() == 0 && input.getDamage() == 0){
                MEDurability.set(input, match.get(entity.outputIndex).getAmount());
                input.setDamage(match.get(entity.outputIndex).getAmount() - (int) (Math.random() * (maxRandProgress - minRandProgress) + minRandProgress));
            } else{
                input.setDamage(input.getDamage() - (int) (Math.random() * (maxRandProgress - minRandProgress) + minRandProgress));
            }

            Level serverWorld = this.getWorld();
            if (serverWorld instanceof ServerLevel) {
                ((ServerLevel)serverWorld).spawnParticles(ModParticleTypes.ANVIL_SPARK_PARTICLE, pos.getX()+ 0.5f, pos.getY() + 1.0f, pos.getZ() + 0.5f, Math.max(temperature / 10, 3), 0.0, 0.0, 0.0, 0.0);
            }

            int minRandTemperature = 10;
            int maxRandTemperature = 18;
            int value = (int) (Math.random() * (maxRandTemperature - minRandTemperature) + minRandTemperature);

            if ((MEComponents.get(input, MEComponents.TEMPERATURE_DATA).temperature() - value) <= 0){
                MEComponents.remove(input, MEComponents.TEMPERATURE_DATA);
            } else {
                MEComponents.set(input, MEComponents.TEMPERATURE_DATA, new TemperatureDataComponent(MEComponents.get(input, MEComponents.TEMPERATURE_DATA).temperature() - value));
            }
            HolderLookup.Impl<TrimMaterial>  armorTrimMaterialRegistry = entity.getWorld().getRegistryManager().getWrapperOrThrow(Registries.TRIM_MATERIAL);
            HolderLookup.Impl<TrimPattern>  armorTrimPatternRegistry = entity.getWorld().getRegistryManager().getWrapperOrThrow(Registries.TRIM_PATTERN);

            if (input.getDamage() == 0){
                int xp = 2;
                if(input.getItem() == ResourceItemsME.ROD || input.getItem() == ResourceItemsME.ARMOR_PLATE) xp = 4;
                if(input.getItem() == ResourceItemsME.LARGE_ROD) xp = 7;
                ExperienceOrb.spawn(world, entity.getPos().toCenterPos().add(0, 1, 0), xp);

                ItemStack output = match.get(entity.outputIndex).craft(new SingleStackRecipeInput(input), entity.world.getRegistryManager());

                if(MEArmorTrim.get(input) != null){
                    MEArmorTrim.set(output, MEArmorTrim.get(input));
                } else{
                    MetalTypes metal = MetalTypes.EMPTY;
                    if(input.isIn(TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "ingot_shaping")))) {
                        metal = MetalTypes.getMetalByIngot(input.getItem());
                    }else if(input.isIn(TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "nugget_shaping")))) {
                        metal = MetalTypes.getMetalByNugget(input.getItem());
                    }
                    if (metal.isVanilla()){
                        MEArmorTrim.set(output, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(ResourceKey.of(Registries.TRIM_MATERIAL, new ResourceLocation(metal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(ResourceKey.of(Registries.TRIM_PATTERN, new ResourceLocation(MiddleEarth.MOD_ID,"smithing_part")))));
                    } else {
                        MEArmorTrim.set(output, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(ResourceKey.of(Registries.TRIM_MATERIAL, new ResourceLocation(MiddleEarth.MOD_ID, metal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(ResourceKey.of(Registries.TRIM_PATTERN, new ResourceLocation(MiddleEarth.MOD_ID, "smithing_part")))));
                    }
                }
                if (MEComponents.get(input, MEComponents.TEMPERATURE_DATA) != null){
                    MEComponents.set(output, MEComponents.TEMPERATURE_DATA, new TemperatureDataComponent(MEComponents.get(input, MEComponents.TEMPERATURE_DATA).temperature()));
                }
                entity.getWorld().playSound(null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                entity.setStack(0, output);
                entity.update();
            }
        } else {
            Level serverWorld = this.getWorld();
            if (serverWorld instanceof ServerLevel) {
                ((ServerLevel)serverWorld).spawnParticles(ModParticleTypes.ANVIL_SPARK_PARTICLE, pos.getX()+ 0.5f, pos.getY() + 1.0f, pos.getZ() + 0.5f, 2, 0.0, 0.0, 0.0, 0.0);
            }
            entity.getWorld().playSound(null, pos, SoundEvents.BLOCK_ANVIL_LAND, SoundSource.BLOCKS, 1.5f, 1.0f);
        }
    }

    public static void tick(Level world, BlockPos blockPos, BlockState blockState, ShapingAnvilBlockEntity entity) {
        ItemStack input = entity.getStack(0);
        if (!input.isEmpty()){
            RecipeManager serverRecipeManager = (RecipeManager)entity.getWorld().getRecipeManager();
            List<AnvilShapingRecipe> match = serverRecipeManager.getAllMatches(AnvilShapingRecipe.Type.INSTANCE, new SingleStackRecipeInput(input), entity.getWorld());
            if(!match.isEmpty()){
                entity.maxOutputIndex = match.size() - 1;
                if (entity.outputIndex > entity.maxOutputIndex){
                    entity.outputIndex = entity.maxOutputIndex;
                }
                entity.update();
            } else {
                entity.maxOutputIndex = 0;
                entity.update();
            }
        } else {
            entity.maxOutputIndex = 0;
            entity.update();
        }
    }

    private static boolean hasShapingRecipe(ShapingAnvilBlockEntity entity) {
        SimpleContainer inventory1 = new SimpleContainer(entity.size());
        ItemStack input;

        inventory1.setStack(0, entity.getStack(0));
        input = entity.getStack(0);

        if(input.isEmpty()) return false;

        SingleStackRecipeInput inputStack = new SingleStackRecipeInput(input);
        RecipeManager serverRecipeManager = (RecipeManager)entity.getWorld().getRecipeManager();
        List<AnvilShapingRecipe> match = serverRecipeManager.getAllMatches(AnvilShapingRecipe.Type.INSTANCE, inputStack, entity.getWorld());

        return match.get(0).getOutput() != null;
    }

    @Override
    protected void writeNbt(CompoundTag nbt) {
        super.writeNbt(nbt);
        WriteView view = new WriteView(nbt);
        ContainerHelper.writeNbt(view.nbt(), this.inventory, true);
        view.putInt("current-index", this.outputIndex);
        view.putInt("current-max-index", this.maxOutputIndex);
    }

    @Override
    public void readNbt(CompoundTag nbt) {
        super.readNbt(nbt);
        ReadView view = new ReadView(nbt);
        this.inventory.clear();
        ContainerHelper.readNbt(view.nbt(), this.inventory);
        this.outputIndex = view.getInt("current-index", 0);
        this.maxOutputIndex = view.getInt("current-max-index", 0);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new ShapingAnvilScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> toUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] slots = new int[inventory.size()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }
        return slots;
    }

    public void update() {
        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return getStack(0).isEmpty();
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack itemStack = getStack(i);
            if (!itemStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = ContainerHelper.splitStack(inventory, slot, amount);
        if (!result.isEmpty()) {
            update();
        }
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return ContainerHelper.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        update();
    }

    @Override
    public boolean canPlayerUse(Player player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public void writeScreenOpeningData(net.minecraft.server.level.ServerPlayer player, net.minecraft.network.FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }
}
