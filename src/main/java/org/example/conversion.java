package org.example;

import java.io.IOException;
import java.util.List;
import java.lang.reflect.Type;

import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class conversion {

    public static void main(String[] args) {
        String j = "[{\"accountNumber\":\"HRAF-Account-2019-01-17_17-01-59Account\",\"renewalEndDate\":\"2017-12-30T00:00:00.000-05:00\",\"marketSegment\":\"Large Group\",\"supportPhoneNumber\":\"888-000-0000\",\"renewalStartDate\":\"2016-12-31T00:00:00.000-05:00\",\"primaryContactEmails\":[\"support@HRAF-Account-2019-01-17_17-01-59Account.com\",\"support@us.HRAF-Account-2019-01-17_17-01-59Account.com\"],\"objectType\":\"account\",\"supportWebsiteAddress\":\"http://support.HRAF-Account-2019-01-17_17-01-59Account.com\",\"accountStatus\":\"Prospect\",\"accountName\":\"HRAF-Account-2019-01-17_17-01-59 Account\",\"name\":\"accounts_7512998464447008\",\"objectId\":\"42a7e799-c28b-4829-9db4-f52949f4e7c8\",\"associations\":{\"groups\":[{\"objectId\":\"67c26c0c-d0e4-4cc6-b4f9-7be7c399334b\"}]},\"createdBy\":\"qahraf@anthem.com\",\"creationDate\":\"2019-01-17T17:10:06.814-05:00\",\"lastModifiedBy\":\"qahraf@anthem.com\",\"lastModificationDate\":\"2019-01-17T17:10:06.814-05:00\"},{\"accountNumber\":\"HRAF-Account-2018-03-21_18-31-32Account\",\"renewalEndDate\":\"2017-12-30T00:00:00.000-05:00\",\"marketSegment\":\"Large Group\",\"supportPhoneNumber\":\"888-000-0000\",\"renewalStartDate\":\"2016-12-31T00:00:00.000-05:00\",\"primaryContactEmails\":[\"support@HRAF-Account-2018-03-21_18-31-32Account.com\",\"support@us.HRAF-Account-2018-03-21_18-31-32Account.com\"],\"objectType\":\"account\",\"supportWebsiteAddress\":\"http://support.HRAF-Account-2018-03-21_18-31-32Account.com\",\"accountStatus\":\"Prospect\",\"accountName\":\"HRAF-Account-2018-03-21_18-31-32 Account\",\"name\":\"accounts_2348952714850494\",\"objectId\":\"ecc3de62-76b4-451c-a1ef-5c22d6c00f89\",\"associations\":{\"groups\":[{\"objectId\":\"321ec2a2-5619-4f22-936c-5c8e1d00fdf1\"}]},\"createdBy\":\"qatest@anthem.com\",\"creationDate\":\"2018-03-21T18:31:57.627-04:00\",\"lastModifiedBy\":\"qatest@anthem.com\",\"lastModificationDate\":\"2018-03-21T18:31:57.627-04:00\"}]";
        String j1= "{\"accountNumber\":\"HRAF-Account-2018-03-21_18-31-32Account\",\"renewalEndDate\":\"2017-12-30T00:00:00.000-05:00\",\"marketSegment\":\"Large Group\",\"supportPhoneNumber\":\"888-000-0000\",\"renewalStartDate\":\"2016-12-31T00:00:00.000-05:00\",\"primaryContactEmails\":[\"support@HRAF-Account-2018-03-21_18-31-32Account.com\",\"support@us.HRAF-Account-2018-03-21_18-31-32Account.com\"],\"objectType\":\"account\",\"supportWebsiteAddress\":\"http://support.HRAF-Account-2018-03-21_18-31-32Account.com\",\"accountStatus\":\"Prospect\",\"accountName\":\"HRAF-Account-2018-03-21_18-31-32 Account\",\"name\":\"accounts_2348952714850494\",\"objectId\":\"ecc3de62-76b4-451c-a1ef-5c22d6c00f89\",\"associations\":{\"groups\":[{\"objectId\":\"321ec2a2-5619-4f22-936c-5c8e1d00fdf1\"}]},\"createdBy\":\"qatest@anthem.com\",\"creationDate\":\"2018-03-21T18:31:57.627-04:00\",\"lastModifiedBy\":\"qatest@anthem.com\",\"lastModificationDate\":\"2018-03-21T18:31:57.627-04:00\"}";
//        System.out.println(stringToList(j).get(0).get("objectId"));
        System.out.println(stringToMap(j1).getClass());
        System.out.println(stringToList(j).get(1).getClass());

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
