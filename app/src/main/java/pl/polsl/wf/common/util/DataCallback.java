package pl.polsl.wf.common.util;

public interface DataCallback<T>
{
    void onSuccess(T data);

    void onError(Exception exception);
}
