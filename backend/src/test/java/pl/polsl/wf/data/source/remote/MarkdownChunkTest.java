package pl.polsl.wf.data.source.remote;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import pl.polsl.wf.data.source.remote.MarkdownHeader;

@RunWith(Parameterized.class)
public class MarkdownChunkTest {

    @Parameterized.Parameters(name = "{index}: level=\"{0}\", resStart=\"{1}\", resEnd=\"{2}\", headword=\"{3}\",")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {2, 0, 12, "Header 2"},
                {3, 13, 27, "Header 3"},
                {4, 28, 44, "Header 4"},
                {5, 45, 63, "Header 5"},
        });
    }

    @Parameterized.Parameter(0)
    public int level;
    @Parameterized.Parameter(1)
    public int resStart;
    @Parameterized.Parameter(2)
    public int resEnd;
    @Parameterized.Parameter(3)
    public String headword;

    @Test
    public void getMarkdownHeader() {
        String markdown = "==Header 2==\n" +
                "===Header 3===\n" +
                "====Header 4====\n" +
                "=====Header 5=====\n";
        MarkdownHeader markdownChunk = new MarkdownHeader(level, 0, markdown);
        assertEquals(resStart, markdownChunk.resStart);
        assertEquals(resEnd, markdownChunk.resEnd);
        assertEquals(headword, markdownChunk.headword);
    }
}
