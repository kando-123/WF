package pl.polsl.wf.domain.model;

import androidx.annotation.NonNull;

public record TranslationEntryPhrase(String text, String link)
{
    public boolean hasLink()
    {
        return link != null;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "TranslationEntryPhrase[" +
                "text=" + text + ", " +
                "link=" + link + ']';
    }

}
