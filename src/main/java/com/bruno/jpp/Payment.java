package com.bruno.jpp;

import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class Payment {

    private String referenceId;
    private BufferedImage qr_code;
    private String qr_url;
    private String payment_url;
    private JSONObject raw;

    public Payment(JSONObject values) {
        this.raw = values;
        JSONObject message = values.getJSONObject("message");
        this.payment_url = message.getString("paymentUrl");
        this.referenceId = message.getString("referenceId");
        this.qr_url = message.getJSONObject("qrcode").getString("content");
        this.qr_code = buildImage(message.getJSONObject("qrcode").getString("base64"));
    }

    public String getReferenceId() {
        return referenceId;
    }

    public BufferedImage getQrCode() {
        return qr_code;
    }

    public String getQrUrl() {
        return qr_url;
    }

    public String getPaymentUrl() {
        return payment_url;
    }

    public JSONObject getRawResponse() {
        return raw;
    }

    public File getQrCodeFile(String path) {
        try {
            File file = new File(path + File.separator + "file.png");
            ImageIO.write(qr_code, "png", file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage buildImage(String source) {
        String[] parts = source.split(",");
        String imageString = parts[1];

        BufferedImage image = null;
        byte[] imageByte;

        Base64.Decoder decoder = Base64.getDecoder();
        imageByte = decoder.decode(imageString);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        try {
            image = ImageIO.read(bis);
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
