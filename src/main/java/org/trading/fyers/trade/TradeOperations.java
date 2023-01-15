package org.trading.fyers.trade;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.trading.fyers.model.ProductType;
import org.trading.fyers.model.requests.PlaceOrder;
import org.trading.fyers.model.responses.*;

import java.util.List;

public interface TradeOperations {

    ProfileResponse fetchProfile();
    FundsResponse getFunds();
    OpenPositionsResponse openPositions();
    PlaceOrderResponse placeOrder(PlaceOrder placeOrder) throws JsonProcessingException;
    MultiOrderResponse placeMultipleOrders(List<PlaceOrder> orders) throws JsonProcessingException;
    ExitOrderResponse exitPosition(OpenPositionsResponse.Position position) throws JsonProcessingException;
    ExitOrderResponse exitAllPositions();
    ConvertApiResponse convertPosition(OpenPositionsResponse.Position position, ProductType to) throws JsonProcessingException;
}