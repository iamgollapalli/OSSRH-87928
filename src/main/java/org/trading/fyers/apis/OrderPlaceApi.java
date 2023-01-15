package org.trading.fyers.apis;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.trading.fyers.config.FyersProperties;
import org.trading.fyers.model.requests.PlaceOrder;
import org.trading.fyers.model.responses.PlaceOrderResponse;
import org.trading.fyers.token.TokenFetcher;
import org.trading.fyers.util.TradeConstants;

public class OrderPlaceApi extends AbstractApiCall<PlaceOrderResponse>{
    public OrderPlaceApi(TokenFetcher tokenFetcher, FyersProperties fyersProperties) {
        super(tokenFetcher, fyersProperties, fyersProperties.getHost() + TradeConstants.Fyers.PLACE_ORDER, PlaceOrderResponse.class);
    }

    public PlaceOrderResponse placeOrder(PlaceOrder placeOrder) throws JsonProcessingException {
        Request request = new Request.Builder(this.request).post(RequestBody.create(mapper.writeValueAsBytes(placeOrder))).build();
        return execute(okHttpClient.newCall(request));
    }


}
