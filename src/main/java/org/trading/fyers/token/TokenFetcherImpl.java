package org.trading.fyers.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.trading.fyers.config.FyersProperties;
import org.trading.fyers.model.requests.GenerateAuthCodeRequest;
import org.trading.fyers.model.requests.LoginRequest;
import org.trading.fyers.model.requests.ValidateAuthCodeRequest;
import org.trading.fyers.model.requests.VerifyPinRequest;
import org.trading.fyers.model.responses.LoginResponse;
import org.trading.fyers.model.responses.ValidateAuthCodeResponse;
import org.trading.fyers.model.responses.VerifyPinResponse;
import org.trading.fyers.util.TradeConstants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class TokenFetcherImpl implements TokenFetcher {

    private final FyersProperties fyersProperties;
    private final Call loginRequest;
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String appIdHash;
    @NonNull
    private final Token token;

    public TokenFetcherImpl(FyersProperties fyersProperties, String tokenFileName){
        this.fyersProperties = fyersProperties;
        try {
            this.loginRequest = this.okHttpClient.newCall(new Request.Builder()
                    .url(fyersProperties.getHost() + TradeConstants.Fyers.LOGIN_CALL)
                    .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .post(RequestBody.create(mapper.writeValueAsBytes(LoginRequest.builder().fy_id(fyersProperties.getUsername()).password(fyersProperties.getPassword()).build()))).build());

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(String.format("%s:%s", fyersProperties.getClientId(), fyersProperties.getClientSecret()).getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            this.appIdHash =  hexString.toString();
            this.token = new FyersToken(tokenFileName);

        } catch (JsonProcessingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Token fetchToken() {
        if(!token.isTokenExpired()){
            return this.token;
        }
        try {
            try (Response response = this.loginRequest.execute()){
                if(response.isSuccessful()) {
                    LoginResponse loginResponse = this.mapper.readValue(Objects.requireNonNull(response.body()).bytes(), LoginResponse.class);
                    VerifyPinRequest verifyPinRequest = VerifyPinRequest.builder().request_key(loginResponse.getRequest_key()).identifier(fyersProperties.getPin()).build();
                    Call verifyPingCall = this.okHttpClient.newCall(
                            new Request.Builder()
                                    .url(fyersProperties.getHost() + TradeConstants.Fyers.VERIFY_PIN)
                                    .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                    .post(RequestBody.create(mapper.writeValueAsBytes(verifyPinRequest))).build());
                    try (Response pinResponse = verifyPingCall.execute()){
                        if(pinResponse.isSuccessful()) {
                            VerifyPinResponse verifyPinResponse = this.mapper.readValue(Objects.requireNonNull(pinResponse.body()).bytes(), VerifyPinResponse.class);
                            GenerateAuthCodeRequest generateAuthCodeRequest = GenerateAuthCodeRequest.builder().fyers_id(fyersProperties.getUsername())
                                    .app_id(fyersProperties.getClientId().substring(0, fyersProperties.getClientId().length() - 4)).build();
                            Call generateAuthCode = this.okHttpClient.newCall(
                                    new Request.Builder()
                                            .url(fyersProperties.getHost() + TradeConstants.Fyers.GENERATE_AUTH_CODE)
                                            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                            .addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+verifyPinResponse.getData().getAccess_token())
                                            .post(RequestBody.create(mapper.writeValueAsBytes(generateAuthCodeRequest))).build());

                            try (Response authCodeRes = generateAuthCode.execute()){
                                if(authCodeRes.code() == 308){
                                    Map generateAuthCodeResponse = this.mapper.readValue(Objects.requireNonNull(authCodeRes.body()).bytes(), Map.class);
                                    String auth_code = Stream.of(((String)generateAuthCodeResponse.get("Url")).split("&")).filter(token -> token.startsWith("auth_code"))
                                            .findFirst().map(s -> s.split("=")[1]).orElse("");

                                    ValidateAuthCodeRequest validateAuthCodeRequest = ValidateAuthCodeRequest.builder().code(auth_code).appIdHash(this.appIdHash).build();
                                    Call validateAuthRequest = this.okHttpClient.newCall(
                                            new Request.Builder()
                                                    .url(fyersProperties.getHost() + TradeConstants.Fyers.VALIDATE_AUTH_CODE)
                                                    .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                                    .post(RequestBody.create(mapper.writeValueAsBytes(validateAuthCodeRequest))).build());
                                    try (Response validateAuthResponse = validateAuthRequest.execute()){
                                        if(validateAuthResponse.isSuccessful()){
                                            ValidateAuthCodeResponse validateAuthCodeResponse = this.mapper.readValue(Objects.requireNonNull(validateAuthResponse.body()).bytes(), ValidateAuthCodeResponse.class);
                                            this.token.updateToken(validateAuthCodeResponse.getAccess_token());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return token;
        }
        return token;
    }
}