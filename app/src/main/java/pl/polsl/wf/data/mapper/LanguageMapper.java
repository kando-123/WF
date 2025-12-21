package pl.polsl.wf.data.mapper;

import java.util.List;
import java.util.stream.Collectors;

import pl.polsl.wf.data.model.LanguageDto;
import pl.polsl.wf.domain.model.Language;

/**
 * Conversion from LanguageDto to Language (domain object).
 */
public class LanguageMapper
{
    public Language mapToDomain(LanguageDto dto)
    {
        return new Language(dto.getName(),
                dto.getCode(),
                dto.isActive(),
                dto.isDownloaded());
    }

    public List<Language> mapListToDomain(List<LanguageDto> dtos)
    {
        return dtos.stream().map(this::mapToDomain).collect(Collectors.toList());
    }
}
