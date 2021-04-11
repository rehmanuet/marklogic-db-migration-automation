package org.nbshrtest;

import com.amazonaws.services.s3.model.S3Object;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.marklogic.client.DatabaseClient;
import org.json.JSONArray;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestMLtoS3 extends BaseClass {

    public static void main(String[] args) throws IOException {
        TestMLtoS3 test = new TestMLtoS3();
//        test.tc_count();
        test.tc_comparison();
    }

    public void tc_count() throws IOException {
        DatabaseClient client;
        BaseClass obj = new BaseClass();
        client = obj.connectML();
        obj.getCountFromML(client);
        obj.getCountFromS3Raw();
        client.release();

    }

    public void tc_comparison() throws IOException {
        BaseClass obj = new BaseClass();
        // Create a file for Error logging
        FileWriter errorLog = obj.writeFile();
        // S3
        S3Object file = obj.connectS3();
        JSONArray listURI = obj.getUriFromS3Raw(file);
        // ML
        DatabaseClient client;
        client = obj.connectML();
        List<String> URI = obj.pageListUri(client);
        //System.out.println(URI);
        for (int i = 0; i <= URI.size() - 1; i++) {
//            System.out.println(URI.get(i));
            Map<Object, Object> ML = obj.readDoc(client, URI.get(i));
            for (int y = 0; y <= listURI.length(); y++) {
                if (listURI.getJSONObject(y).get("objectId").toString().equals(ML.get("objectId"))) {
                    MapDifference<Object, Object> diff = Maps.difference(ML, stringToMap(listURI.getJSONObject(y).toString()));
                    System.out.println("ObjectID: " + ML.get("objectId"));
                    if (diff.entriesDiffering().size() != 0) {
                        System.out.println("MisMatched ObjectID: " + ML.get("objectId"));
                        errorLog.write("MisMatched ObjectID: " + ML.get("objectId"));
                        errorLog.append(System.getProperty("line.separator"));
                        errorLog.write("ML: " + ML);
                        errorLog.append(System.getProperty("line.separator"));
                        errorLog.write("S3: " + stringToMap(listURI.getJSONObject(y).toString()));
                        errorLog.append(System.getProperty("line.separator"));
                        errorLog.write("Difference: "+String.valueOf(diff.entriesDiffering()));
                        errorLog.append(System.getProperty("line.separator"));
                        errorLog.append(System.getProperty("line.separator"));
                    }
                    break;
                }
            }
        }
        errorLog.close();
        client.release();
    }
}
