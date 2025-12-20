package pl.polsl.wf.data.datasource.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "languages")
public class LanguageEntity
{
    @PrimaryKey
    public String code;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "is_active")
    public boolean isActive;

    @ColumnInfo(name = "is_downloaded")
    public boolean isDownloaded;

    public LanguageEntity()
    {
    }

    public LanguageEntity(String code,
                          String name,
                          boolean isActive,
                          boolean isDownloaded)
    {
        this.code = code;
        this.name = name;
        this.isActive = isActive;
        this.isDownloaded = isDownloaded;
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
        return isActive;
    }

    public boolean isDownloaded()
    {
        return isDownloaded;
    }
}
