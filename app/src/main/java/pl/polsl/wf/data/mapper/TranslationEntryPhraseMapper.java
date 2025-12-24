package pl.polsl.wf.data.mapper;

import java.util.List;
import java.util.stream.Collectors;

import pl.polsl.wf.data.model.TranslationEntryPhraseDto;
import pl.polsl.wf.domain.model.TranslationEntryPhrase;

class TranslationEntryPhraseMapper
{
    TranslationEntryPhrase mapToDomain(TranslationEntryPhraseDto dto)
    {
        return new TranslationEntryPhrase(dto.getText(), dto.getLink().orElse(null));
    }

    List<TranslationEntryPhrase> mapListToDomain(List<TranslationEntryPhraseDto> dtos)
    {
        return dtos.stream().map(this::mapToDomain).collect(Collectors.toList());
    }
}
