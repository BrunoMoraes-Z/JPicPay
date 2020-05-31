package com.bruno.jpp;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;

public class JPicPay {

    private String base_url = "https://appws.picpay.com/ecommerce/public";
    private String picpay_token = "token aqui";
    private String seller_token = "token aqui";
    private String callback = "https://localhost/callback";
    private String urlReturn = "https://localhost/pedido/";

    public JSONObject createPayment(String paymentCode, double value, Date expire, Buyer buyer) {

        JSONObject payment = new JSONObject();
        payment.put("referenceId", paymentCode);
        payment.put("callbackUrl", callback);
        payment.put("returnUrl", urlReturn + paymentCode);
        payment.put("value", value);
        payment.put("expiresAt", expire);

        JSONObject buyero = new JSONObject();
        buyero.put("firstName", buyer.getFirstName());
        buyero.put("lastName", buyer.getLastName());
        buyero.put("document", buyer.getCpf());
        buyero.put("email", buyer.getEmail());
        buyero.put("phone", buyer.getContact());
        payment.put("buyer", buyero);

        return execute(Method.POST, true, endPoint("/payments"), payment);
    }

    public JSONObject getPaymentStatus(String paymentCode) {
        return execute(Method.GET, true, endPoint("/payments/%s/status", paymentCode), null);
    }

    public JSONObject cancelPayment(String paymentCode, String authorizationId) {
        JSONObject body = new JSONObject();
        body.put("authorizationId", authorizationId);
        return execute(Method.POST, false, endPoint("/payments/%s/cancellations", paymentCode), authorizationId != null ? body : null);
    }

    public static enum Method {
        POST, GET
    }

    private JSONObject execute(Method method, boolean isPicpay, String urlString, JSONObject body) {
        URL url = null;

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection con = null;

        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            con.setRequestMethod(method.name().toUpperCase());
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        con.addRequestProperty("Content-Type", "application/json");
        if (isPicpay) {
            con.addRequestProperty("x-picpay-token", picpay_token);
        } else {
            con.addRequestProperty("x-seller-token", seller_token);
        }

        con.setDoOutput(true);

        if (body != null) {
            OutputStream os = null;
            try {
                os = con.getOutputStream();
                os.write(body.toString().getBytes());
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JSONObject response = new JSONObject();
        try {
            response.put("statusCode", con.getResponseCode());
            response.put("message", con.getResponseMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private String endPoint(String endpoint, String... args) {
        return base_url + (args == null || args.length == 0 ? endpoint : String.format(endpoint, args));
    }

}
