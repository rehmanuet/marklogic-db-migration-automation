package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class test extends hoponemain2 {
    public static void main(String[] args) {
        String s = "[{\"accountNumber\":\"HRAF-Account-2018-09-27_01-25-36Account\",\"renewalEndDate\":\"2017-12-30T00:00:00.000-05:00\",\"marketSegment\":\"Large Group\",\"supportPhoneNumber\":\"888-000-0000\",\"renewalStartDate\":\"2016-12-31T00:00:00.000-05:00\",\"primaryContactEmails\":[\"support@HRAF-Account-2018-09-27_01-25-36Account.com\",\"support@us.HRAF-Account-2018-09-27_01-25-36Account.com\"],\"objectType\":\"account\",\"supportWebsiteAddress\":\"http://support.HRAF-Account-2018-09-27_01-25-36Account.com\",\"accountStatus\":\"Prospect\",\"accountName\":\"HRAF-Account-2018-09-27_01-25-36 Account\",\"name\":\"accounts_150667954217186\",\"objectId\":\"28b72dd7-744a-42fa-803e-2dae9c738151\",\"associations\":{\"groups\":[{\"objectId\":\"d08a356d-6251-4e8e-be42-451c5d1b372f\"}]},\"createdBy\":\"qahraf@anthem.com\",\"creationDate\":\"2018-09-27T01:33:23.300-04:00\",\"lastModifiedBy\":\"qahraf@anthem.com\",\"lastModificationDate\":\"2018-09-27T01:33:23.300-04:00\"},{\"accountNumber\":\"HRAF-Account-2018-08-08_18-40-39Account\",\"renewalEndDate\":\"2017-12-30T00:00:00.000-05:00\",\"marketSegment\":\"Large Group\",\"supportPhoneNumber\":\"888-000-0000\",\"renewalStartDate\":\"2016-12-31T00:00:00.000-05:00\",\"primaryContactEmails\":[\"support@HRAF-Account-2018-08-08_18-40-39Account.com\",\"support@us.HRAF-Account-2018-08-08_18-40-39Account.com\"],\"objectType\":\"account\",\"supportWebsiteAddress\":\"http://support.HRAF-Account-2018-08-08_18-40-39Account.com\",\"accountStatus\":\"Prospect\",\"accountName\":\"HRAF-Account-2018-08-08_18-40-39 Account\",\"name\":\"accounts_3172036491210304\",\"objectId\":\"64e80427-d439-4a43-b068-551f10fcceef\",\"associations\":{\"groups\":[{\"objectId\":\"25033405-d57e-4a6b-a805-d7e864d91fbe\"}]},\"createdBy\":\"qahraf@anthem.com\",\"creationDate\":\"2018-08-08T18:09:50.879-04:00\",\"lastModifiedBy\":\"qahraf@anthem.com\",\"lastModificationDate\":\"2018-08-08T18:09:50.879-04:00\"}]";

        JSONArray array = new JSONArray(s);
//        System.out.println(array.getJSONObject(1).toString());
        System.out.println(stringToMap(array.getJSONObject(0).toString()));
        System.out.println(stringToMap(array.getJSONObject(0).toString()).getClass());
        System.out.println(array.getJSONObject(0).get("objectId"));

    }
}
