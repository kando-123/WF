package pl.polsl.wf.domain.model;

public record TranslationEntryPhrase(String text, String link)
{
    public boolean hasLink()
    {
        return link != null;
    }

    @Override
    public String toString()
    {
        return "TranslationEntryPhrase[" +
                "text=" + text + ", " +
                "link=" + link + ']';
    }

}
