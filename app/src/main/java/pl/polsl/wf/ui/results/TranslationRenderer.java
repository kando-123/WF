package pl.polsl.wf.ui.results;

import android.view.View;

import pl.polsl.wf.domain.model.Translation;

public interface TranslationRenderer
{
    View renderTranslation(Translation translation);
}
