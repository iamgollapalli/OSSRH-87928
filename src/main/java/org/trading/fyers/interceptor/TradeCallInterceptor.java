package org.trading.fyers.interceptor;

import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.trading.fyers.token.TokenFetcher;

import java.io.IOException;

@RequiredArgsConstructor
public class TradeCallInterceptor implements Interceptor {

    private final TokenFetcher token;
    private final String clientId;

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder().addHeader(HttpHeaders.AUTHORIZATION, String.format("%s:%s", clientId, token.fetchToken().getToken())).build();
        return chain.proceed(newRequest);
    }
}
