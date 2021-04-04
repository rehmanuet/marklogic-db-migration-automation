package org.example;

import com.amazonaws.services.s3.model.S3Object;
import com.marklogic.client.DatabaseClient;
import org.json.JSONArray;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class hop1 extends hoponemain {

    public static void main(String[] args) throws IOException {
        hop1 test = new hop1();
//        test.tc_count();
        test.tc_comparison();


    }

    public void tc_count() throws IOException {
        DatabaseClient client;
        hoponemain app = new hoponemain();
        client = app.connectML();
        app.getCountFromML(client);
        app.getCountFromS3Raw();

    }

    public void tc_comparison() throws IOException {
        hoponemain app2 = new hoponemain();
        // S3
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
                    System.out.println("ML: " + ML);
                    System.out.println("S3: " + stringToMap(listURI.getJSONObject(y).toString()));
                    System.out.println("MATCHED");
                    break;
                }

            }
        }
    }
}
