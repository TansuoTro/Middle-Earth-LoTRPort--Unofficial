package net.jukoz.me.datageneration.content.models;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class SimpleArtefactModels {
    public static List<Artefact> artefacts = new ArrayList<>();

    public record Artefact(Item item, boolean isDualModel) {
    }
}
