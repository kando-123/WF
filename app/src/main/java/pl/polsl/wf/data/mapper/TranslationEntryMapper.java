package pl.polsl.wf.data.mapper;

import java.util.List;
import java.util.stream.Collectors;

import pl.polsl.wf.data.model.TranslationEntryDto;
import pl.polsl.wf.domain.model.TranslationEntry;

class TranslationEntryMapper
{
    private final TranslationEntryPhraseMapper phraseMapper = new TranslationEntryPhraseMapper();

    TranslationEntry mapToDomain(TranslationEntryDto dto)
    {
        return new TranslationEntry(dto.getDefinition().orElse(null),
                phraseMapper.mapListToDomain(dto.getPhrases()));
    }

    List<TranslationEntry> mapListToDomain(List<TranslationEntryDto> dtos)
    {
        return dtos.stream().map(this::mapToDomain).collect(Collectors.toList());
    }
}
