package org.example;

import com.marklogic.client.DatabaseClient;

import java.io.IOException;

public class hop1 extends App {

    public static void main(String[] args) throws IOException {
        DatabaseClient client;
        App app = new App();
        client = app.connectML();
        app.connectS3();
        app.count(client);

    }
}
