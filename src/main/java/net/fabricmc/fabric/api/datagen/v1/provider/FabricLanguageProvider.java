package net.fabricmc.fabric.api.datagen.v1.provider;

import net.minecraft.data.PackOutput;
import net.minecraft.locale.Language;

import java.util.function.Consumer;
import net.minecraft.world.entity.EntityType;

/**
 * Forge compat shim for Fabric's {@code FabricLanguageProvider}. It mirrors Fabric's
 * {@code generateTranslations(TranslationBuilder)} entry point and writes the resulting map with
 * vanilla's {@link Language} machinery.
 */
public abstract class FabricLanguageProvider {
    private final PackOutput output;
    private final String languageCode;

    protected FabricLanguageProvider(net.fabricmc.fabric.api.datagen.v1.FabricDataOutput output,
                                     String languageCode) {
        this.output = output.getOutput();
        this.languageCode = languageCode;
    }

    protected FabricLanguageProvider(PackOutput output, String languageCode) {
        this.output = output;
        this.languageCode = languageCode;
    }

    /** Fabric's entry point; ported providers override this. */
    public abstract void generateTranslations(TranslationBuilder builder);

    /** Runs generation and hands the finished map back to the caller. */
    public final java.util.Map<String, String> build() {
        TranslationBuilder builder = new TranslationBuilder();
        generateTranslations(builder);
        return builder.entries;
    }

    public final PackOutput getPackOutput() {
        return output;
    }

    public final String getLanguageCode() {
        return languageCode;
    }

    /** Fabric's translation collector. */
    public static final class TranslationBuilder implements Consumer<TranslationBuilder> {
        final java.util.Map<String, String> entries = new java.util.LinkedHashMap<>();

        public TranslationBuilder add(String key, String value) {
            if (entries.putIfAbsent(key, value) != null) {
                throw new RuntimeException("Duplicate translation key " + key);
            }
            return this;
        }

        public TranslationBuilder add(net.minecraft.world.level.block.Block block, String value) {
            return add(block.getDescriptionId(), value);
        }

        public TranslationBuilder add(net.minecraft.world.item.Item item, String value) {
            return add(item.getDescriptionId(), value);
        }

        public TranslationBuilder add(net.minecraft.world.entity.EntityType<?> type, String value) {
            return add(type.getDescriptionId(), value);
        }

        public TranslationBuilder add(net.minecraft.world.effect.MobEffect effect, String value) {
            return add(effect.getDescriptionId(), value);
        }

        public TranslationBuilder add(net.minecraft.world.entity.ai.attributes.Attribute attribute,
                                      String value) {
            return add(attribute.getDescriptionId(), value);
        }

        @Override
        public void accept(TranslationBuilder builder) {
            this.entries.putAll(builder.entries);
        }
    }
}
