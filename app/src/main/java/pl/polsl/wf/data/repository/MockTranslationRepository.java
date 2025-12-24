package pl.polsl.wf.data.repository;

import java.util.*;

import javax.inject.Inject;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.mapper.TranslationMapper;
import pl.polsl.wf.data.model.TranslationDto;
import pl.polsl.wf.data.model.TranslationEntryDto;
import pl.polsl.wf.data.model.TranslationEntryPhraseDto;
import pl.polsl.wf.data.source.TranslationDirection;
import pl.polsl.wf.domain.model.Translation;
import pl.polsl.wf.domain.repository.TranslationRepository;

public class MockTranslationRepository implements TranslationRepository
{
    private final Random random = new Random();
    private final TranslationMapper mapper = new TranslationMapper();
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    @Inject
    public MockTranslationRepository()
    {

    }

    private String getRandomString(int minLength, int maxLength)
    {
        StringBuilder builder = new StringBuilder();
        int length = random.nextInt(minLength, maxLength);
        for (int j = 0; j < length; j++)
        {
            builder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return builder.toString();
    }

    @Override
    public void getTranslations(String headword,
                                String mainLanguageCode,
                                List<String> foreignLanguageCodes,
                                TranslationDirection direction,
                                DataCallback<List<Translation>> callback)
    {
        if (random.nextBoolean())
        {
            List<Translation> list = new ArrayList<>();
            if (direction == TranslationDirection.UNIDIRECTIONAL_TO_FOREIGN
                    || direction == TranslationDirection.BIDIRECTIONAL)
            {
                for (var foreign : foreignLanguageCodes)
                {
                    list.add(mapper.mapToDomain(mockTranslate(headword, mainLanguageCode, foreign)));
                }
            }
            if (direction == TranslationDirection.UNIDIRECTIONAL_TO_MAIN
                    || direction == TranslationDirection.BIDIRECTIONAL)
            {
                for (var foreign : foreignLanguageCodes)
                {
                    list.add(mapper.mapToDomain(mockTranslate(headword, foreign, mainLanguageCode)));
                }
            }
            callback.onSuccess(list);
        }
        else
        {
            callback.onError(new Exception("No translations"));
        }
    }

    private static final String[] PART_OF_SPEECH = { "noun", "verb", "adjective", "adverb" };
    private static final String[] STYLE = { "obsolete", "colloquial", "formal", "vulgar" };

    private TranslationDto mockTranslate(String headword,
                                         String sourceLanguageCode,
                                         String targetLanguageCode)
    {
        List<String> attributes = new ArrayList<>();
        attributes.add(PART_OF_SPEECH[random.nextInt(PART_OF_SPEECH.length)]);
        if (random.nextBoolean())
        {
            attributes.add(STYLE[random.nextInt(STYLE.length)]);
        }

        List<TranslationEntryDto> entries = new ArrayList<>();
        final int entryCount = random.nextInt(1, 6);
        for (int i = 0; i < entryCount; i++)
        {
            String definition = null;
            if (random.nextBoolean())
            {
                definition = getRandomString(5, 20).concat(" (def.)");
            }

            List<TranslationEntryPhraseDto> phrases = new ArrayList<>();
            final int phraseCount = random.nextInt(1, 6);
            for (int j = 0; j < phraseCount; j++)
            {
                phrases.add(new TranslationEntryPhraseDto(getRandomString(5, 20)));
            }

            entries.add(new TranslationEntryDto(definition, phrases));
        }

        return new TranslationDto(headword,
                attributes,
                sourceLanguageCode,
                targetLanguageCode,
                entries);
    }

    @Override
    public void getHints(String input,
                         String mainLanguageCode,
                         List<String> foreignLanguageCodes,
                         TranslationDirection direction,
                         int maxCount,
                         DataCallback<List<String>> callback)
    {
        List<String> hints = new ArrayList<>(maxCount);
        for (int i = 0; i < maxCount; i++)
        {
            hints.add(input.concat(getRandomString(1, 8)));
        }
        callback.onSuccess(hints);
    }
}
