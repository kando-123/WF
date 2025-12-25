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

public class MarkdownChunkTest {

    private final String markdown =
                    "==Header 2==\n" +
                    "===Header 3===\n" +
                    "0123456789\n" +
                    "===Header 3===\n" +
                    "9012345678\n" +
                    "==Header 2==\n" +
                    "===Header 3===\n" +
                    "8901234567\n" +
                    "===Header 3===\n" +
                    "7890123456\n";
    @Test
    public void firstLevel2MarkdownChunk () {
        MarkdownHeader header = new MarkdownHeader(2, 0, "Header 2", markdown);
        MarkdownChunk chunk = new MarkdownChunk(header, markdown);
        String expected = "\n" +
                "===Header 3===\n" +
                "0123456789\n" +
                "===Header 3===\n" +
                "9012345678\n";
        assertEquals(expected, chunk.contents);
    }
    @Test
    public void otherLevel2MarkdownChunk () {
        MarkdownHeader header = new MarkdownHeader(2, 1, "Header 2", markdown);
        MarkdownChunk chunk = new MarkdownChunk(header, markdown);
        String expected = "\n" +
                "===Header 3===\n" +
                "8901234567\n" +
                "===Header 3===\n" +
                "7890123456\n";
        assertEquals(expected, chunk.contents);
    }
    @Test
    public void firstLevel3MarkdownChunk () {
        MarkdownHeader header = new MarkdownHeader(3, 1, "Header 3", markdown);
        MarkdownChunk chunk = new MarkdownChunk(header, markdown);
        String expected = "\n" +
                "0123456789\n";
        assertEquals(expected, chunk.contents);
    }

    @Test
    public void secondLevel3MarkdownChunk () {
        MarkdownHeader header = new MarkdownHeader(3, 28, "Header 3", markdown);
        MarkdownChunk chunk = new MarkdownChunk(header, markdown);
        String expected = "\n" +
                "9012345678\n";
        assertEquals(expected, chunk.contents);
    }
}

