package pl.polsl.wf.common.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import pl.polsl.wf.data.repository.*;
import pl.polsl.wf.domain.repository.*;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule
{
    @Provides
    @Singleton
    public TranslationRepository provideTranslationRepository()
    {
        return new TranslationRepositoryImpl();
    }

    @Provides
    @Singleton
    public LanguagesRepository provideLanguagesRepository()
    {
        return new MockLanguagesRepository();
    }
}
