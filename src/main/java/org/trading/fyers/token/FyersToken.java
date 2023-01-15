package org.trading.fyers.token;

import com.nimbusds.jwt.SignedJWT;

import java.io.*;
import java.text.ParseException;
import java.time.Instant;

public class FyersToken implements Token{

    private final String tokenFileName;
    private volatile String token;


    public FyersToken(String tokenFileName){
        this.tokenFileName = tokenFileName;
        this.token = readExistingToken();
    }

    @Override
    public boolean isTokenExpired() {
        if(this.token == null || token.isBlank() || token.isEmpty())
            return true;
        SignedJWT signedJWT = null;
        try {
            signedJWT = parseToken(this.token);
            return isTokenExpired(signedJWT);
        } catch (ParseException e) {
            return true;
        }
    }

    @Override
    public String getToken() {
        return token;
    }

    public boolean updateToken(String token) {
        this.token = token;
        return this.replaceExistingToken(token);
    }

    private SignedJWT parseToken(String token) throws ParseException {
        return SignedJWT.parse(token);
    }

    private boolean isTokenExpired(SignedJWT signedJWT) throws ParseException {
        return Instant.now().isAfter(Instant.ofEpochSecond(signedJWT.getJWTClaimsSet().getExpirationTimeClaim() - 30));
    }

    private String readExistingToken(){
        String token  = "";
        try (BufferedReader br = new BufferedReader(new FileReader(tokenFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                token = line;
            }
        } catch (IOException e) {
            //NO SONAR
        }

        return token;
    }

    private boolean replaceExistingToken(String token){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tokenFileName))) {
            bw.write(token);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
