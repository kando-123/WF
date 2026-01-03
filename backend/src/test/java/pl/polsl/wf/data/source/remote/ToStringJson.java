package pl.polsl.wf.data.source.remote;

import java.util.Iterator;
import java.util.List;

import pl.polsl.wf.data.model.TranslationDto;
import pl.polsl.wf.data.model.TranslationEntryDto;
import pl.polsl.wf.data.model.TranslationEntryPhraseDto;

public final class ToStringJson
{
    private static final String INDENT = "  ";

    private ToStringJson() {}

    /* =========================
       Public API
       ========================= */

    public static String toJson(TranslationDto dto)
    {
        StringBuilder sb = new StringBuilder();
        appendTranslationDto(sb, dto, 0);
        return sb.toString();
    }

    public static String toJson(List<TranslationDto> list)
    {
        StringBuilder sb = new StringBuilder();
        indent(sb, 0).append("[\n");

        for (int i = 0; i < list.size(); i++) {
            appendTranslationDto(sb, list.get(i), 1);
            if (i < list.size() - 1) sb.append(",");
            sb.append("\n");
        }

        indent(sb, 0).append("]");
        return sb.toString();
    }

    /* =========================
       TranslationDto
       ========================= */

    private static void appendTranslationDto(StringBuilder sb, TranslationDto dto, int level)
    {
        indent(sb, level).append("{\n");

        field(sb, level + 1, "headword", dto.getHeadword(), true);
        field(sb, level + 1, "attributes", dto.getAttributes(), true);
        field(sb, level + 1, "sourceLanguageCode", dto.getSourceLanguageCode(), true);
        field(sb, level + 1, "targetLanguageCode", dto.getTargetLanguageCode(), true);

        indent(sb, level + 1).append("\"entries\": ");
        appendEntries(sb, dto.getEntries(), level + 1);
        sb.append("\n");

        indent(sb, level).append("}");
    }

    /* =========================
       TranslationEntryDto
       ========================= */

    private static void appendEntry(StringBuilder sb, TranslationEntryDto entry, int level)
    {
        indent(sb, level).append("{\n");

        indent(sb, level + 1)
                .append("\"definition\": ")
                .append(entry.getDefinition()
                        .map(ToStringJson::quote)
                        .orElse("null"))
                .append(",\n");

        indent(sb, level + 1).append("\"phrases\": ");
        appendPhrases(sb, entry.getPhrases(), level + 1);
        sb.append("\n");

        indent(sb, level).append("}");
    }

    /* =========================
       TranslationEntryPhraseDto
       ========================= */

    private static void appendPhrase(StringBuilder sb, TranslationEntryPhraseDto phrase, int level)
    {
        indent(sb, level).append("{\n");

        field(sb, level + 1, "text", phrase.getText(), true);

        indent(sb, level + 1)
                .append("\"link\": ")
                .append(phrase.getLink()
                        .map(ToStringJson::quote)
                        .orElse("null"))
                .append("\n");

        indent(sb, level).append("}");
    }

    /* =========================
       Collections
       ========================= */

    private static void appendEntries(StringBuilder sb, List<TranslationEntryDto> entries, int level)
    {
        sb.append("[\n");

        for (int i = 0; i < entries.size(); i++) {
            appendEntry(sb, entries.get(i), level + 1);
            if (i < entries.size() - 1) sb.append(",");
            sb.append("\n");
        }

        indent(sb, level).append("]");
    }

    private static void appendPhrases(StringBuilder sb, List<TranslationEntryPhraseDto> phrases, int level)
    {
        sb.append("[\n");

        for (int i = 0; i < phrases.size(); i++) {
            appendPhrase(sb, phrases.get(i), level + 1);
            if (i < phrases.size() - 1) sb.append(",");
            sb.append("\n");
        }

        indent(sb, level).append("]");
    }

    /* =========================
       Helpers
       ========================= */

    private static void field(StringBuilder sb, int level, String name, String value, boolean comma)
    {
        indent(sb, level)
                .append("\"").append(name).append("\": ")
                .append(quote(value));
        if (comma) sb.append(",");
        sb.append("\n");
    }

    private static void field(StringBuilder sb, int level, String name, List<String> values, boolean comma)
    {
        indent(sb, level).append("\"").append(name).append("\": [");

        Iterator<String> it = values.iterator();
        while (it.hasNext()) {
            sb.append(quote(it.next()));
            if (it.hasNext()) sb.append(", ");
        }

        sb.append("]");
        if (comma) sb.append(",");
        sb.append("\n");
    }

    private static String quote(String value)
    {
        return value == null
                ? "null"
                : "\"" + value.replace("\"", "\\\"") + "\"";
    }

    private static StringBuilder indent(StringBuilder sb, int level)
    {
        sb.append(INDENT.repeat(level));
        return sb;
    }
}
