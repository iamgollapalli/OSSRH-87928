package org.trading.fyers.apis;

import org.trading.fyers.config.FyersProperties;
import org.trading.fyers.model.responses.ProfileResponse;
import org.trading.fyers.token.TokenFetcher;
import org.trading.fyers.util.TradeConstants;


public class ProfileApi extends AbstractApiCall<ProfileResponse>{

    public ProfileApi(TokenFetcher tokenFetcher, FyersProperties fyersProperties){
        super(tokenFetcher, fyersProperties, fyersProperties.getHost() + TradeConstants.Fyers.PROFILE_API, ProfileResponse.class);
    }

}
