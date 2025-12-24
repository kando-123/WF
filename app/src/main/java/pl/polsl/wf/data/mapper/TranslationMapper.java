package pl.polsl.wf.data.mapper;

import pl.polsl.wf.data.model.*;
import pl.polsl.wf.domain.model.*;

/**
 * Conversion from TranslationDto to Translation (domain object).
 */
public class TranslationMapper
{
    private final TranslationEntryMapper entryMapper = new TranslationEntryMapper();

    public Translation mapToDomain(TranslationDto dto)
    {
        return new Translation(dto.getHeadword(),
                dto.getAttributes(),
                dto.getSourceLanguageCode(),
                dto.getTargetLanguageCode(),
                entryMapper.mapListToDomain(dto.getEntries()));
    }
}
