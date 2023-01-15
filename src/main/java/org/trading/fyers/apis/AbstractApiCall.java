package org.trading.fyers.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.trading.fyers.config.FyersProperties;
import org.trading.fyers.interceptor.TradeCallInterceptor;
import org.trading.fyers.token.TokenFetcher;

import java.io.IOException;
import java.util.Objects;

public abstract class AbstractApiCall<T> {
    protected final OkHttpClient okHttpClient;
    protected Request request;
    private final Class<T> responseType;
    protected static final ObjectMapper mapper = new ObjectMapper();
    public AbstractApiCall(TokenFetcher tokenFetcher, FyersProperties fyersProperties, String apiUri, Class<T> responseType) {
        this.responseType = responseType;
        this.okHttpClient = new OkHttpClient.Builder().addInterceptor(new TradeCallInterceptor(tokenFetcher, fyersProperties.getClientId())).build();
        this.request = new Request.Builder().url(apiUri).addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }

    public T call(){
        return this.execute(okHttpClient.newCall(this.request));
    }

    public T execute(Call call){
        try (Response response = call.execute()){
            return mapper.readValue(Objects.requireNonNull(response.body()).bytes(), responseType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
