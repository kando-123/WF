//package pl.polsl.wf.data.source.remote;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//
//import pl.polsl.wf.data.source.remote.MarkdownHeader;
//
//public class MarkdownHeaderHeadwordTest {
//    private final String markdown =
//            "== Header 2a ==\n"
//            + "=== Header 3a ===\n"
//            + "== Header 2b ==\n"
//            + "=== Header 3b ===\n"
//            + "== Header ==\n"
//            + "=== Header ===\n"
//            + "== Header ==\n";
//
//    ///  right header even though other level 2 header is present
//    @Test
//    public void rightHeadwordTest()
//    {
//        MarkdownHeader markdownHeader = new MarkdownHeader(2, 0, "Header 2b", markdown );
//        assertEquals(34, markdownHeader.resStart);
//        assertEquals(49, markdownHeader.resEnd);
//        assertEquals("Header 2b", markdownHeader.headword);
//    }
//
//    ///  right header even though there's a level 2 header with the same headword before
//    @Test
//    public void rightLevelTest()
//    {
//        MarkdownHeader markdownHeader = new MarkdownHeader(3, 0, "Header", markdown );
//        assertEquals(81, markdownHeader.resStart);
//        assertEquals(95, markdownHeader.resEnd);
//        assertEquals("Header", markdownHeader.headword);
//    }
//
//    @Test
//    public void rightPositionTest()
//    {
//        MarkdownHeader markdownHeader = new MarkdownHeader(2, 69, "Header", markdown );
//        assertEquals(96, markdownHeader.resStart);
//        assertEquals( 108, markdownHeader.resEnd);
//        assertEquals("Header", markdownHeader.headword);
//    }
//}
