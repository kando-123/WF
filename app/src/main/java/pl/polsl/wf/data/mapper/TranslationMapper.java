package pl.polsl.wf.data.mapper;

import java.util.ArrayList;
import java.util.List;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.model.*;
import pl.polsl.wf.data.source.remote.DataCallbackImp;
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

    public void mapCallbackToDomain(DataCallbackImp<List<TranslationDto>> source, DataCallback<List<Translation>> dest)
    {
        if (source.getException() != null)
        {
            dest.onError(source.getException());
        }
        else {
            List<Translation> list = new ArrayList<>();
            for (TranslationDto translationDto : source.getData())
                list.add(mapToDomain(translationDto));
            dest.onSuccess(list);
        }
    }
}
