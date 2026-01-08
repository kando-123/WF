package pl.polsl.wf.data.source.remote;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RemoveComments {
    private Pattern pattern;

    public RemoveComments() {
        pattern = Pattern.compile("<!-- [\\w\\W]+?->");
    }

    public String process(String input) {
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }
}
