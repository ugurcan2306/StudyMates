package com.example.firebase;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

public class MailService {

    private static final String API_KEY = "d79886de8883ee0c29ab20914b1e6f8b";
    private static final String SECRET_KEY = "3694f221e9a642d6f71269588a3a9d3f";
    private static final String FROM_EMAIL = "burak.yilmaz8745@gmail.com";
    private static final String FROM_NAME = "StudyMates";

    private MailjetClient client;

    public MailService() {
        ClientOptions options = ClientOptions.builder()
            .apiKey(API_KEY).apiSecretKey(SECRET_KEY).build();

        client = new MailjetClient(options);
    }

    public void sendWelcomeEmail(String toEmail, String toName) {
        try {
            MailjetRequest request;
            request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                    .put(new JSONObject()
                        .put(Emailv31.Message.FROM, new JSONObject()
                            .put("Email", FROM_EMAIL)
                            .put("Name", FROM_NAME))
                        .put(Emailv31.Message.TO, new JSONArray()
                            .put(new JSONObject()
                                .put("Email", toEmail)
                                .put("Name", toName)))
                        .put(Emailv31.Message.SUBJECT, "Welcome to StudyMates!")
                        .put(Emailv31.Message.HTMLPART,
                            "<h3>You have successfully registered to StudyMates!</h3>" +
                            "<p>We’re excited to have you on board.</p>")));

            MailjetResponse response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendResetPasswordEmail(String toEmail, String toName, String newPassword) 
    {
    try 
    {
        MailjetRequest request;
        request = new MailjetRequest(Emailv31.resource)
            .property(Emailv31.MESSAGES, new JSONArray()
                .put(new JSONObject()
                    .put(Emailv31.Message.FROM, new JSONObject()
                        .put("Email", FROM_EMAIL)
                        .put("Name", FROM_NAME))
                    .put(Emailv31.Message.TO, new JSONArray()
                        .put(new JSONObject()
                            .put("Email", toEmail)
                            .put("Name", toName)))
                    .put(Emailv31.Message.SUBJECT, "StudyMates Password Reset")
                    .put(Emailv31.Message.HTMLPART,
                        "<h3>Your password has been reset</h3>" +
                        "<p>Your new default password is:</p>" +
                        "<h2 style='color:blue'>" + newPassword + "</h2>" +
                        "<p>Please log in and change your password immediately for security reasons.</p>")));

        MailjetResponse response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());

    } 
   catch (Exception e) {
        // TODO: handle exception
    }
}

}

