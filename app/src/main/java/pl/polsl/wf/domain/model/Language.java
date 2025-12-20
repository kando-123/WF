package pl.polsl.wf.domain.model;

import java.util.Objects;

/**
 * Class representing a language.
 * <p>
 * A language is characterized by its full name (e.g. "English") and code (e.g. "en").
 *
 * @author Kay Jay O'Nail
 */
public class Language implements Comparable<Language>
{
    private final String name;

    private final String code;

    private boolean isActive;

    private boolean isDownloaded;

    public Language(String name, String code)
    {
        this.name = name;
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public String getCode()
    {
        return code;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        this.isActive = active;
    }

    public boolean isDownloaded()
    {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded)
    {
        this.isDownloaded = downloaded;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Language that
                && this.name.equals(that.name)
                && this.code.equals(that.code);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, code, isActive, isDownloaded);
    }

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
