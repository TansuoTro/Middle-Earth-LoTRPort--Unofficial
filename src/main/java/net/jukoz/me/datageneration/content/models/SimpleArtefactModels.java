package net.jukoz.me.datageneration.content.models;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class SimpleArtefactModels {

    public record Artefact(Item artefact, Boolean dualModel) {}


    public static List<Artefact> artefacts = new ArrayList<>(){

    };

}
