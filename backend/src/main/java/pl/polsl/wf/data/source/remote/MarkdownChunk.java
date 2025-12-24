package pl.polsl.wf.data.source.remote;

import pl.polsl.wf.data.source.remote.MarkdownHeader;

class MarkdownChunk {
    public final int level;
    public final int resStart;
    /// position AFTER the markdown ends
    public final int resEnd;
    public final String headword;
    public final CharSequence contents;

    public MarkdownChunk(MarkdownHeader header, String markdown) {
        level = header.level;
        resStart = header.resStart;
        headword = header.headword;

        MarkdownHeader nextHeader = new MarkdownHeader(level, header.resEnd, markdown);
        if (nextHeader.resStart == -1) //end of input
        {
            resEnd = markdown.length();
        } else {
            resEnd = nextHeader.resEnd;
        }
        contents = markdown.subSequence(resStart, resEnd);
    }

}
