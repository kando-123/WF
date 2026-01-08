package pl.polsl.wf.domain.model;

/**
 * Class representing a language.
 * <p>
 * A language is characterized by its full name (e.g. "English") and code (e.g. "en").
 *
 * @author Kay Jay O'Nail
 */
public record Language(String name,
                       String code,
                       boolean active,
                       boolean downloaded)
        implements Comparable<Language>
{
    public int compareByNameTo(Language that)
    {
        return this.name.compareToIgnoreCase(that.name);
    }

    public int compareByCodeTo(Language that)
    {
        return this.code.compareToIgnoreCase(that.code);
    }

    @Override
    public int compareTo(Language that)
    {
        return this.compareByNameTo(that);
    }
}
