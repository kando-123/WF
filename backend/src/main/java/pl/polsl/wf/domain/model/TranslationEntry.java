package pl.polsl.wf.domain.model;

//import androidx.annotation.NonNull;
import java.util.*;

public record TranslationEntry(String definition,
                               List<TranslationEntryPhrase> phrases)
{
    public TranslationEntry(String definition,
                            List<TranslationEntryPhrase> phrases)
    {
        this.definition = definition;
        this.phrases = new ArrayList<>(phrases);
    }

    @Override
    public List<TranslationEntryPhrase> phrases()
    {
        return new ArrayList<>(phrases);
    }

//    @NonNull
    @Override
    public String toString()
    {
        return "TranslationEntry[" +
                "definition=" + definition + ", " +
                "phrases=" + phrases + ']';
    }

}
