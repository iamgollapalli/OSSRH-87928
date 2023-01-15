package org.trading.fyers.apis;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.trading.fyers.config.FyersProperties;
import org.trading.fyers.model.requests.PlaceOrder;
import org.trading.fyers.model.responses.MultiOrderResponse;
import org.trading.fyers.token.TokenFetcher;
import org.trading.fyers.util.TradeConstants;

import java.util.List;

public class MultipleOrderPlaceApi extends AbstractApiCall<MultiOrderResponse>{

    public MultipleOrderPlaceApi(TokenFetcher tokenFetcher, FyersProperties fyersProperties){
        super(tokenFetcher, fyersProperties, fyersProperties.getHost() + TradeConstants.Fyers.PLACE_MULTI_ORDER, MultiOrderResponse.class);
    }

    public MultiOrderResponse placeMultipleOrders(List<PlaceOrder> orders) throws JsonProcessingException {
        return execute(okHttpClient.newCall(new Request.Builder(request).post(RequestBody.create(mapper.writeValueAsBytes(orders))).build()));
    }
}
