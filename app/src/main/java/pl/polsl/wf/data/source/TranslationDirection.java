package pl.polsl.wf.data.source;

public enum TranslationDirection
{
    UNIDIRECTIONAL_TO_MAIN,
    UNIDIRECTIONAL_TO_FOREIGN,
    BIDIRECTIONAL;

    public boolean isIncludedIn(TranslationDirection other)
    {
        return other == this || other == BIDIRECTIONAL;
    }
}
