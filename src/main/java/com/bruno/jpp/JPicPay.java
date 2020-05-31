package com.bruno.jpp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;

public class JPicPay {

    private String picpay_token = "";
    private String seller_token = "";
    private String callback = "";
    private String urlReturn = "";

    public void createPayment(String paymentCode, double value, Date expire, Buyer buyer) {

    }

    public void getPaymentStatus(String paymentCode) {

    }

    public void cancelPayment(String paymentCode) {

    }

    public static enum Method {
        POST, GET
    }

    private void execute(Method method, boolean isPicpay, String urlString) {
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

    }

}
