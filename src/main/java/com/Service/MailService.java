package com.Service;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class MailService {

    // ...

    public static com.mashape.unirest.http.JsonNode sendSimpleMessage() throws UnirestException {
    	String apikey = "acb93178d14e5a87f3eb830b8143ba07-f135b0f1-1579252a";
        HttpResponse<com.mashape.unirest.http.JsonNode> request = Unirest.post("https://api.mailgun.net/v3/").basicAuth("api", apikey).field("from", "Excited User <USER@YOURDOMAIN.COM>").field("to", "avadh@support.com").field("subject", "Welocme All").field("text", "HELLLLOOOOO").asJson();

        return request.getBody();
    }
}