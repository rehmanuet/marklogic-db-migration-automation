package org.example;

import com.amazonaws.services.s3.model.S3Object;
import com.marklogic.client.DatabaseClient;
import org.json.JSONArray;

import java.io.IOException;


public class com extends hoponemain2{
    public static void main(String[] args) throws IOException {
        hoponemain2 app2 = new hoponemain2();
//        S3
        S3Object file = app2.connectS3();
        JSONArray listURI = app2.getUriFromS3Raw(file);

        DatabaseClient client;
        client = app2.connectML();
//        List<String> URI = app2.pageListUri(client);
//        System.out.println(URI);
//        for (int i = 0; i <= URI.size() - 1; i++) {
//            System.out.println(URI.get(i));
//            app2.readDoc(client, URI.get(i));
//            for (int y = 0; y <= listURI.size() - 1; y++) {
//                if (listURI.get(y).get("objectId").toString().equals("1189d87a-a1c8-46eb-a959-e2e579c39e95")) {
//                    System.out.println(listURI.get(y));
//                    System.out.println(y);
//                }
//            }
//        }
//        System.out.println(app2.readDoc(client, URI.get(1)));
        System.out.println(app2.readDoc(client, "42a7e799-c28b-4829-9db4-f52949f4e7c8"));

        System.out.println(stringToMap(listURI.getJSONObject(0).toString()));



    }
}

