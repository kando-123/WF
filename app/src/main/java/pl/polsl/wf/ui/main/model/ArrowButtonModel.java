package pl.polsl.wf.ui.main.model;

import android.view.View;
import android.widget.Button;

import androidx.lifecycle.Observer;

import pl.polsl.wf.data.source.TranslationDirection;
import pl.polsl.wf.domain.model.Translation;

public class ArrowButtonModel<B extends Button> implements View.OnClickListener, Observer<TranslationDirection>
{
    private final B button;
    private final TranslationDirection direction;
    private final Togglable<TranslationDirection> toggler;

    public interface Callback<T>
    {
        void call(T obj);
    }


    private Callback<B> onActivateCallback;
    private Callback<B> onDeactivateCallback;

    private boolean active;

    public ArrowButtonModel(B button,
                            TranslationDirection direction,
                            Togglable<TranslationDirection> toggler,
                            boolean active)
    {
        this.button = button;
        this.direction = direction;
        this.toggler = toggler;
        this.active = active;
    }

    public ArrowButtonModel(B button,
                            TranslationDirection direction,
                            Togglable<TranslationDirection> toggler)
    {
        this(button, direction, toggler, false);
    }

    public ArrowButtonModel<B> setOnActivateCallback(Callback<B> callback)
    {
        onActivateCallback = callback;
        return this;
    }

    public ArrowButtonModel<B> setOnDeactivateCallback(Callback<B> callback)
    {
        onDeactivateCallback = callback;
        return this;
    }

    public void call()
    {
        if (active)
        {
            onActivateCallback.call(button);
        }
        else
        {
            onDeactivateCallback.call(button);
        }
    }

    @Override
    public void onClick(View v)
    {
        toggler.toggle(direction);
    }

    @Override
    public void onChanged(TranslationDirection updated)
    {
        if (!active && direction.isIncludedIn(updated))
        {
            onActivateCallback.call(button);
            active = true;
        }
        else if (active && !direction.isIncludedIn(updated))
        {
            onDeactivateCallback.call(button);
            active = false;
        }
    }
}