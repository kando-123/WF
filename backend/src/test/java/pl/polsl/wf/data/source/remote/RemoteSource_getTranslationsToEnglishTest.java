package pl.polsl.wf.data.source.remote;

import org.junit.Test;
import java.util.List;

import pl.polsl.wf.data.model.TranslationDto;

import static org.junit.Assert.fail;

public class RemoteSource_getTranslationsToEnglishTest {

    private final String SEP =  "--------------------------------------";

    private void assertNoException(
            int tcid,
            String headword,
            List<String> foreigns)
    {
        String header = SEP + "tcid" + tcid +SEP;
        System.out.println(header);

        try {
            RemoteSource remoteSource = new RemoteSource();
            List<TranslationDto> res =
                    remoteSource.getTranslationsToEnglish(headword, foreigns);

            System.out.println(ToStringJson.toJson(res));
        }
        catch (Exception e) {
            fail("tcid" + tcid +
                    ": getTranslationsToEnglish threw exception: " +
                    e.getMessage());
        }
    }

    @Test
    public void foreignToEnglish1Test()
    {
        assertNoException(1, "bad", List.of("Polish", "Somali"));
    }

    @Test
    public void foreignToEnglish2Test()
    {
        assertNoException(2, "mal", List.of("French"));
    }

    @Test
    public void foreignToEnglish3Test()
    {
        assertNoException(3, "bułka", List.of("French"));
    }

    @Test
    public void foreignToEnglish4Test()
    {
        assertNoException(4, "Ποσειδῶνα", List.of("Ancient Greek"));
    }

    @Test
    public void foreignToEnglish5Test()
    {
        assertNoException(5, "bułka", List.of("Polish"));
    }

    @Test
    public void foreignToEnglish6Test()
    {
        assertNoException(6, "ricordate", List.of("Italian"));
    }

    @Test
    public void foreignToEnglish7Test()
    {
        assertNoException(7, "iluk", List.of("Old French", "Tausug"));
    }

    @Test
    public void foreignToEnglish8Test()
    {
        assertNoException(8, "désengorgea", List.of("French"));
    }

    @Test
    public void foreignToEnglish9Test()
    {
        assertNoException(9, "on drive", List.of("English"));
    }

    @Test
    public void foreignToEnglish10Test()
    {
        assertNoException(10, "pas du tout", List.of("French"));
    }
}
