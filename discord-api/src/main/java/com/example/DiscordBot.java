package com.example;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import okhttp3.*;

public class DiscordBot extends ListenerAdapter {
    private static final String API_URL = "http://localhost:8080/messages";

    public static void main(String[] args) throws Exception {
        String botToken = "OTg1MzgyODEyNTM2NDE0MjQ4.GmJnq-.FL-L6PpvkOobYoVsrw2Nr5bKL27UuO8fEW6tBM"; // Replace with your Discord bot token

        JDABuilder.createDefault(botToken, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new DiscordBot())
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) 
            return;

        String content = event.getMessage().getContentRaw();
        String username = event.getAuthor().getName();

        System.out.println("Username: " + username + " | Message: " + content);


        sendToApi(username, content);
    }

    private void sendToApi(String username, String content) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("content", content)
                .build();

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Sent to API: " + response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

