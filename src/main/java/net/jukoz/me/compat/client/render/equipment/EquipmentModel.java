package net.jukoz.me.compat.client.render.equipment;

/**
 * 1.20.1 bridge for 1.21.x {@code net.minecraft.client.render.entity.equipment.EquipmentModel}.
 * Only the LayerType discriminator is used by ported call sites; the data-driven layer JSON
 * does not exist on 1.20.1, so {@link EquipmentRenderer} resolves textures directly from the
 * asset id using the vanilla 1.21.8 layout
 * {@code textures/entity/equipment/<layer_type>/<asset_id>.png}.
 */
public class EquipmentModel {

    public enum LayerType {
        HUMANOID("humanoid"),
        HUMANOID_LEGGINGS("humanoid_leggings"),
        HORSE_BODY("horse_body");

        private final String name;

        LayerType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
