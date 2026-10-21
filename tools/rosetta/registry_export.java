// Execute through tools/bridge.py on the server thread after world startup.
com.google.gson.JsonObject out = new com.google.gson.JsonObject();
com.google.gson.JsonObject blocks = new com.google.gson.JsonObject();
for (net.minecraft.block.Block block : net.minecraft.registry.Registries.BLOCK) {
    com.google.gson.JsonObject properties = new com.google.gson.JsonObject();
    for (net.minecraft.state.property.Property<?> property : block.getStateManager().getProperties()) {
        com.google.gson.JsonArray values = new com.google.gson.JsonArray();
        for (Object value : property.getValues()) values.add(value.toString());
        properties.add(property.getName(), values);
    }
    blocks.add(net.minecraft.registry.Registries.BLOCK.getId(block).toString(), properties);
}
out.add("blocks", blocks);
String[] names = {"items", "entities", "block_entities"};
net.minecraft.registry.Registry<?>[] registries = {
    net.minecraft.registry.Registries.ITEM,
    net.minecraft.registry.Registries.ENTITY_TYPE,
    net.minecraft.registry.Registries.BLOCK_ENTITY_TYPE
};
for (int i = 0; i < registries.length; i++) {
    com.google.gson.JsonArray ids = new com.google.gson.JsonArray();
    for (net.minecraft.util.Identifier id : registries[i].getIds()) ids.add(id.toString());
    out.add(names[i], ids);
}
com.google.gson.JsonObject banners = new com.google.gson.JsonObject();
for (net.minecraft.block.entity.BannerPattern pattern : net.minecraft.registry.Registries.BANNER_PATTERN) {
    banners.addProperty(net.minecraft.registry.Registries.BANNER_PATTERN.getId(pattern).toString(), pattern.getId());
}
out.add("banner_patterns", banners);
java.nio.file.Path path = java.nio.file.Paths.get("../build/structure-registry.json");
java.nio.file.Files.writeString(path, new com.google.gson.GsonBuilder().setPrettyPrinting().create().toJson(out));
return "Exported " + blocks.size() + " blocks and item/entity registries to " + path.toAbsolutePath();
