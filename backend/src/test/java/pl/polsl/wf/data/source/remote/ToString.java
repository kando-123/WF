package pl.polsl.wf.data.source.remote;

import pl.polsl.wf.data.model.*;

import java.util.List;
import java.util.stream.Collectors;

import pl.polsl.wf.data.model.*;

import java.util.Iterator;
import java.util.List;

public final class ToString
{
    private ToString() {
        // utility class
    }

    /* =========================
       TranslationDto
       ========================= */

    public static String toString(TranslationDto dto)
    {
        if (dto == null) {
            return "TranslationDto{null}";
        }

        return "TranslationDto{" +
                "headword='" + dto.getHeadword() + '\'' +
                ", attributes=" + dto.getAttributes() +
                ", sourceLanguageCode='" + dto.getSourceLanguageCode() + '\'' +
                ", targetLanguageCode='" + dto.getTargetLanguageCode() + '\'' +
                ", entries=" + toStringEntries(dto.getEntries()) +
                '}';
    }

    /* =========================
       TranslationEntryDto
       ========================= */

    public static String toString(TranslationEntryDto entry)
    {
        if (entry == null) {
            return "TranslationEntryDto{null}";
        }

        return "TranslationEntryDto{" +
                "definition=" + entry.getDefinition().orElse("null") +
                ", phrases=" + toStringPhrases(entry.getPhrases()) +
                '}';
    }

    /* =========================
       TranslationEntryPhraseDto
       ========================= */

    public static String toString(TranslationEntryPhraseDto phrase)
    {
        if (phrase == null) {
            return "TranslationEntryPhraseDto{null}";
        }

        return "TranslationEntryPhraseDto{" +
                "text='" + phrase.getText() + '\'' +
                ", link=" + phrase.getLink().orElse("null") +
                '}';
    }

    /* =========================
       List<TranslationDto>
       ========================= */

    public static String toString(List<TranslationDto> translations)
    {
        if (translations == null) {
            return "List<TranslationDto>{null}";
        }

        return translations.stream()
                .map(ToString::toString)
                .collect(Collectors.joining(
                        ",\n",
                        "List<TranslationDto>[\n",
                        "\n]"
                ));
    }

    /* =========================
       Internal helpers
       ========================= */

    private static String toStringEntries(List<TranslationEntryDto> entries)
    {
        if (entries == null) {
            return "null";
        }

        return entries.stream()
                .map(ToString::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    private static String toStringPhrases(List<TranslationEntryPhraseDto> phrases)
    {
        if (phrases == null) {
            return "null";
        }

        return phrases.stream()
                .map(ToString::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
