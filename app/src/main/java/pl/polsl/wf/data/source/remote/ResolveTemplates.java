package pl.polsl.wf.data.source.remote;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ResolveTemplates {

    Pattern lb_regex;
    Pattern latn_def_regex;
    Pattern cyrl_def_regex;
    Pattern gloss_regex;
    Pattern nongloss_regex;
    Pattern l_regex;
    Pattern tcl_regex;
    public ResolveTemplates()
    {
        lb_regex = Pattern.compile("\\{\\{lb\\|.+?\\|(.+?)\\}\\}");
        latn_def_regex = Pattern.compile("\\{\\{Latn-def\\|.+?\\}\\}");
        cyrl_def_regex = Pattern.compile("\\{\\{Cyrl-def\\|.+?\\}\\}");
        gloss_regex = Pattern.compile("\\{\\{(gloss|gl)\\|(.+?)\\}\\}");
        nongloss_regex = Pattern.compile("\\{\\{n-?g\\|(.+?)\\}\\}");
        l_regex =Pattern.compile("\\{\\{l\\|.+?\\|(.+?)\\}\\}");
        tcl_regex =Pattern.compile("\\{\\{tcl\\|.+?\\|(.+?)\\|.+?\\}\\}");
    }
    private String replaceAll(Pattern regex, String input)
    {
        String res = input;
        Matcher m;
        while(true)
        {
            m = regex.matcher(res);
            if (!m.find())
                break;
            res = m.replaceFirst("("+m.group(1).replace("|"," ")+")");
        }
        return  res;
    }
    public String process(String input) {
        String res = input;
        res = replaceAll(lb_regex, res);
        res = latn_def_regex.matcher(res).replaceAll("a letter of the latin alphabet*");
        res = cyrl_def_regex.matcher(res).replaceAll("a letter of the cyrylic alphabet*");
        res = gloss_regex.matcher(res).replaceAll("($2)");
        res = nongloss_regex.matcher(res).replaceAll("$1");
        res = l_regex.matcher(res).replaceAll("$1");
        res = tcl_regex.matcher(res).replaceAll("$1");
        return res;
    }
}
