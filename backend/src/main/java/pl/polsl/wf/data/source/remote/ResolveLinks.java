package pl.polsl.wf.data.source.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.polsl.wf.data.model.TranslationEntryPhraseDto;

class ResolveLinks {
    Pattern simple_links_regex;
    Pattern alter_links_regex;
    Pattern any_link_regex;

    public ResolveLinks() {
        simple_links_regex = Pattern.compile("\\[\\[([^\\|]+?)\\]\\]");
        alter_links_regex = Pattern.compile("\\[\\[([^\\|]+?)\\|([^\\|]+?)\\]\\]");
        any_link_regex = Pattern.compile("\\[\\[([\\w\\W]+?)\\]\\]");
    }

    private TranslationEntryPhraseDto processLink(String link) {
        TranslationEntryPhraseDto res;
        Matcher m = simple_links_regex.matcher(link);
        if (m.matches()) {
            res = new TranslationEntryPhraseDto(m.group(1), m.group(1));
        } else {
            m = alter_links_regex.matcher(link);
            if (m.matches()) {
                res = new TranslationEntryPhraseDto(m.group(2), m.group(1));
            } else {
                res = new TranslationEntryPhraseDto("");
            }
        }
        return res;
    }

    public List<TranslationEntryPhraseDto> process(String input) {
        List<TranslationEntryPhraseDto> res = new ArrayList<>();
        int start = 0;
        while (true) {
            int end = input.indexOf("[[", start);

            if (end == -1) {
                String substr = input.substring(start, input.length());
                if (!substr.isBlank())
                    res.add(new TranslationEntryPhraseDto(substr));
                break;
            } else {
                String substr = input.substring(start, end);
                if (!substr.isBlank())
                    res.add(new TranslationEntryPhraseDto(substr));
                Matcher m = any_link_regex.matcher(input);
                m.find(end);
                res.add(processLink(m.group()));
                start = m.end();
            }
        }

        return res;
    }
}
