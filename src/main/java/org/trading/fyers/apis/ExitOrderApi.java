package org.trading.fyers.apis;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.trading.fyers.config.FyersProperties;
import org.trading.fyers.model.responses.ExitOrderResponse;
import org.trading.fyers.model.responses.OpenPositionsResponse;
import org.trading.fyers.token.TokenFetcher;
import org.trading.fyers.util.TradeConstants;

import java.util.Map;

public class ExitOrderApi extends AbstractApiCall<ExitOrderResponse> {

    public ExitOrderApi(TokenFetcher tokenFetcher, FyersProperties fyersProperties) {
        super(tokenFetcher, fyersProperties, fyersProperties.getHost() + TradeConstants.Fyers.EXIT_API, ExitOrderResponse.class);
    }

    public ExitOrderResponse exitAllPositions(){
        return execute(okHttpClient.newCall(new Request.Builder(request).delete(RequestBody.create("{}".getBytes())).build()));
    }

    public ExitOrderResponse exitPosition(OpenPositionsResponse.Position position) throws JsonProcessingException {
        return execute(okHttpClient.newCall(new Request.Builder(request).delete(RequestBody.create(mapper.writeValueAsBytes(Map.of("id", position.getId())))).build()));
    }
}
