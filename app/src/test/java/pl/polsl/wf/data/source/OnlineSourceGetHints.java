package pl.polsl.wf.data.source;

import org.junit.Test;

import java.util.List;

import pl.polsl.wf.data.source.remote.DataCallbackImp;

public class OnlineSourceGetHints {
    @Test
    public void getHintsTest()
    {
        OnlineDataSource onlineDataSource = new OnlineDataSource();
        DataCallbackImp<List<String>> callback = new DataCallbackImp<>();
        onlineDataSource.getHints(
                "hello",
                null,
                null,
                null,
                10,
                callback
        );

        for (int i=0; i<callback.getData().size(); i++)
            System.out.println("["+i+"] " + callback.getData().get(i) );
    }
}
