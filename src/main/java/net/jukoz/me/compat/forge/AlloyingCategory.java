package net.jukoz.me.compat.forge;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.compat.REICommonPluginME;
import net.jukoz.me.gui.forge.ForgeAlloyingScreen;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.gui.layouts.LayoutElement;

@OnlyIn(Dist.CLIENT)
public class AlloyingCategory implements DisplayCategory<AlloyingDisplay> {
    public static final ResourceLocation TEXTURE = MiddleEarth.of('/', "textures", "gui", "forge_rei.png");

    @Override
    public CategoryIdentifier<? extends AlloyingDisplay> getCategoryIdentifier() {
        return REICommonPluginME.FORGE_CATEGORY;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("screen." + MiddleEarth.MOD_ID + ".forge");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModDecorativeBlocks.FORGE.asItem().getDefaultInstance());
    }

    @Override
    public int getDisplayHeight() {
        return 83;
    }

    @Override
    public List<LayoutElement> setupDisplay(AlloyingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
        List<LayoutElement> widgets = new LinkedList<>();

        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 175, 82)));

        for(int x = 0; x < 4; x++) {
            Slot slot = Widgets.createSlot(new Point(startPoint.x + 41 + 18*x, startPoint.y + 16));
            if(display.getInputEntries().size() > x) slot.markOutput().entries(display.getInputEntries().get(x));
            widgets.add(slot);
        }

        widgets.add(Widgets.createTexturedWidget(TEXTURE,startPoint.x + 66, startPoint.y + 44, 218, 14, 20, 15));
        int storedLiquid = (int) (Math.min(1.0f, (float)display.amount / 576) * ForgeAlloyingScreen.LIQUID_HEIGHT);
        widgets.add(Widgets.createTexturedWidget(TEXTURE,startPoint.x + 106, startPoint.y + 75 - storedLiquid, 211, 76 - storedLiquid, 20, storedLiquid));

        widgets.add(Widgets.createLabel(new Point(startPoint.x + 77, startPoint.y + 5),
                Component.translatable("trim_material." + MiddleEarth.MOD_ID + "." + display.output)));

        return widgets;
    }
}
