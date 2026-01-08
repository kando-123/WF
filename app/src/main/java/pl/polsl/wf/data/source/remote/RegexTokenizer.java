package pl.polsl.wf.data.source.remote;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RegexTokenizer {
    Pattern pattern;

    public RegexTokenizer(String regex) {
        pattern = Pattern.compile(regex);
    }

    /**
     * splits `input` by the regex, making tokens that match and don't match
     * @param input     text to be split
     * @param tokens    output vector of the tokens
     * @param matched   output vector whether given token has the regex matched or not
     */
    public void tokenize(String input, List<String> tokens, List<Boolean> matched) {
        int start = 0;
        Matcher m = pattern.matcher(input);
        while (true) {
            if (m.find(start)) {
                tokens.add(input.substring(start, m.start()));
                matched.add(false);
                tokens.add(m.group());
                matched.add(true);
                start = m.end();
            } else {
                tokens.add(input.substring(start, input.length()));
                matched.add(false);
                break;
            }
        }
    }
}
