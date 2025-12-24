package pl.polsl.wf.ui.main.model;

import pl.polsl.wf.data.source.TranslationDirection;

public interface Togglable<T>
{
    void toggle(T toggled);
}
