package pl.polsl.wf.data.repository;

import java.util.List;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.mapper.TranslationMapper;
import pl.polsl.wf.data.model.TranslationDto;
import pl.polsl.wf.data.source.OnlineDataSource;
import pl.polsl.wf.data.source.TranslationDirection;
import pl.polsl.wf.data.source.remote.DataCallbackImp;
import pl.polsl.wf.domain.model.Translation;
import pl.polsl.wf.domain.repository.TranslationRepository;

/**
 * Main class managing data sources. It decides whether to use an online or offline data source.
 */
public class TranslationRepositoryImpl implements TranslationRepository
{
    private OnlineDataSource onlineDataSource;
    private TranslationMapper translationMapper;
    public TranslationRepositoryImpl()
    {
        onlineDataSource = new OnlineDataSource();
        translationMapper = new TranslationMapper();
    }
    @Override
    public void getTranslations(String headword,
                                String mainLanguageCode,
                                List<String> foreignLanguageCodes,
                                TranslationDirection direction,
                                DataCallback<List<Translation>> callback)
    {
        DataCallbackImp<List<TranslationDto>> dataCallback = new DataCallbackImp<List<TranslationDto>>();
        onlineDataSource.getTranslations(
                headword,
                mainLanguageCode,
                foreignLanguageCodes,
                direction,
                dataCallback
        );
        translationMapper.mapCallbackToDomain(dataCallback, callback);
    }

    @Override
    public void getHints(String input, String mainLanguageCode, List<String> foreignLanguageCodes, TranslationDirection direction, int maxCount, DataCallback<List<String>> callback)
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
