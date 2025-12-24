package pl.polsl.wf.common.util;

import pl.polsl.wf.domain.model.Language;

public class WrapperDataCallback<T> implements DataCallback<T>
{
    private T data;
    private Exception exception;

    @Override
    public void onSuccess(T data)
    {
        this.data = data;
    }

    @Override
    public void onError(Exception exception)
    {
        this.exception = exception;
    }

    public void reset()
    {
        data = null;
        exception = null;
    }

    public T get() throws Exception
    {
        if (exception != null)
        {
            throw exception;
        }
        return data;
    }
}