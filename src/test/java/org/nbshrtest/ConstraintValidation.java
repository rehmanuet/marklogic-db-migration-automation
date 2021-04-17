package org.nbshrtest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConstraintValidation extends BaseConstraintValidation {

    public static void main(String[] args) throws SQLException {
        checkPrimaryKeyNamingConvention("accounts", "tenant_01");
        checkPrfimaryKeyNamingConvention("accounts", "tenant_01");
    }

    public static void checkPrimaryKeyNamingConvention(String tbl_name, String schema) throws SQLException {
        ResultSet result = runQuery("SELECT table_name,column_name ,constraint_name FROM information_schema.key_column_usage where table_name = '" + tbl_name + "' and table_schema = '" + schema + "';");
        String col_name = result.getString("column_name");
        String const_name = result.getString("constraint_name");
        System.out.println(tbl_name + "_pk_" + col_name);
        System.out.println(const_name);
//        con.close();

    }

    public static void checkPrfimaryKeyNamingConvention(String tbl_name, String schema) throws SQLException {
        ResultSet result = runQuery("SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_type = 'PRIMARY KEY' AND table_name = '" + tbl_name + "' and table_schema = '" + schema + "';");
        String tl_count = result.getString("count");

        System.out.println(tl_count);
        con.close();


    }
}
