package com.vlazma.Utils;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.*;
@Configuration
public class RORequest {
    public Response provinceRequest() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.rajaongkir.com/starter/province")
                .get()
                .addHeader("key", "38693b9c0adbaa5163a39c1a9f929443")
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }

    public Response provinceRequest(String id) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.rajaongkir.com/starter/province?id=" + id)
                .get()
                .addHeader("key", "38693b9c0adbaa5163a39c1a9f929443")
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }

    public Response cityRequest() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.rajaongkir.com/starter/city")
                .get()
                .addHeader("key", "38693b9c0adbaa5163a39c1a9f929443")
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }

    public Response cityRequest(int id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String x = Integer.toString(id);
        Request request = new Request.Builder()
                .url("https://api.rajaongkir.com/starter/city?id=" + x)
                .get()
                .addHeader("key", "38693b9c0adbaa5163a39c1a9f929443")
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }

    public Response cityRequest(String province) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.rajaongkir.com/starter/city?province=" + province)
                .get()
                .addHeader("key", "38693b9c0adbaa5163a39c1a9f929443")
                .build();
        System.out.println("https://api.rajaongkir.com/starter/city?province=" + province);
        Response response = client.newCall(request).execute();
        return response;
    }

    public Response costRequest(ROBody rObody) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String createBody = "origin=" + rObody.getOrigin() + "&destination=" + rObody.getDestination() + "&weight="
                + rObody.getWeight() + "&courier=" + rObody.getCourier();
        RequestBody body = RequestBody.create(createBody, mediaType);
        Request request = new Request.Builder()
                .url("https://api.rajaongkir.com/starter/cost")
                .post(body)
                .addHeader("key", "38693b9c0adbaa5163a39c1a9f929443")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public int countShipmentCost(ROBody roBody) throws IOException{
        System.out.println("Origin : "+roBody.getOrigin());
        String result = costRequest(roBody).body().string();
        System.out.println("Ini Resultnya"+result);
        JSONObject jsonObject = new JSONObject(result);
        JSONArray costsArray = jsonObject.getJSONObject("rajaongkir")
                .getJSONArray("results")
                .getJSONObject(0)
                .getJSONArray("costs");

        int value = costsArray.getJSONObject(1).getJSONArray("cost")
                .getJSONObject(0).getInt("value");
        return value;
    }

}
