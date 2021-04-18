package org.nbshrtest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ddlcolumnsparser extends BaseConstraintValidation {


    public static void main(String[] args) throws SQLException {

        con = getConnection();
        String query = "SELECT distinct(data_type) FROM information_schema.columns WHERE table_name = 'base_plan' and table_schema ='qatest'";
        ResultSet resultSet = runQuery(query);


    }

    public static ResultSet runQuery(String query) throws SQLException {


        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
//        rs.next();

//        System.out.println(rs.getString("constraint_name"));
        while (rs.next()) {
//            System.out.println(rs.getString(6)+rs.getString(15));

            System.out.println("--DATA TYPE-- "+rs.getString(1)+" --");

//            ArrayList<String> rs.getString(1)= null;
            String nq = "SELECT column_name FROM information_schema.columns WHERE table_name = 'base_plan' and table_schema ='qatest' and data_type ='" + rs.getString(1) + "';";
            runQuery(nq);
            System.out.println("_______________________________");
        }
        return rs;
    }

}
