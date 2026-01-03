package pl.polsl.wf.data.source.remote;

import static org.junit.Assert.fail;

import org.junit.Test;

import java.util.List;

public class RemoteSource_getTranslationsToForeignTest {
    private final String SEP = "---------------------------------";
    @Test
    public void allTest()
    {
        List<String> inputs = List.of("brat",
                "basket",
                "Burkina Faso",
                "WWW",
                "a cappella",
                "ye",
                "maroon",
                "ab",
                "Armenia",
                "nurse");
        for (int i=0; i<inputs.size(); i++)
        {
            System.out.println(SEP+"tcid"+i+SEP);
            RemoteSource remoteSource = new RemoteSource();
            try{
                remoteSource.getTranslationsToForeign(inputs.get(i), List.of("French", "Mandarin"));
            }
            catch (Exception e) {
                fail("tcid" + i + ": getTranslationsToEnglish threw exception: " + e.getMessage());
            }
        }
    }
}
