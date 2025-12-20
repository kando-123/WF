package pl.polsl.wf.common.state;

public sealed class UiState<T> permits UiState.Loading, UiState.Success, UiState.Error
{
    public static final class Loading<T> extends UiState<T>
    {
    }

    public static final class Success<T> extends UiState<T>
    {
        public final T data;

        public Success(T data)
        {
            this.data = data;
        }
    }

    public static final class Error<T> extends UiState<T>
    {
        public final Exception cause;

        public Error(Exception cause)
        {
            this.cause = cause;
        }
    }
}
