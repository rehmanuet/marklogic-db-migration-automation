package org.nbshrtest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConstraintValidation extends BaseConstraintValidation {

    public static void main(String[] args) throws SQLException {
//        checkPrimaryKeyNamingConvention("base_plan", "public");
//        checkIfPrimaryKeyExist("base_plan", "public");
//        checkNotNULL("base_plan", "effective_datetime", "public");
//        checkENUM("base_plan", "plan_year", "public");
        checkEmptyQuotes("base_plan", "description", "public");

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

}
