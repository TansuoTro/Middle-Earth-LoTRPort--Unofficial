package net.jukoz.me.datageneration;

import com.google.gson.JsonElement;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Port shim (datagen only): the 1.21 tree could register the same model twice without consequence,
 * but 1.20.1's {@code ModelProvider} writes into a {@code Map} and throws
 * {@code IllegalStateException: Duplicate model definition for ...} on the second write. That single
 * exception used to abort the whole provider, leaving thousands of items without item models.
 *
 * <p>The collector fields on {@code ItemModelGenerator}/{@code BlockStateModelGenerator} are
 * {@code public final}, so they can only be swapped through {@code sun.misc.Unsafe}. Everything is
 * best-effort: if the swap fails the provider behaves exactly as before.
 */
public final class DedupingModelCollector {
    private DedupingModelCollector() {
    }

    public static void install(Object generator) {
        if (generator == null) {
            return;
        }
        dedupeBiConsumer(generator, "writer");
        dedupeBiConsumer(generator, "modelCollector");
        dedupeBlockStateCollector(generator);
    }

    private static void dedupeBiConsumer(Object generator, String fieldName) {
        try {
            Field field = generator.getClass().getField(fieldName);
            Object original = field.get(generator);
            if (!(original instanceof BiConsumer)) {
                return;
            }
        Set<Identifier> seen = new HashSet<>();
        BiConsumer<Identifier, Supplier<JsonElement>> wrapper = (id, supplier) -> {
            // Remember what got uploaded so ModelProvider's item fallback can tell a real block
            // model from one that only exists as sub-models.
            net.jukoz.me.datageneration.ModelProvider.UPLOADED_BLOCK_MODELS.add(id);
            if (seen.add(id)) {
                    //noinspection unchecked
                    ((BiConsumer<Identifier, Supplier<JsonElement>>) original).accept(id, supplier);
                }
            };
            setFinalField(generator, field, wrapper);
        } catch (Throwable ignored) {
            // keep the vanilla behaviour
        }
    }

    private static void dedupeBlockStateCollector(Object generator) {
        try {
            Field field = generator.getClass().getField("blockStateCollector");
            Object original = field.get(generator);
            if (!(original instanceof java.util.function.Consumer)) {
                return;
            }
            Set<net.minecraft.block.Block> seen = new HashSet<>();
            java.util.function.Consumer<net.minecraft.data.client.BlockStateSupplier> wrapper = supplier -> {
                if (seen.add(supplier.getBlock())) {
                    //noinspection unchecked
                    ((java.util.function.Consumer<net.minecraft.data.client.BlockStateSupplier>) original).accept(supplier);
                }
            };
            setFinalField(generator, field, wrapper);
        } catch (Throwable ignored) {
            // keep the vanilla behaviour
        }
    }

    private static void setFinalField(Object target, Field field, Object value) throws Exception {
        Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
        Field theUnsafe = unsafeClass.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Object unsafe = theUnsafe.get(null);
        java.lang.reflect.Method objectFieldOffset = unsafeClass.getMethod("objectFieldOffset", Field.class);
        java.lang.reflect.Method putObject = unsafeClass.getMethod("putObject", Object.class, long.class, Object.class);
        long offset = (Long) objectFieldOffset.invoke(unsafe, field);
        putObject.invoke(unsafe, target, offset, value);
    }
}
