package pl.polsl.wf.common.state;

public sealed class UiState<T> permits UiState.Loading, UiState.Success, UiState.Error
{
    protected enum Status { LOADING, SUCCESS, ERROR }
    private final Status status;

    private UiState(Status status)
    {
        this.status = status;
    }

    public final boolean isLoading()
    {
        return status == Status.LOADING;
    }

    public final boolean isSuccess()
    {
        return status == Status.SUCCESS;
    }

    public final boolean isError()
    {
        return status == Status.ERROR;
    }

    public static final class Loading<T> extends UiState<T>
    {
        public Loading()
        {
            super(Status.LOADING);
        }
    }

    public static final class Success<T> extends UiState<T>
    {
        public final T data;

        public Success(T data)
        {
            super(Status.SUCCESS);
            this.data = data;
        }
    }

    public static final class Error<T> extends UiState<T>
    {
        public final Exception cause;

        public Error(Exception cause)
        {
            super(Status.ERROR);
            this.cause = cause;
        }
    }
}
