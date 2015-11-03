/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.calc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sun.net.www.http.HttpClient;

/**
 *
 * @author Aleks
 */
@Controller
//@PropertySource("classpath:/resources/application.properties")
public class VKAPIController {

   // @Value("${vkapi.connection.url}")
    private String connectonUrl;
   // @Value("${vkapi.api.url}")
    private String apiUrl;
   // @Value("${vkapi.connection.url.accessToken}")
    private String accessTokenUrl;
   // @Value("${vkapi.id}")
    private String clientId;
   // @Value("${vkapi.secret}")
    private String apiSecret;
   // @Value("${vkapi.redirect}")
    private String redirectUrl;

    private String accessToken;

    public void init(){
        connectonUrl = "https://oauth.vk.com/authorize?client_id=4952101&scope=12&redirect_uri=http://localhost:8080/calc/vk_callback&response_type=code&v=5.34&state=1234&display=popup";
        apiUrl = "https://api.vk.com/method/";
        apiSecret = "DrmFt30Q96RMkNnYah2Q";
        accessTokenUrl = "https://oauth.vk.com/access_token?";
        clientId="4952101";
        redirectUrl="http://localhost:8080/calc/vk_callback";
    }
    private class Param {

        private String name;
        private String value;

        public Param(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    @RequestMapping(value = "/vk_connection_url")
    public void vkConnectionUrl(HttpServletResponse response) throws IOException {
        init();
        response.sendRedirect(connectonUrl);
    }

    @RequestMapping(value = "/vk_callback", method = RequestMethod.GET)
    public String vkCallback(@RequestParam("code") String code, HttpServletRequest r, HttpServletResponse response, ModelMap model) throws IOException, JSONException {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet method = new HttpGet(accessTokenUrl
                .concat("client_id=")
                .concat(clientId)
                .concat("&client_secret=")
                .concat(apiSecret)
                .concat("&code=")
                .concat(code)
                .concat("&redirect_uri=")
                .concat(redirectUrl)
        );

        HttpResponse resp = client.execute(method);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
        JSONObject json = new JSONObject(reader.readLine());
        accessToken = json.get("access_token").toString();
        reader.close();
        
        model.addAttribute("jsonObject",
                execute("users.get", 
                new Param("user_ids","205387401"),
                new Param("fields", "photo_50,city,verified"),
                new Param("version", "5.37")
                ));
        model.addAttribute("accessToken", accessToken!=null?accessToken:"null");
        return "vk";
    }

    
    private JSONObject execute(String apiMethod, Param... params) throws IOException, JSONException {
        if (accessToken == null) {
            return new JSONObject();
        }

        String paramString = "?"
                .concat("access_token=")
                .concat(accessToken);
        if (params != null) {
            for (Param p : params) {
                paramString = paramString
                        .concat("&")
                        .concat(p.getName())
                        .concat("=")
                        .concat(p.getValue());
            }
        }

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet method = new HttpGet(apiUrl
                .concat(apiMethod)
                .concat(paramString)
        );

        HttpResponse resp = client.execute(method);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
        JSONObject json = new JSONObject(reader.readLine());
        reader.close();
        return json;
    }

}