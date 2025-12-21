package pl.polsl.wf.domain.model;

import androidx.annotation.NonNull;

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
    public int compareByNameTo(@NonNull Language that)
    {
        return this.name.compareToIgnoreCase(that.name);
    }

    public int compareByCodeTo(@NonNull Language that)
    {
        return this.code.compareToIgnoreCase(that.code);
    }

    @Override
    public int compareTo(Language that)
    {
        return this.compareByNameTo(that);
    }
}
