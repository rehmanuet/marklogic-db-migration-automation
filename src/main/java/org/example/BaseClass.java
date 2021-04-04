package org.example;

import java.io.IOException;
import java.util.List;
import java.lang.reflect.Type;

import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.document.TextDocumentManager;
import com.marklogic.client.io.JacksonHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.query.*;
import org.json.JSONArray;

import java.util.ArrayList;


public class BaseClass {

    public DatabaseClient connectML() {
        System.out.println("Connecting ML");

        return DatabaseClientFactory.newClient(
                "bldintdb.highroads.local", 8002, "p2a-aws-bldint-00-content",
                new DatabaseClientFactory.DigestAuthContext("admin", "admin"));

    }

    public List<String> pageListUri(DatabaseClient client) {

        List<String> uriList = new ArrayList<>();
        QueryManager queryMgr = client.newQueryManager();
        StructuredQueryBuilder qb = new StructuredQueryBuilder();
        StructuredQueryDefinition querydef = qb.directory(1, "/anthem.com/accounts/");
        SearchHandle results = queryMgr.search(querydef, new SearchHandle(), 10);
        long pageLength = results.getPageLength();
        long totalResults = results.getTotalResults();
//        System.out.println("totalResults: " + totalResults + " pageLength: " + pageLength);
//        long timesToLoop = totalResults / pageLength;
//        System.out.println(timesToLoop);
        for (int i = 0; i < totalResults; i = (int) (i + pageLength)) {
//            System.out.println("Printing Results from: " + (i) + " to: " + (i + pageLength));
            results = queryMgr.search(querydef, new SearchHandle(), i + 1); // initially it was i but now i+1 due duplication error
            MatchDocumentSummary[] summaries = results.getMatchResults();//10 results because page length is 10
            for (MatchDocumentSummary summary : summaries) {
//                System.out.println("Extracted from URI-> " + summary.getUri());
                uriList.add(summary.getUri());
            }
            if (i >= 1000) {//number of URI to store/retreive. plus 10

                System.out.println("BREAK");
                break;
            }
        }
        //To Remove the Duplication
        //uriList = uriList.stream().distinct().collect(Collectors.toList());
        uriList = new ArrayList<>(uriList);
//        client.release();

        return uriList;
    }

    public void listUri(DatabaseClient client) {
        QueryManager queryMgr = client.newQueryManager();
        StructuredQueryBuilder structuredQueryBuilder = new StructuredQueryBuilder();
        StructuredQueryDefinition query = structuredQueryBuilder.directory(0, "/anthem.com/accounts/");
        SearchHandle resultsHandle = queryMgr.search(query, new SearchHandle());
        MatchDocumentSummary[] matches = resultsHandle.getMatchResults();
        System.out.println(resultsHandle.getTotalResults());
        long pageLength = resultsHandle.getPageLength();
        System.out.println(pageLength);
        for (MatchDocumentSummary match : matches) {
            System.out.println("Extracted from uri: " + match.getUri());
        }
        client.release();
    }

    public void getCountFromML(DatabaseClient client) {
        QueryManager queryMgr = client.newQueryManager();
        StructuredQueryBuilder qb = new StructuredQueryBuilder();
//        StructuredQueryDefinition querydef = qb.directory(true, "/anthem.com/serviceAreas/");
        StructuredQueryDefinition querydef = qb.directory(true, "/anthem.com/accounts/");
//        StructuredQueryDefinition querydef = qb.directory(0, "/anthem.com/serviceAreas/");
        SearchHandle resultsHandle = queryMgr.search(querydef, new SearchHandle());
        MatchDocumentSummary[] results = resultsHandle.getMatchResults();
        System.out.println("Total count from ML: " + resultsHandle.getTotalResults());
//        client.release();
    }

    public Map<Object, Object> readDoc(DatabaseClient client, String objectURI) {
//    public void readDoc(DatabaseClient client) {
//        String filename = "4b00f3f5-05ad-4183-9024-e03413e0340f";
        JSONDocumentManager docMgr = client.newJSONDocumentManager();

//        String docId = "/anthem.com/accounts/" + objectURI;

        JacksonHandle handle = new JacksonHandle();

//        docMgr.read(docId, handle);
        docMgr.read(objectURI, handle);
        JsonNode node = handle.get();
//        System.out.println(node);
//        System.out.println(stringToMap(node.toString()));

//        client.release();
        return stringToMap(node.toString());
    }

    public void createDoc(DatabaseClient client) {
        // Make a document manager to work with text files.
        TextDocumentManager docMgr = client.newTextDocumentManager();
        // Define a URI value for a document.
        String docId = "/example/text.txt";
        // Create a handle to hold string content.
        StringHandle handle = new StringHandle();
        // Give the handle some content
        handle.set("A simple text document");
        // Write the document to the database with URI from docId
        // and content from handle
        docMgr.write(docId, handle);

        // release the client
        client.release();

    }

    private static String getAsString(InputStream is) throws IOException {
        if (is == null)
            return "";
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, StringUtils.UTF8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            is.close();
        }
        return sb.toString();
    }

    public static Map<Object, Object> stringToMap(String payload) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return new TreeMap<Object, Object>(mapper.readValue(payload, Map.class));
        } catch (IOException e) {
            return null;
        }
    }

    public static List<Map<String, Object>> stringToList(String payload) {
        Gson gson = new Gson();
        Type resultType = new TypeToken<List<Map<String, Object>>>() {
        }.getType();
        return gson.fromJson(payload, resultType);
    }

    public S3Object connectS3() throws IOException {
        String bucket = "highroads-marklogics-export";
        String key = "highroads_ml_data/anthem.com/1617366391468/799ac3e8-3938-4848-bb3d-4a7627f0d866";
//        String key = "highroads_ml_data/anthem.com/1617178897343/799ac3e8-3938-4848-bb3d-4a7627f0d866";
        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
        S3Object o = s3Client.getObject(new GetObjectRequest(bucket, key));
        System.out.println("Connecting S3");
        return o;
    }

    public void getCountFromS3Raw() throws IOException {
        S3Object o = connectS3();
        S3ObjectInputStream s3is = o.getObjectContent();
        String str = getAsString(s3is);
        System.out.println("Total count from S3: " + stringToList(str).size());
//        for (int i = 0; i <= stringToList(str).size() - 1; i++) {
//            System.out.println(stringToList(str).get(i).get("objectId"));
//        }

    }

    public JSONArray getUriFromS3Raw(S3Object o) throws IOException {
        S3ObjectInputStream s3is = o.getObjectContent();
        String str = getAsString(s3is);
        return new JSONArray(str);
    }
}
