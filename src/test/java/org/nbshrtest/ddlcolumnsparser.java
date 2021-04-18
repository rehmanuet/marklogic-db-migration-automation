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

        while (rs.next()) {
            System.out.println("_______________________________");
            System.out.println("--DATA TYPE-- " + rs.getString(1) + " --");

            String nq = "SELECT column_name FROM information_schema.columns WHERE table_name = 'base_plan' and table_schema ='qatest' and data_type ='" + rs.getString(1) + "';";

            Statement st1 = con.createStatement();
            ResultSet rs1 = st1.executeQuery(nq);
            while (rs1.next()) {
                System.out.println("Column: " + rs1.getString(1));

            }
            System.out.println("_______________________________\n");
        }
        return rs;
    }

}
