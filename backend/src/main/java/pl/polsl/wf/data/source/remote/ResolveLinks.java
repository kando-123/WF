package pl.polsl.wf.data.source.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.polsl.wf.data.model.TranslationEntryPhraseDto;

class ResolveLinks {
    Pattern simple_links_regex;
    Pattern alter_links_regex;
    RegexTokenizer regexTokenizer;

    public ResolveLinks() {
        simple_links_regex = Pattern.compile("\\[\\[([^\\|]+?)\\]\\]");
        alter_links_regex = Pattern.compile("\\[\\[([^\\|]+?)\\|([^\\|]+?)\\]\\]");
        regexTokenizer = new RegexTokenizer("\\[\\[([\\w\\W]+?)\\]\\]");
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

        List<String> tokens = new ArrayList<>();
        List<Boolean> matches = new ArrayList<>();

        regexTokenizer.tokenize(input, tokens, matches);
        for (int i=0; i<tokens.size(); i++)
        {
            if (matches.get(i))
                res.add(processLink(tokens.get(i)));
            else if (!tokens.get(i).isBlank())
                res.add(new TranslationEntryPhraseDto(tokens.get(i)));
        }
        return res;
    }
}
