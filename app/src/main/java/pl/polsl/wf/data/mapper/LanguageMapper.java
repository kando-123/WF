package pl.polsl.wf.data.mapper;

import pl.polsl.wf.data.datasource.local.entities.LanguageEntity;
import pl.polsl.wf.domain.model.Language;

public class LanguageMapper
{
    public static Language toDomain(LanguageEntity entity)
    {
        Language language = new Language(entity.getName(), entity.getCode());
        language.setActive(entity.isActive());
        language.setDownloaded(entity.isDownloaded());
        return language;
    }

    public static LanguageEntity toEntity(Language language)
    {
        LanguageEntity entity = new LanguageEntity();
        entity.code = language.getCode();
        entity.name = language.getName();
        entity.isActive = language.isActive();
        entity.isDownloaded = language.isDownloaded();
        return entity;
    }
}
