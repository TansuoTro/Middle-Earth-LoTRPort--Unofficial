package net.fabricmc.fabric.api.itemgroup.v1;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Forge compat shim for Fabric's {@code FabricItemGroup}. It keeps Fabric's fluent builder API
 * (displayName / icon / entries) and produces a vanilla {@link CreativeModeTab} whose entry
 * callback runs on {@code DisplayItemsGenerator}, which is what 1.20.1 actually calls.
 */
public final class FabricItemGroup {
    private Component title;
    private Supplier<ItemStack> icon;
    private BiConsumer<CreativeModeTab.ItemDisplayParameters, EntryOutput> entries;
    private boolean showTitle = true;
    private boolean scrollBar = true;
    private boolean canScroll = true;
    private CreativeModeTab.Row row = CreativeModeTab.Row.TOP;
    private int column = 0;

    private FabricItemGroup() {}

    public static FabricItemGroup builder() {
        return new FabricItemGroup();
    }

    public FabricItemGroup displayName(Component name) {
        this.title = name;
        return this;
    }

    public FabricItemGroup icon(Supplier<ItemStack> icon) {
        this.icon = icon;
        return this;
    }

    /** Fabric's {@code entries((context, entries) -> ...)} overload. */
    public FabricItemGroup entries(BiConsumer<CreativeModeTab.ItemDisplayParameters, EntryOutput> callback) {
        this.entries = callback;
        return this;
    }

    public FabricItemGroup showTitle(boolean value) {
        this.showTitle = value;
        return this;
    }

    public FabricItemGroup scrollBar(boolean value) {
        this.scrollBar = value;
        return this;
    }

    public FabricItemGroup canScroll(boolean value) {
        this.canScroll = value;
        return this;
    }

    public FabricItemGroup row(CreativeModeTab.Row row) {
        this.row = row;
        return this;
    }

    public FabricItemGroup column(int column) {
        this.column = column;
        return this;
    }

    public CreativeModeTab build() {
        final BiConsumer<CreativeModeTab.ItemDisplayParameters, EntryOutput> callback = this.entries;
        final Supplier<ItemStack> iconSupplier = this.icon;
        final boolean showTitle = this.showTitle;
        final boolean scrollBar = this.scrollBar;
        final boolean canScroll = this.canScroll;
        final CreativeModeTab.Row row = this.row;
        final int column = this.column;
        final Component title = this.title == null ? Component.empty() : this.title;

        CreativeModeTab.Builder builder = new CreativeModeTab.Builder(row, column)
                .title(title)
                .icon(iconSupplier == null ? () -> ItemStack.EMPTY : iconSupplier)
                .displayItems((parameters, output) -> {
                    if (callback == null) {
                        return;
                    }
                    callback.accept(parameters, stack -> output.accept(stack));
                });
        if (!showTitle) {
            builder.hideTitle();
        }
        if (!scrollBar) {
            builder.noScrollBar();
        }
        return builder.build();
    }

    /** Fabric's {@code entries} callback target: it only needs {@code add}. */
    public interface EntryOutput {
        void add(ItemStack stack);
    }
}
