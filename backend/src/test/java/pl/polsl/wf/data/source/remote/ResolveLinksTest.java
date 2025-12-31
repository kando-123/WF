package pl.polsl.wf.data.source.remote;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import pl.polsl.wf.data.model.TranslationEntryPhraseDto;

public class ResolveLinksTest {

    private static void printPhrases(List<TranslationEntryPhraseDto> phrases) {
        System.out.println("-----------------------------------------------");
        if (phrases == null) {
            System.out.println("phrases = null");
            return;
        }

        for (int i = 0; i < phrases.size(); i++) {
            TranslationEntryPhraseDto p = phrases.get(i);
            if (p == null) {
                System.out.printf("[%d] null%n", i);
                continue;
            }

            System.out.printf(
                    "[%d] text=\"%s\", link=%s%n",
                    i,
                    p.getText(),
                    p.getLink().orElse("null")
            );
        }
    }

    private void compare(List<TranslationEntryPhraseDto> expected, List<TranslationEntryPhraseDto> res)
    {
        assertEquals(expected.size(), res.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getText(), res.get(i).getText());
            assertEquals(
                    expected.get(i).getLink(),
                    res.get(i).getLink()
            );
        }

    }

    @Test
    public void noLinks()
    {
        String input = "hello world i have no links!!!";
        ResolveLinks resolveLinks = new ResolveLinks();
        List<TranslationEntryPhraseDto> res = resolveLinks.process(input);

        List<TranslationEntryPhraseDto> expected = new ArrayList<>();
        expected.add(new TranslationEntryPhraseDto("hello world i have no links!!!", null));

        compare(expected, res);
    }
    @Test
    public void simpleLink()
    {
        String input = "[[hello world i am link!!!]]";
        ResolveLinks resolveLinks = new ResolveLinks();
        List<TranslationEntryPhraseDto> res = resolveLinks.process(input);

        List<TranslationEntryPhraseDto> expected = new ArrayList<>();
        expected.add(new TranslationEntryPhraseDto(
                "hello world i am link!!!",
                "hello world i am link!!!"
        ));

        compare(expected, res);
    }
    @Test
    public void altLink()
    {
        String input = "[[hello world i am link!!!|hidden link]]";
        ResolveLinks resolveLinks = new ResolveLinks();
        List<TranslationEntryPhraseDto> res = resolveLinks.process(input);

        List<TranslationEntryPhraseDto> expected = new ArrayList<>();
        expected.add(new TranslationEntryPhraseDto(
                "hidden link",
                "hello world i am link!!!"
        ));

        compare(expected, res);
    }
    @Test
    public void mixedSimpleLink()
    {
        String input = "hello world i have 1 [[link]]s!!!";
        ResolveLinks resolveLinks = new ResolveLinks();
        List<TranslationEntryPhraseDto> res = resolveLinks.process(input);

        List<TranslationEntryPhraseDto> expected = new ArrayList<>();
        expected.add(new TranslationEntryPhraseDto("hello world i have 1 ", null));
        expected.add(new TranslationEntryPhraseDto("link", "link"));
        expected.add(new TranslationEntryPhraseDto("s!!!", null));

        compare(expected, res);
    }
    @Test
    public void mixedAltLink()
    {
        String input = "hello world i have 1 [[link|LINK]]s!!!";
        ResolveLinks resolveLinks = new ResolveLinks();
        List<TranslationEntryPhraseDto> res = resolveLinks.process(input);

        List<TranslationEntryPhraseDto> expected = new ArrayList<>();
        expected.add(new TranslationEntryPhraseDto("hello world i have 1 ", null));
        expected.add(new TranslationEntryPhraseDto("LINK", "link"));
        expected.add(new TranslationEntryPhraseDto("s!!!", null));

        compare(expected, res);
    }
    @Test
    public void mixedLinks()
    {
        String input = "hello [[world]] i have [[1|2]] [[link|LINK]]s!!!";
        ResolveLinks resolveLinks = new ResolveLinks();
        List<TranslationEntryPhraseDto> res = resolveLinks.process(input);

        List<TranslationEntryPhraseDto> expected = new ArrayList<>();
        expected.add(new TranslationEntryPhraseDto("hello ", null));
        expected.add(new TranslationEntryPhraseDto("world", "world"));
        expected.add(new TranslationEntryPhraseDto(" i have ", null));
        expected.add(new TranslationEntryPhraseDto("2", "1"));
        expected.add(new TranslationEntryPhraseDto("LINK", "link"));
        expected.add(new TranslationEntryPhraseDto("s!!!", null));

        compare(expected, res);
    }

}
