package pl.polsl.wf.data.model;

import java.util.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Data transfer object representing a single translation entry.
 */
public class TranslationEntryDto
{
    /**
     * Definition of the headword being translated, specifying what meaning the phrases correspond.
     */
    @Nullable
    private final String definition;

    /**
     * Phrases corresponding to the meaning of the headword.
     */
    private final List<TranslationEntryPhraseDto> phrases;

    public TranslationEntryDto(@Nullable String definition,
                               @Nonnull List<TranslationEntryPhraseDto> phrases)
    {
        this.definition = definition;
        this.phrases = new ArrayList<>(phrases);
    }

    public Optional<String> getDefinition()
    {
        return Optional.ofNullable(definition);
    }

    public List<TranslationEntryPhraseDto> getPhrases()
    {
        return new ArrayList<>(phrases);
    }
}
