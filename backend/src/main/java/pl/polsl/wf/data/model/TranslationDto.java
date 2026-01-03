package pl.polsl.wf.data.model;

import java.util.*;

/**
 * Raw data from the Wiktionary API or from the local files before mapping to the domain model.
 */
public class TranslationDto
{
    private final String headword;
    private final List<String> attributes;
    private final String sourceLanguageCode;
    private final String targetLanguageCode;
    private final List<TranslationEntryDto> entries;

    private static final List<String> allAttributes = List.of(
            "Number", "Phonogram", "Gerund", "Adverb", "Determiner", "Compound part",
            "Suffix", "Ideophone", "Prepositional phrase", "Affixations", "Preposition",
            "Classifier", "Relative", "Punctuation mark", "Conjunction", "Article",
            "Letter", "Numeral", "Affix", "Noun", "Verb", "Pronoun", "Contraction", "Inflection",
            "Phrase", "Proper noun", "Postposition", "Adjective", "Prefix", "Syllable", "Particle",
            "Participle", "Stem set", "Counter", "Adnominal", "Symbol", "Interjection"
    );

    public TranslationDto(String headword,
                          List<String> attributes,
                          String sourceLanguageCode,
                          String targetLanguageCode,
                          List<TranslationEntryDto> entries)
    {
        this.headword = headword;
        this.attributes = new ArrayList<>(attributes);
        this.sourceLanguageCode = sourceLanguageCode;
        this.targetLanguageCode = targetLanguageCode;
        this.entries = new ArrayList<>(entries);
    }

    public String getHeadword()
    {
        return headword;
    }

    public List<String> getAttributes()
    {
        return new ArrayList<>(attributes);
    }

    public String getSourceLanguageCode()
    {
        return sourceLanguageCode;
    }

    public String getTargetLanguageCode()
    {
        return targetLanguageCode;
    }

    public List<TranslationEntryDto> getEntries()
    {
        return new ArrayList<>(entries);
    }
    public static List<String> getAllAttributes(){return allAttributes;}
}
