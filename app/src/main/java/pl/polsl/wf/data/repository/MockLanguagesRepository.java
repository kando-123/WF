package pl.polsl.wf.data.repository;

import java.util.*;

import javax.inject.Inject;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.mapper.LanguageMapper;
import pl.polsl.wf.data.model.LanguageDto;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.repository.LanguagesRepository;

public class MockLanguagesRepository implements LanguagesRepository
{
    private final Map<String, LanguageDto> languages;
    private final LanguageMapper mapper = new LanguageMapper();

    @Inject
    public MockLanguagesRepository()
    {
        languages = new TreeMap<>();
        languages.put("en", new LanguageDto("en", "English", false, false, 0));
        languages.put("pl", new LanguageDto("pl", "Polish", false, false, 0));
        languages.put("de", new LanguageDto("de", "German", false, false, 0));
        languages.put("es", new LanguageDto("es", "Spanish", false, false, 0));
        languages.put("fr", new LanguageDto("fr", "French", true, false, 0));
        languages.put("it", new LanguageDto("it", "Italian", true, false, 0));
        languages.put("ru", new LanguageDto("ru", "Russian", false, false, 0));
        languages.put("ja", new LanguageDto("ja", "Japanese", false, false, 0));
        languages.put("zh", new LanguageDto("zh", "Chinese", false, false, 0));
        languages.put("ko", new LanguageDto("ko", "Korean", false, false, 0));
        languages.put("ar", new LanguageDto("ar", "Arabic", false, false, 0));
        languages.put("hi", new LanguageDto("hi", "Hindi", false, false, 0));
        languages.put("vi", new LanguageDto("vi", "Vietnamese", false, false, 0));
        languages.put("th", new LanguageDto("th", "Thai", false, false, 0));
        languages.put("id", new LanguageDto("id", "Indonesian", false, false, 0));
        languages.put("tr", new LanguageDto("tr", "Turkish", false, false, 0));
    }

    @Override
    public void getAllLanguages(DataCallback<List<Language>> callback)
    {
        callback.onSuccess(mapper.mapListToDomain(languages.values().stream().toList()));
    }

    @Override
    public void getLanguageByCode(String languageCode, DataCallback<Language> callback)
    {
        LanguageDto language = languages.get(languageCode);
        if (language != null)
        {
            callback.onSuccess(mapper.mapToDomain(language));
        }
        else
        {
            callback.onError(new Exception("Language not found"));
        }
    }

    @Override
    public void setLanguageActive(String languageCode, boolean active, DataCallback<Language> callback)
    {
        LanguageDto language = languages.get(languageCode);
        if (language != null)
        {
            language.setActive(active);
            callback.onSuccess(mapper.mapToDomain(language));
        }
        else
        {
            callback.onError(new Exception("Language not found"));
        }
    }

    private final Random random = new Random();

    @Override
    public void downloadLanguage(String languageCode)
    {
        LanguageDto language = languages.get(languageCode);
        if (language != null)
        {
            language.setDownloaded(true);

            /* 1:200Mi [B] */
            language.setDatabaseByteSize(random.nextLong(1L, 209715200L));
        }
    }
}
