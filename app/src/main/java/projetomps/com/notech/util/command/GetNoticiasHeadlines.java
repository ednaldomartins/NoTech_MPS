package projetomps.com.notech.util.command;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import projetomps.com.notech.data.BancoFacade;
import projetomps.com.notech.data.BancoFacade.OnSuccessNoticiaListener;
import projetomps.com.notech.data.remote.NoticiaClient;
import projetomps.com.notech.model.Noticia;

public class GetNoticiasHeadlines implements BancoFacadeCommand {

    private String source;
    private NoticiaClient client;
    private OnSuccessNoticiaListener callback;

    public GetNoticiasHeadlines(final String source, final String api_key) {
        this.source = source;
        client = NoticiaClient.getInstance(api_key);
    }

    @Override
    public void commit(OnSuccessNoticiaListener callback) {
        this.callback = callback;
    }

    @Override
    public void commit(BancoFacade.OnSuccessUsuarioListener callback) {
    }

    @Override
    public void push() {
        client.getNoticiasHeadlines(source, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    callback.onSuccessNoticicia(Noticia.fromJson(response.getJSONArray("articles")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject response){
                Log.d(String.valueOf(statusCode), response.toString());
            }
        });
    }
}
