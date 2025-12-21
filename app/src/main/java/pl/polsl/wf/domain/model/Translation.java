package pl.polsl.wf.domain.model;

import java.util.ArrayList;
import java.util.List;

public record Translation(String headword,
                         List<String> attributes,
                         String sourceLanguageCode,
                         String targetLanguageCode,
                         List<TranslationEntry> entries)
{
    public Translation(String headword,
                       List<String> attributes,
                       String sourceLanguageCode,
                       String targetLanguageCode,
                       List<TranslationEntry> entries)
    {
        this.headword = headword;
        this.attributes = new ArrayList<>(attributes);
        this.sourceLanguageCode = sourceLanguageCode;
        this.targetLanguageCode = targetLanguageCode;
        this.entries = new ArrayList<>(entries);
    }

    public List<String> attributes()
    {
        return new ArrayList<>(attributes);
    }

    public List<TranslationEntry> entries()
    {
        return new ArrayList<>(entries);
    }
}
