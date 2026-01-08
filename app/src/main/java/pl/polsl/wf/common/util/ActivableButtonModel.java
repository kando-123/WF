package pl.polsl.wf.common.util;

import android.view.View;
import android.widget.Button;

public abstract class ActivableButtonModel<B extends Button>
        extends ActivableModel<B>
        implements View.OnClickListener
{
    public ActivableButtonModel(B button, boolean active)
    {
        super(button, active);
    }

    public ActivableButtonModel(B button)
    {
        this(button, false);
    }
}
