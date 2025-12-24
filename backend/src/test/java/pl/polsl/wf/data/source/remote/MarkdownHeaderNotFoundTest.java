package pl.polsl.wf.data.source.remote;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

import pl.polsl.wf.data.source.remote.MarkdownHeader;

@RunWith(Parameterized.class)
public class MarkdownHeaderNotFoundTest {

    @Parameterized.Parameters(name = "{index}: markdown=\"{0}\"")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"==234=="},
                {"== 234 =="},
                {"\nsdfadfa\n==234=="},
                {"==234==\ndslfadflkasdfjlkadjflk"},
                {"sadfafa\n==234==\ndslfadflkasdfjlkadjflk"},
                {"====234===="},
                {"==== 234 ===="},
                {"\nsdfadfa\n====234===="},
                {"====234====\ndslfadflkasdfjlkadjflk"},
                {"sadfafa\n====234====\ndslfadflkasdfjlkadjflk"},
                {"============="},
                {"======"}
        });
    }

    @Parameterized.Parameter
    public String markdown;

    @Test
    public void getMarkdownHeader() {
        MarkdownHeader markdownChunk = new MarkdownHeader(3, 0, markdown);
        assertEquals(-1, markdownChunk.resStart);
        assertEquals(-1, markdownChunk.resEnd);
        assertEquals("", markdownChunk.headword);
    }
}

