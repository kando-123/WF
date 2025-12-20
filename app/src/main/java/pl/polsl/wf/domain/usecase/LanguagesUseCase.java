package pl.polsl.wf.domain.usecase;

import java.util.List;
import java.util.stream.Collectors;

import pl.polsl.wf.domain.repository.LanguagesRepository;
import pl.polsl.wf.domain.model.Language;

public class LanguagesUseCase
{
    private final LanguagesRepository languagesRepository;
    private final DownloadUseCase downloadUseCase;

    public LanguagesUseCase(LanguagesRepository languagesRepository,
                            DownloadUseCase downloadUseCase)
    {
        this.languagesRepository = languagesRepository;
        this.downloadUseCase = downloadUseCase;
    }

    public List<Language> getLanguages()
    {
        return languagesRepository.getAllLanguages();
    }

    public List<Language> getActiveLanguages()
    {
        return languagesRepository.getAllLanguages()
                .stream()
                .filter(Language::isActive)
                .collect(Collectors.toList());
    }

    public void toggleLanguage(String languageCode)
    {
        Language language = languagesRepository.getLanguageByCode(languageCode);
        language.setActive(!language.isActive());
        languagesRepository.updateLanguage(language);
    }

    public void downloadLanguage(String languageCode)
    {
        Language language = languagesRepository.getLanguageByCode(languageCode);
        if (language.isDownloaded())
        {
            throw new IllegalStateException("Language is already downloaded");
        }
        downloadUseCase.downloadLanguage(languageCode);
    }
}
