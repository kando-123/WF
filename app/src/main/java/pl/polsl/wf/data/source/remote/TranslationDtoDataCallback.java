package pl.polsl.wf.data.source.remote;

import java.util.List;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.model.TranslationDto;
import pl.polsl.wf.domain.model.Translation;

public class TranslationDtoDataCallback implements DataCallback<List<TranslationDto>>
{
    public Exception getException() {
        return exception;
    }

    public List<TranslationDto> getData() {
        return data;
    }

    private Exception exception;
    private List<TranslationDto> data;

    @Override
    public void onSuccess(List<TranslationDto> data) {
        this.data = data;
    }

    @Override
    public void onError(Exception exception) {
        this.exception = exception;
    }

}
