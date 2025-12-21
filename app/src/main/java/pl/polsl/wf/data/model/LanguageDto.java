package pl.polsl.wf.data.model;

/**
 * Data transfer object representing the language from the data sources.
 */
public class LanguageDto
{
    private final String code;
    private final String name;
    private boolean active;
    private boolean downloaded;

    private long databaseByteSize;

    public LanguageDto(String code,
                       String name,
                       boolean active,
                       boolean downloaded,
                       long databaseByteSize)
    {
        this.code = code;
        this.name = name;
        this.active = active;
        this.downloaded = downloaded;
        this.databaseByteSize = databaseByteSize;
    }

    public String getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public boolean isDownloaded()
    {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded)
    {
        this.downloaded = downloaded;
    }

    public long getDatabaseByteSize()
    {
        return databaseByteSize;
    }

    public void setDatabaseByteSize(long databaseByteSize)
    {
        this.databaseByteSize = databaseByteSize;
    }
}
