package org.trading.fyers.apis;

import org.trading.fyers.config.FyersProperties;
import org.trading.fyers.model.responses.FundsResponse;
import org.trading.fyers.token.TokenFetcher;
import org.trading.fyers.util.TradeConstants;

public class FundsResponseApi extends AbstractApiCall<FundsResponse> {

    public FundsResponseApi(TokenFetcher tokenFetcher, FyersProperties fyersProperties) {
        super(tokenFetcher, fyersProperties, fyersProperties.getHost() + TradeConstants.Fyers.FUNDS_API, FundsResponse.class);
    }

}
