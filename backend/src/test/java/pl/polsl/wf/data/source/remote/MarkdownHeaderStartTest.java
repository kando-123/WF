package pl.polsl.wf.data.source.remote;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import pl.polsl.wf.data.source.remote.MarkdownHeader;

@RunWith(Parameterized.class)
public class MarkdownHeaderStartTest {

    @Parameterized.Parameters(name = "{index}: level=\"{0}\", start=\"{1}\", resStart=\"{2}\", resEnd=\"{3}\", headword=\"{4}\",")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {4, 1, 19, 35, "Header 4"},
                {4, 0, 19, 35, "Header 4"},
                {3, 20, 36, 50, "Header 3"},
                {3, 0, 36, 50, "Header 3"},
                {2, 37, 51, 63, "Header 2"},
                {2, 0, 51, 63, "Header 2"},
        });
    }

    @Parameterized.Parameter(0)
    public int level;
    @Parameterized.Parameter(1)
    public int start;
    @Parameterized.Parameter(2)
    public int resStart;
    @Parameterized.Parameter(3)
    public int resEnd;
    @Parameterized.Parameter(4)
    public String headword;

    @Test
    public void getMarkdownHeader() {
        String markdown =
                "=====Header 5=====\n" +
                "====Header 4====\n" +
                "===Header 3===\n" +
                "==Header 2==";

        MarkdownHeader markdownChunk = new MarkdownHeader(level, start, markdown);
        assertEquals(resStart, markdownChunk.resStart);
        assertEquals(resEnd, markdownChunk.resEnd);
        assertEquals(headword, markdownChunk.headword);
    }
}
