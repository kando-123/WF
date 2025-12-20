package pl.polsl.wf.data.model;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TranslationEntryPhrase
{
    private final String text;
    private final String link;

    public TranslationEntryPhrase(@Nonnull String text, @Nullable String link)
    {
        this.text = text;
        this.link = link;
    }

    public String getText()
    {
        return text;
    }

    public Optional<String> getLink()
    {
        return Optional.ofNullable(link);
    }

    public boolean hasLink()
    {
        return link != null;
    }
}
