package org.trading.fyers.trade;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.trading.fyers.apis.*;
import org.trading.fyers.config.FyersProperties;
import org.trading.fyers.model.ProductType;
import org.trading.fyers.model.requests.PlaceOrder;
import org.trading.fyers.model.responses.*;
import org.trading.fyers.token.TokenFetcher;
import org.trading.fyers.token.TokenFetcherImpl;

import java.util.List;

public class Trader implements TradeOperations{

    private final ProfileApi profileApi;
    private final OrderPlaceApi orderPlaceApi;
    private final MultipleOrderPlaceApi multipleOrderPlaceApi;
    private final FundsResponseApi fundsResponseApi;
    private final ExitOrderApi exitOrderApi;
    private final ConvertApi convertApi;
    private final OpenPositionsApi openPositionsApi;

    public Trader(FyersProperties fyersProperties, String tokenFileName){
        TokenFetcher fetcher = new TokenFetcherImpl(fyersProperties, tokenFileName);
        this.profileApi = new ProfileApi(fetcher, fyersProperties);
        this.orderPlaceApi = new OrderPlaceApi(fetcher, fyersProperties);
        this.multipleOrderPlaceApi = new MultipleOrderPlaceApi(fetcher, fyersProperties);
        this.fundsResponseApi = new FundsResponseApi(fetcher, fyersProperties);
        this.exitOrderApi = new ExitOrderApi(fetcher, fyersProperties);
        this.convertApi = new ConvertApi(fetcher, fyersProperties);
        this.openPositionsApi = new OpenPositionsApi(fetcher, fyersProperties);
    }

    @Override
    public ProfileResponse fetchProfile() {
        return profileApi.call();
    }

    @Override
    public FundsResponse getFunds() {
        return fundsResponseApi.call();
    }

    @Override
    public OpenPositionsResponse openPositions() {
        return openPositionsApi.call();
    }

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrder placeOrder) throws JsonProcessingException {
        return orderPlaceApi.placeOrder(placeOrder);
    }

    @Override
    public MultiOrderResponse placeMultipleOrders(List<PlaceOrder> orders) throws JsonProcessingException {
        return multipleOrderPlaceApi.placeMultipleOrders(orders);
    }

    @Override
    public ExitOrderResponse exitPosition(OpenPositionsResponse.Position position) throws JsonProcessingException {
        return exitOrderApi.exitPosition(position);
    }

    @Override
    public ExitOrderResponse exitAllPositions() {
        return exitOrderApi.exitAllPositions();
    }

    @Override
    public ConvertApiResponse convertPosition(OpenPositionsResponse.Position position, ProductType to) throws JsonProcessingException {
        return convertApi.convert(position, to);
    }
}