package com.example.idocs.ui.api;

import android.content.Context;

import io.appwrite.Client;

public class AppwriteClient {
    private static Client client;

    public static Client createClient(Context context) {
        client = new Client(context)
                .setEndpoint("https://984e-182-189-237-122.ap.ngrok.io/v1") // Your API Endpoint
                .setProject("6265f0feac18893637c1"); // Your project ID;
        return client;
    }
}
