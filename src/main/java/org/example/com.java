package org.example;

import com.amazonaws.services.s3.model.S3Object;
import com.marklogic.client.DatabaseClient;
import org.json.JSONArray;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class com extends hoponemain2 {
    public static void main(String[] args) throws IOException {
        hoponemain2 app2 = new hoponemain2();
//        S3
        S3Object file = app2.connectS3();
        JSONArray listURI = app2.getUriFromS3Raw(file);

        DatabaseClient client;
        client = app2.connectML();
        List<String> URI = app2.pageListUri(client);
//        System.out.println(URI);
        for (int i = 0; i <= URI.size() - 1; i++) {
            System.out.println(URI.get(i));
            Map<Object, Object> ML = app2.readDoc(client, URI.get(i));
            System.out.println(ML.get("objectId"));
            for (int y = 0; y <= listURI.length(); y++) {
                if (listURI.getJSONObject(y).get("objectId").toString().equals(ML.get("objectId"))) {
                    System.out.println("ML: "+ML);
                    System.out.println("S3: "+stringToMap(listURI.getJSONObject(y).toString()));
                    System.out.println("MATCHED");
                    break;
                }

            }
        }
//        System.out.println(app2.readDoc(client, URI.get(1)));
//        System.out.println("ML: " + app2.readDoc(client, "42a7e799-c28b-4829-9db4-f52949f4e7c8"));

//                    System.out.println("S3: " + stringToMap(listURI.getJSONObject(0).toString()));


    }
}

