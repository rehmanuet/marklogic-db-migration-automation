package org.nbshrtest;

import org.json.simple.JSONArray;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DDLParser extends BaseConstraintValidation {

    static String tbl_name = null;
    static String schema_name = null;

    public static void main(String[] args) throws SQLException {
        tbl_name = "extraction_job";
        schema_name = "qatest";
        con = getConnection();
        String query = "SELECT distinct(data_type) FROM information_schema.columns WHERE table_name = '" + tbl_name + "' and table_schema ='" + schema_name + "'";
        executeQuery(query);
        con.close();

    }

    public static void executeQuery(String query) throws SQLException {

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<ArrayList<String>> a = new ArrayList<>();

        while (rs.next()) {
            System.out.println("_______________________________");
            System.out.println("--DATA TYPE-- " + rs.getString(1) + " --");
            String str = rs.getString(1);
            ArrayList<String> value = null;
            switch (str) {
                case "ARRAY":
                    value = new ArrayList<>();
                    value.add("ARRAY");
                    break;
                case "boolean":
                    value = new ArrayList<>();
                    value.add("BOOLEAN");
                    break;
                case "character varying":
                    value = new ArrayList<>();
                    value.add("VARCHAR");

                    break;
                case "date":
                    value = new ArrayList<>();
                    value.add("DATE");

                    break;
                case "integer":
                    value = new ArrayList<>();
                    value.add("INTEGER");

                    break;
                case "jsonb":
                    value = new ArrayList<>();
                    value.add("JSONB");

                    break;
                case "timestamp with time zone":
                    value = new ArrayList<>();
                    value.add("TIMESTAMP-TZ");

                    break;
                case "timestamp without time zone":
                    value = new ArrayList<>();
                    value.add("TIMESTAMP-NTZ");

                    break;
                case "uuid":

                    value = new ArrayList<>();
                    value.add("UUID");

                    break;
                case "text":

                    value = new ArrayList<>();
                    value.add("TEXT");

                    break;
                case "bigint":

                    value = new ArrayList<>();
                    value.add("BIGINT");
                    break;
                default:
                    System.out.println("please the type :" + rs.getString(1));
            }

            String nq = "SELECT column_name FROM information_schema.columns WHERE table_name = '" + tbl_name + "' and table_schema ='" + schema_name + "' and data_type ='" + rs.getString(1) + "';";

            Statement st1 = con.createStatement();
            ResultSet rs1 = st1.executeQuery(nq);
            while (rs1.next()) {

                System.out.println("Column: " + rs1.getString(1));
                if (value != null) {
                    value.add(rs1.getString(1));
                }

            }
            a.add(value);
            System.out.println("_______________________________\n");
        }
        System.out.println(a);
        String jsonStr = JSONArray.toJSONString(a);
        System.out.println(jsonStr);
    }
}