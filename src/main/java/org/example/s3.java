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


public class s3 {
    public static void main(String[] args) throws IOException {

        String bucket = "highroads-marklogics-export";
        String key = "highroads_ml_data/anthem.com/1617366391468/799ac3e8-3938-4848-bb3d-4a7627f0d866";
        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
        S3Object o = s3Client.getObject(new GetObjectRequest(bucket, key));
        S3ObjectInputStream s3is = o.getObjectContent();
        String str = getAsString(s3is);
//        System.out.println(str);

//        String j = "[{\"accountNumber\":\"HRAF-Account-2019-01-17_17-01-59Account\",\"renewalEndDate\":\"2017-12-30T00:00:00.000-05:00\",\"marketSegment\":\"Large Group\",\"supportPhoneNumber\":\"888-000-0000\",\"renewalStartDate\":\"2016-12-31T00:00:00.000-05:00\",\"primaryContactEmails\":[\"support@HRAF-Account-2019-01-17_17-01-59Account.com\",\"support@us.HRAF-Account-2019-01-17_17-01-59Account.com\"],\"objectType\":\"account\",\"supportWebsiteAddress\":\"http://support.HRAF-Account-2019-01-17_17-01-59Account.com\",\"accountStatus\":\"Prospect\",\"accountName\":\"HRAF-Account-2019-01-17_17-01-59 Account\",\"name\":\"accounts_7512998464447008\",\"objectId\":\"42a7e799-c28b-4829-9db4-f52949f4e7c8\",\"associations\":{\"groups\":[{\"objectId\":\"67c26c0c-d0e4-4cc6-b4f9-7be7c399334b\"}]},\"createdBy\":\"qahraf@anthem.com\",\"creationDate\":\"2019-01-17T17:10:06.814-05:00\",\"lastModifiedBy\":\"qahraf@anthem.com\",\"lastModificationDate\":\"2019-01-17T17:10:06.814-05:00\"},{\"accountNumber\":\"HRAF-Account-2018-03-21_18-31-32Account\",\"renewalEndDate\":\"2017-12-30T00:00:00.000-05:00\",\"marketSegment\":\"Large Group\",\"supportPhoneNumber\":\"888-000-0000\",\"renewalStartDate\":\"2016-12-31T00:00:00.000-05:00\",\"primaryContactEmails\":[\"support@HRAF-Account-2018-03-21_18-31-32Account.com\",\"support@us.HRAF-Account-2018-03-21_18-31-32Account.com\"],\"objectType\":\"account\",\"supportWebsiteAddress\":\"http://support.HRAF-Account-2018-03-21_18-31-32Account.com\",\"accountStatus\":\"Prospect\",\"accountName\":\"HRAF-Account-2018-03-21_18-31-32 Account\",\"name\":\"accounts_2348952714850494\",\"objectId\":\"ecc3de62-76b4-451c-a1ef-5c22d6c00f89\",\"associations\":{\"groups\":[{\"objectId\":\"321ec2a2-5619-4f22-936c-5c8e1d00fdf1\"}]},\"createdBy\":\"qatest@anthem.com\",\"creationDate\":\"2018-03-21T18:31:57.627-04:00\",\"lastModifiedBy\":\"qatest@anthem.com\",\"lastModificationDate\":\"2018-03-21T18:31:57.627-04:00\"}]";
//        String j1= "{\"accountNumber\":\"HRAF-Account-2018-03-21_18-31-32Account\",\"renewalEndDate\":\"2017-12-30T00:00:00.000-05:00\",\"marketSegment\":\"Large Group\",\"supportPhoneNumber\":\"888-000-0000\",\"renewalStartDate\":\"2016-12-31T00:00:00.000-05:00\",\"primaryContactEmails\":[\"support@HRAF-Account-2018-03-21_18-31-32Account.com\",\"support@us.HRAF-Account-2018-03-21_18-31-32Account.com\"],\"objectType\":\"account\",\"supportWebsiteAddress\":\"http://support.HRAF-Account-2018-03-21_18-31-32Account.com\",\"accountStatus\":\"Prospect\",\"accountName\":\"HRAF-Account-2018-03-21_18-31-32 Account\",\"name\":\"accounts_2348952714850494\",\"objectId\":\"ecc3de62-76b4-451c-a1ef-5c22d6c00f89\",\"associations\":{\"groups\":[{\"objectId\":\"321ec2a2-5619-4f22-936c-5c8e1d00fdf1\"}]},\"createdBy\":\"qatest@anthem.com\",\"creationDate\":\"2018-03-21T18:31:57.627-04:00\",\"lastModifiedBy\":\"qatest@anthem.com\",\"lastModificationDate\":\"2018-03-21T18:31:57.627-04:00\"}";
//        System.out.println(stringToList(j).get(0).get("objectId"));
//        System.out.println(stringToMap(j1).getClass());
//        System.out.println(stringToList(str).get(1));
        System.out.println(stringToList(str).size());
        for (int i = 0; i <= stringToList(str).size() - 1; i++) {
            System.out.println(stringToList(str).get(i).get("objectId"));
        }
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
}
