package pl.polsl.wf.common.util;

public class ActivableModel<T>
{
    protected final T object;

    public interface Callback<T>
    {
        void call(T obj);
    }

    private Callback<T> onActivateCallback;
    private Callback<T> onDeactivateCallback;

    private boolean active;

    public ActivableModel(T object, boolean active)
    {
        this.object = object;
        this.active = active;
    }

    public boolean isActive()
    {
        return active;
    }

    public final ActivableModel<T> setOnActivateCallback(Callback<T> callback)
    {
        onActivateCallback = callback;
        return this;
    }

    public final ActivableModel<T> setOnDeactivateCallback(Callback<T> callback)
    {
        onDeactivateCallback = callback;
        return this;
    }

    public final void call()
    {
        if (active)
        {
            onActivateCallback.call(object);
        }
        else
        {
            onDeactivateCallback.call(object);
        }
    }

    public final void activate()
    {
        onActivateCallback.call(object);
        active = true;
    }

    public final void deactivate()
    {
        onDeactivateCallback.call(object);
        active = false;
    }

    public final void toggle(boolean activateCondition, boolean deactivateCondition)
    {
        if (!active && activateCondition)
        {
            activate();
        }
        else if (active && deactivateCondition)
        {
            deactivate();
        }
    }

    public final void toggle(boolean activateCondition)
    {
        toggle(activateCondition, !activateCondition);
    }

    public final void toggle()
    {
        if (!active)
        {
            activate();
        }
        else
        {
            deactivate();
        }
    }
}