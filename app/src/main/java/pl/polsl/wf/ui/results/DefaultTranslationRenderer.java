package pl.polsl.wf.ui.results;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.stream.Collectors;

import pl.polsl.wf.R;
import pl.polsl.wf.domain.model.Translation;
import pl.polsl.wf.domain.model.TranslationEntry;
import pl.polsl.wf.domain.model.TranslationEntryPhrase;

public class DefaultTranslationRenderer implements TranslationRenderer
{
    private final LayoutInflater inflater;
    private final LinearLayout container;
    private final Context context;

    public DefaultTranslationRenderer(Context context,
                                       LayoutInflater inflater,
                                       LinearLayout container)
    {
        this.inflater = inflater;
        this.container = container;
        this.context = context;
    }

    @Override
    public View renderTranslation(Translation translation)
    {
        View itemView = inflater.inflate(R.layout.item_result,
                container, false);

        TextView tvLanguages = itemView.findViewById(R.id.languages);
        tvLanguages.setText(new StringBuilder()
                .append(translation.sourceLanguageCode())
                .append(" >> ")
                .append(translation.targetLanguageCode()));

        TextView tvHeadword = itemView.findViewById(R.id.headword);
        tvHeadword.setText(translation.headword());

        TextView tvAttributes = itemView.findViewById(R.id.attributes);
        tvAttributes.setText(String.join(" ", translation.attributes()));

        LinearLayout llEntries = itemView.findViewById(R.id.entries_list);
        llEntries.setOrientation(LinearLayout.VERTICAL);
        final int size = translation.entries().size();
        for (int i = 0; i < size; ++i)
        {
            TranslationEntry entry = translation.entries().get(i);
            View entryView = renderEntry(entry, i + 1);
            llEntries.addView(entryView);
        }

        return itemView;
    }

    private View renderEntry(TranslationEntry entry, int numeral)
    {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        String definition = entry.definition();
        if (definition != null)
        {
            TextView tvDefinition = new TextView(context);
            tvDefinition.setText(new StringBuilder()
                    .append(numeral)
                    .append(". ")
                    .append(definition));
            tvDefinition.setPaddingRelative(8, 4, 4,4);
            layout.addView(tvDefinition);

            TextView phrases = new TextView(context);
            String phrasesText = entry.phrases().stream()
                    .map(TranslationEntryPhrase::text)
                    .collect(Collectors.joining("; "));
            phrases.setText(phrasesText);
            phrases.setPaddingRelative(48, 4, 4,4);
            phrases.setTextSize(24);
            layout.addView(phrases);
        }
        else
        {
            TextView phrases = new TextView(context);
            String phrasesText = entry.phrases().stream()
                    .map(TranslationEntryPhrase::text)
                    .collect(Collectors.joining("; "));
            phrases.setText(new StringBuilder()
                    .append(numeral)
                    .append(". ")
                    .append(phrasesText));
            phrases.setPaddingRelative(8, 4, 4,4);
            phrases.setTextSize(24);
            layout.addView(phrases);
        }

        return layout;
    }
}
