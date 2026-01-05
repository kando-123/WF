package pl.polsl.wf.common.util;

import android.view.View;
import android.widget.Button;

import androidx.lifecycle.Observer;

/**
 *
 * @param <B> type of the button
 * @param <O> type of the observed object
 */
public abstract class ActivableButtonObserverModel<B extends Button, O>
        extends ActivableButtonModel<B>
        implements Observer<O>
{
    public ActivableButtonObserverModel(B button, boolean active)
    {
        super(button, active);
    }

    public ActivableButtonObserverModel(B button)
    {
        this(button, false);
    }
}
