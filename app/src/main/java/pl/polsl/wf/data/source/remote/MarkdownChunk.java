package pl.polsl.wf.data.source.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.polsl.wf.data.source.remote.MarkdownHeader;

class MarkdownChunk {
    public final int resStart;
    /// position AFTER the markdown ends
    public final int resEnd;
    public final String headword;
    public final CharSequence contents;

    public MarkdownChunk(MarkdownHeader header, CharSequence markdown) {
        resStart = header.getResEnd();
        headword = header.getHeadword();

        MarkdownHeader nextHeader =
                MarkdownHeader.anyHeaderLessOrEqual(header.getLevel(), header.getResEnd(), markdown);
        if (nextHeader.next()) //there's another header
        {
            resEnd = nextHeader.getResStart();
        } else {
            resEnd = markdown.length();
        }
        contents = markdown.subSequence(resStart, resEnd);
    }

}

class LeafMarkdownChunk
{
    public final int resStart;
    /// position AFTER the markdown ends
    public final int resEnd;
    public final String headword;
    public final CharSequence contents;


    public LeafMarkdownChunk(MarkdownHeader header, CharSequence markdown) {
        resStart = header.getResEnd();
        headword = header.getHeadword();

        MarkdownHeader nextHeader = MarkdownHeader.anyHeader(header.getResEnd(), markdown);
        if (nextHeader.next()) {
            resEnd = nextHeader.getResStart();
        } else {//end of input
            resEnd = markdown.length();
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