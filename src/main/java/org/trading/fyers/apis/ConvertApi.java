package org.trading.fyers.apis;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.trading.fyers.config.FyersProperties;
import org.trading.fyers.model.ProductType;
import org.trading.fyers.model.requests.ConvertPositionRequest;
import org.trading.fyers.model.responses.ConvertApiResponse;
import org.trading.fyers.model.responses.OpenPositionsResponse;
import org.trading.fyers.token.TokenFetcher;
import org.trading.fyers.util.TradeConstants;

public class ConvertApi extends AbstractApiCall<ConvertApiResponse>{
    public ConvertApi(TokenFetcher tokenFetcher, FyersProperties fyersProperties) {
        super(tokenFetcher, fyersProperties, fyersProperties.getHost() + TradeConstants.Fyers.CONVERT_API, ConvertApiResponse.class);
    }

    public ConvertApiResponse convert(OpenPositionsResponse.Position position, ProductType to) throws JsonProcessingException {
        if(position.openLots() > 0){

            ConvertPositionRequest convertPositionRequest = ConvertPositionRequest.builder().symbol(position.getSymbol())
                    .positionSide(position.getSide()).convertFrom(position.getProductType()).convertTo(to)
                    .convertQty(position.openLots()).build();

            return execute(okHttpClient.newCall(new Request.Builder(request).put(RequestBody.create(mapper.writeValueAsBytes(convertPositionRequest))).build()));
        }
        return null;
    }
}
