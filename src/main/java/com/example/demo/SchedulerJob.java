package com.example.demo;

import com.sun.xml.internal.org.jvnet.mimepull.MIMEMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.InternetAddressEditor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.websocket.Session;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

@Component
public class SchedulerJob {
    @Scheduled(cron = "0 */1 * ? * *")
     void checkTomcatInstance(){
        System.out.println("Service is running::"+isTomcatRunning(8080));
        System.out.println("Log in is working::"+isLoginWorking(8080));

    }

    private static boolean isTomcatRunning(int port) {
        try (Socket ignored = new Socket("localhost", port)) {
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }

    private static boolean isLoginWorking(Integer port)  {
        try {
            RestTemplate restTemplate = new RestTemplate();

            final String baseUrl = "http://localhost:" + port + "/login/";
            URI uri = new URI(baseUrl);
            String employee = new String();

            ResponseEntity<String> result = restTemplate.postForEntity(uri, employee, String.class);


            return result.getStatusCodeValue() == 200 ? true : false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }




}

