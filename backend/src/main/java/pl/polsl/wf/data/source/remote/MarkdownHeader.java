package pl.polsl.wf.data.source.remote;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of ITranslationDataSource interface for online data source. It communicates
 * with Wiktionary and parses the responses.s
 */

class MarkdownHeader
{
    public final int level;
    public final int resStart;
    /// position AFTER the markdown ends
    public final int resEnd;
    public final String headword;

    private MarkdownHeader(String regex, int level, int startPos, String markdown)
    {
        this.level = level;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(markdown);

        if (!matcher.find(startPos))
        {
            resStart = -1;
            resEnd = -1;
            headword = "";
        }
        else
        {
            // if the first symbol was ^, the result starts at 0
            // else remove the additional character
            resStart = matcher.start() + matcher.group(1).length();
            // if the last symbol was $, remove nothing
            // else remove the additional character
            resEnd = matcher.end() - matcher.group(3).length();
            headword = matcher.group(2);
        }
    }
    //first boolean is a dummy, so that the constructors differ.
    //techincally the one with set number should be removed
    public MarkdownHeader(boolean lessOrEqual, int level, int startPos,  String markdown)
    {
        this("(^|[^=])={1,"+level+"} ?([^=]+?) ?={1,"+level+"}($|[^=])", level, startPos, markdown);
    }

    public MarkdownHeader(int level, int startPos, String headword, String markdown)
    {
        this("(^|[^=])={"+level+"} ?("+headword+") ?={"+level+"}($|[^=])", level, startPos, markdown);
    }
    public MarkdownHeader(int level, int startPos, String markdown)
    {
        this("(^|[^=])={"+level+"} ?([^=]+?) ?={"+level+"}($|[^=])", level, startPos, markdown);
    }
}
