package net.jukoz.me.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.jukoz.me.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.minecraft.commands.CommandSourceStack;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AllHelmetAttachmentsSuggestionProvider implements SuggestionProvider<CommandSourceStack> {

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        try {
            List<String> candidates = getAllHelmetAttachments();
            return SuggestionUtil.getCorrespondingNames(candidates, builder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getAllHelmetAttachments() {
        List<String> candidates = new ArrayList<>();
        for(HelmetAttachmentsME helmetAttachment : HelmetAttachmentsME.values())
            candidates.add(helmetAttachment.getName());
        return candidates;
    }
}
