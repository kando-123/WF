package pl.polsl.wf.data.source.remote;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MarkdownHeader {
    
    public int getLevel() {
        return level;
    }

    public int getResStart() {
        return resStart;
    }

    public int getResEnd() {
        return resEnd;
    }

    public String getHeadword() {
        return headword;
    }

    private int level;
    private int resStart;
    private int resEnd;
    private String headword;
    private Matcher matcher;

    public boolean next() {
        boolean res = matcher.find();
        if (res) {
            resStart = matcher.start();
            resEnd = matcher.end();
            headword = matcher.group(3);
            level = matcher.group(2).length();
        }
        return res;
    }

    private MarkdownHeader(String regex, int start, CharSequence markdown) {
        matcher = Pattern.compile(regex).matcher(markdown);
        matcher.region(start, markdown.length());
    }

    public static MarkdownHeader anyHeader(int start, CharSequence markdown) {
        MarkdownHeader res = new MarkdownHeader(
                "(^|[^=])(==+) ?([^=]+?) ?==+($|\\n)",
                start, markdown
        );

        return res;
    }

    public static MarkdownHeader anyHeaderLessOrEqual(int level, int start, CharSequence markdown) {

        MarkdownHeader res = new MarkdownHeader(
                "(^|\\n)(={1," + level + "}) ?([^=]+?) ?={1," + level + "}($|\\n)",
                start, markdown
        );

        return res;
    }


    public static MarkdownHeader headerWithAnyKeyword(List<String> keywords, int start, CharSequence markdown) {
        MarkdownHeader res = new MarkdownHeader(
                "(^|[^=])(==+) ?(" + String.join("|", keywords) + ") ?==+($|\\n)",
                start, markdown
        );

        return res;
    }

}