package net.fabricmc.fabric.api.datagen.v1.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

/**
 * Forge compat shim for Fabric's {@code FabricTagProvider}. Fabric's variant adds the
 * {@code configure(HolderLookup.Provider)} entry point and a {@code tag(...)} appender that
 * accepts plain block/item instances; both are mapped onto vanilla's {@link TagsProvider}.
 */
public abstract class FabricTagProvider<T> extends TagsProvider {
    private final ResourceKey<? extends Registry<T>> registryKey;

    protected FabricTagProvider(PackOutput output, ResourceKey<? extends Registry<T>> registryKey,
                               CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registryKey, registries);
        this.registryKey = registryKey;
    }

    /** Fabric's entry point; ported providers override this. */
    protected void configure(HolderLookup.Provider registries) {
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        configure(registries);
    }

    /** Bridges the plain-object {@code add} calls the port makes onto the vanilla appender. */
    public TagAppender<T> tag(TagKey<T> key) {
        return new TagAppender<>(super.tag(key));
    }

    public static final class TagAppender<T> {
        private final net.minecraft.data.tags.TagsProvider.TagAppender<T> delegate;

        TagAppender(net.minecraft.data.tags.TagsProvider.TagAppender<T> delegate) {
            this.delegate = delegate;
        }

        public TagAppender<T> add(T entry) {
            delegate.add(entry);
            return this;
        }

        public TagAppender<T> add(T... entries) {
            for (T entry : entries) {
                delegate.add(entry);
            }
            return this;
        }

        public TagAppender<T> add(ResourceKey<T> key) {
            delegate.add(key);
            return this;
        }

        public TagAppender<T> addOptional(ResourceLocation id) {
            delegate.addOptional(id);
            return this;
        }

        public TagAppender<T> addTag(TagKey<T> tag) {
            delegate.addTag(tag);
            return this;
        }

        public TagAppender<T> addOptionalTag(ResourceLocation id) {
            delegate.addOptionalTag(id);
            return this;
        }

        public TagAppender<T> replace() {
            delegate.replace(true);
            return this;
        }
    }

    /** Block-tag flavour of the shim. */
    public static abstract class BlockTagProvider extends FabricTagProvider<Block> {
        public BlockTagProvider(net.fabricmc.fabric.api.datagen.v1.FabricDataOutput output,
                                CompletableFuture<HolderLookup.Provider> registries) {
            super(output.getOutput(), Registries.BLOCK, registries);
        }

        public BlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, Registries.BLOCK, registries);
        }
    }

    /** Item-tag flavour of the shim. */
    public static abstract class ItemTagProvider extends FabricTagProvider<Item> {
        public ItemTagProvider(net.fabricmc.fabric.api.datagen.v1.FabricDataOutput output,
                               CompletableFuture<HolderLookup.Provider> registries) {
            super(output.getOutput(), Registries.ITEM, registries);
        }

        public ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, Registries.ITEM, registries);
        }
    }
}
