package pl.polsl.wf.data.source.remote;

import pl.polsl.wf.common.util.DataCallback;

public class DataCallbackImp<T> implements DataCallback<T>
{
    public Exception getException() {
        return exception;
    }

    public T getData() {
        return data;
    }

    private Exception exception;
    private T data;

    @Override
    public void onSuccess(T data) {
        this.data = data;
    }

    @Override
    public void onError(Exception exception) {
        this.exception = exception;
    }
}
