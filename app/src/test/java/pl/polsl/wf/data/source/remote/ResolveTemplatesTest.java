package pl.polsl.wf.data.source.remote;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ResolveTemplatesTest {
    @Test
    public void noTemplates()
    {
        String input = "i have no templates";
        ResolveTemplates resolveTemplates = new ResolveTemplates();
        String res = resolveTemplates.process(input);
        String expected = input;

        assertEquals(expected, res);
    }
    @Test
    public void lb1Templates()
    {
        String input = "{{lb|niv|East Sakhalin}} scraper (for scraping hides and planing wood)";
        ResolveTemplates resolveTemplates = new ResolveTemplates();
        String res = resolveTemplates.process(input);
        String expected = "(East Sakhalin) scraper (for scraping hides and planing wood)";

        assertEquals(expected, res);
    }
    @Test
    public void lb1ppTemplates()
    {
        String input = "{{lb|niv|East Sakhalin|something|else}} scraper (for scraping hides and planing wood)";
        ResolveTemplates resolveTemplates = new ResolveTemplates();
        String res = resolveTemplates.process(input);
        String expected = "(East Sakhalin something else) scraper (for scraping hides and planing wood)";

        assertEquals(expected, res);
    }
    @Test
    public void lb2ppTemplates()
    {
        String input = "{{lb|dskfjalsdkfjklasfj|uwu}} {{lb|niv|East Sakhalin|something|else}} scraper (for scraping hides and planing wood)";
        ResolveTemplates resolveTemplates = new ResolveTemplates();
        String res = resolveTemplates.process(input);
        String expected = "(uwu) (East Sakhalin something else) scraper (for scraping hides and planing wood)";

        assertEquals(expected, res);
    }
    @Test
    public void latn_defTemplates()
    {
        String input = "{{Latn-def|en|letter|23|double-u}}";
        ResolveTemplates resolveTemplates = new ResolveTemplates();
        String res = resolveTemplates.process(input);
        String expected = "a letter of the latin alphabet*";

        assertEquals(expected, res);
    }
    @Test
    public void cyrl_defTemplates()
    {
        String input = "{{Cyrl-def|mul|letter||u}}";
        ResolveTemplates resolveTemplates = new ResolveTemplates();
        String res = resolveTemplates.process(input);
        String expected = "a letter of the cyrylic alphabet*";

        assertEquals(expected, res);
    }
    @Test
    public void glossTemplates()
    {
        String input = "you {{gloss|second person singular personal pronoun}}";
        ResolveTemplates resolveTemplates = new ResolveTemplates();
        String res = resolveTemplates.process(input);
        String expected = "you (second person singular personal pronoun)";

        assertEquals(expected, res);
    }
    @Test
    public void glTemplates()
    {
        String input = "{{lb|yo|football|sports}} goal {{gl|an area into which the players attempt to put an object}}";
        ResolveTemplates resolveTemplates = new ResolveTemplates();
        String res = resolveTemplates.process(input);
        String expected = "(football sports) goal (an area into which the players attempt to put an object)";

        assertEquals(expected, res);
    }
    @Test
    public void n_gTemplates()
    {
        String input = "{{n-g|A Chinese interrogative for rhetorical questions.}}";
        ResolveTemplates resolveTemplates = new ResolveTemplates();
        String res = resolveTemplates.process(input);
        String expected = "A Chinese interrogative for rhetorical questions.";

        assertEquals(expected, res);
    }
    @Test
    public void ngTemplates()
    {
        String input = "{{lb|zlw-opl|sometimes|repeated}} {{ng|used to list possibilities}}; and ... or, or ... or";
        ResolveTemplates resolveTemplates = new ResolveTemplates();
        String res = resolveTemplates.process(input);
        String expected = "(sometimes repeated) used to list possibilities; and ... or, or ... or";

        assertEquals(expected, res);
    }
    @Test
    public void lTemplates()
    {
        String input = "The first letter of {{l|en|callsigns}} allocated to American broadcast television and radio stations east of the Mississippi river.";
        ResolveTemplates resolveTemplates = new ResolveTemplates();
        String res = resolveTemplates.process(input);
        String expected = "The first letter of callsigns allocated to American broadcast television and radio stations east of the Mississippi river.";

        assertEquals(expected, res);
    }
    @Test
    public void tclTemplates()
    {
        String input = "{{tcl|af|Georgia|id=Q1428}}";
        ResolveTemplates resolveTemplates = new ResolveTemplates();
        String res = resolveTemplates.process(input);
        String expected = "Georgia";

        assertEquals(expected, res);
    }
}
