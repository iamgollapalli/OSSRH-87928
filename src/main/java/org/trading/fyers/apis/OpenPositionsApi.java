package org.trading.fyers.apis;

import org.trading.fyers.config.FyersProperties;
import org.trading.fyers.model.responses.OpenPositionsResponse;
import org.trading.fyers.token.TokenFetcher;
import org.trading.fyers.util.TradeConstants;

public class OpenPositionsApi extends AbstractApiCall<OpenPositionsResponse> {
    public OpenPositionsApi(TokenFetcher tokenFetcher, FyersProperties fyersProperties) {
        super(tokenFetcher, fyersProperties, fyersProperties.getHost() + TradeConstants.Fyers.GET_POSITIONS, OpenPositionsResponse.class);
    }
}
