package pl.polsl.wf.data.source.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.polsl.wf.data.source.remote.MarkdownHeader;

class MarkdownChunk {
    public final int level;
    public final int resStart;
    /// position AFTER the markdown ends
    public final int resEnd;
    public final String headword;
    public final CharSequence contents;

    public MarkdownChunk(MarkdownHeader header, CharSequence markdown) {
        level = header.level;
        resStart = header.resEnd;
        headword = header.headword;

        MarkdownHeader nextHeader = new MarkdownHeader(true, level, header.resEnd, markdown);
        if (nextHeader.resStart == -1) //end of input
        {
            resEnd = markdown.length();
        } else {
            resEnd = nextHeader.resStart;
        }
        contents = markdown.subSequence(resStart, resEnd);
    }

    public List<String> getTranslations()
    {
        Pattern pattern = Pattern.compile("^\\s*#+\\s+(.+)");
        List<String> res = new ArrayList<>();
        for (String line: contents.toString().split("\n"))
        {
            Matcher m = pattern.matcher(line);
            if (m.matches())
            {
                res.add(m.group(1));
            }
        }
        return  res;
    }
}
