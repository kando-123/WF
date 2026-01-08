package pl.polsl.wf.data.source.remote;

import java.util.regex.Pattern;

class RemoveHTML {
    private Pattern sup_num_regex;
    private Pattern sub_num_regex;
    private Pattern all_html_regex;
    private Pattern ref_regex;

    public RemoveHTML() {
        sup_num_regex = Pattern.compile("<\\s?sup\\s?>\\s?(\\d+)\\s?<\\s?/\\s?sup\\s?>");
        sub_num_regex = Pattern.compile("<\\s?sub\\s?>\\s?(\\d+)\\s?<\\s?/\\s?sub\\s?>");
        all_html_regex = Pattern.compile("<.+?>([\\w\\W]*?)<\\s/.+?>");
        ref_regex = Pattern.compile("<\\s*ref.*?>[\\w\\W]+?<\\s?/\\s?ref\\s?>");
    }

    public String process(String input) {
        String res;
        res = ref_regex.matcher(input).replaceAll("");
        res = sup_num_regex.matcher(res).replaceAll("_$1");
        res = sup_num_regex.matcher(res).replaceAll("^$1");
        res = all_html_regex.matcher(res).replaceAll("$1");
        return res;
    }

}
