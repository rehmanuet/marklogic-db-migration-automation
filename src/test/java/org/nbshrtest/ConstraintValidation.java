package org.nbshrtest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConstraintValidation extends BaseConstraintValidation {

    public static void main(String[] args) throws SQLException {
//        checkPrimaryKeyNamingConvention("base_plan", "public");
//        checkIfPrimaryKeyExist("base_plan", "public");
//        checkNotNULL("base_plan", "effective_datetime", "public");
//        checkENUM("base_plan", "plan_year", "public");
//        checkEmptyQuotes("base_plan", "description", "public");
//        checkBool("base_plan", "min_value_coverage", "public");
//        checkDuplicationOnPKAndUniqueKey("base_plan", "public");
//        checkDate("base_plan", "plan_due_date", "public");
        checkTimestampt("base_plan", "plan_due_datetime", "public");

    }
    public static void checkPrimaryKeyNamingConvention(String tbl_name, String schema) throws SQLException {
        ResultSet result = runQuery("SELECT table_name,column_name ,constraint_name FROM information_schema.key_column_usage where table_name = '" + tbl_name + "' and table_schema = '" + schema + "';");
        String col_name = result.getString("column_name");
        String const_name = result.getString("constraint_name");
        System.out.println(tbl_name + "_pk_" + col_name);
        System.out.println(const_name);
        // TODO Add assertion for above two statements
//        con.close();

    }

    public static void checkIfPrimaryKeyExist(String tbl_name, String schema) throws SQLException {
        ResultSet result = runQuery("SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_type = 'PRIMARY KEY' AND table_name = '" + tbl_name + "' and table_schema = '" + schema + "';");
        Integer tl_count = Integer.valueOf(result.getString("count"));
        System.out.println(tl_count);
        // TODO Add assertion
//        con.close();
    }

    public static void checkNotNULL(String tbl_name, String col_name, String schema) throws SQLException {
        ResultSet result = runQuery("SELECT COUNT(*) FROM " + schema + "." + tbl_name + " WHERE " + col_name + " is null;");
        Integer tl_count = Integer.valueOf(result.getString("count"));
        System.out.println(tl_count);
        // TODO Add assertion and logic to logged those id whose constraint is violated
//        con.close();
    }

    public static void checkENUM(String tbl_name, String col_name, String schema) throws SQLException {
        ResultSet result = runQuery("SELECT COUNT(*) FROM " + schema + "." + tbl_name + " WHERE cast(" + col_name + " as text) !~ '^([0-9][0-9]*|\\{[0-9][0-9]*(,[0-9][0-9]*)*\\})$';");
        Integer tl_count = Integer.valueOf(result.getString("count"));
        System.out.println(tl_count);
        // TODO Add assertion and logic to logged those id whose constraint is violated
        con.close();
    }

    public static void checkEmptyQuotes(String tbl_name, String col_name, String schema) throws SQLException {
        ResultSet result = runQuery("SELECT COUNT(*) FROM " + schema + "." + tbl_name + " WHERE ltrim(rtrim(" + col_name + ")) = '';");
        Integer tl_count = Integer.valueOf(result.getString("count"));
        System.out.println(tl_count);
        // TODO Add assertion and logic to logged those id whose constraint is violated
        con.close();
    }

    public static void checkBool(String tbl_name, String col_name, String schema) throws SQLException {
        ResultSet result = runQuery("SELECT COUNT(*) FROM " + schema + "." + tbl_name + " WHERE " + col_name + " NOT IN (true,false,NULL) ;");
        Integer tl_count = Integer.valueOf(result.getString("count"));
        System.out.println(tl_count);
        // TODO Add assertion and logic to logged those id whose constraint is violated
        con.close();
    }

    public static void checkDuplicationOnPKAndUniqueKey(String tbl_name, String schema) throws SQLException {
        ResultSet result_pk = runQuery("SELECT table_name,column_name ,constraint_name FROM information_schema.key_column_usage where table_name = '" + tbl_name + "' and table_schema = '" + schema + "';");
//        String col_name = result_pk.getString("column_name");
        String col_name = result_pk.getString("column_name");
        ResultSet result = runQuery("SELECT count(*) FROM ( SELECT COUNT(*) OVER (PARTITION BY " + col_name + ") AS cnt FROM " + schema + "." + tbl_name + ") AS t WHERE t.cnt > 1");
        Integer tl_count = Integer.valueOf(result.getString("count"));
        System.out.println(tl_count);
        // TODO Add assertion and logic to logged those id whose constraint is violated
        con.close();
    }

    public static void checkDate(String tbl_name, String col_name, String schema) throws SQLException {
        ResultSet result = runQuery("SELECT COUNT(*) FROM " + schema + "." + tbl_name + " WHERE cast(" + col_name + " as text) !~ '[1-2][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]$';");
        Integer tl_count = Integer.valueOf(result.getString("count"));
        System.out.println(tl_count);
        // TODO Add assertion and logic to logged those id whose constraint is violated
        con.close();

    }
    public static void checkTimestampt(String tbl_name, String col_name, String schema) throws SQLException {
        ResultSet result = runQuery("SELECT COUNT(*) FROM " + schema + "." + tbl_name + " WHERE cast(" + col_name + " as text) !~ '(\\d{4}-\\d{2}-\\d{2}) +(\\d{2}:\\d{2}:\\d{2}\\+\\d{2})';");
        Integer tl_count = Integer.valueOf(result.getString("count"));
        System.out.println(tl_count);
        // TODO Add assertion and logic to logged those id whose constraint is violated
        con.close();

    }
}
