package org.nbshrtest;

import java.sql.*;

public class BaseConstraintValidation {
    static Connection con = null;



    public static Connection getConnection() {
        if (con != null) return con;
        // get db, user, pass from settings file
        return getConnection("ttest");
    }

    private static Connection getConnection(String db_name) {
        try {
            String pgurl = "jdbc:postgresql://database-1-instance-1.cdmnlp9kk2o8.us-east-1.rds.amazonaws.com:5432/dev?user=postgres&password=HighRoads#123&ssl=false";
//            con=DriverManager.getConnection("jdbc:mysql://localhost/"+db_name+"?user="+user_name+"&password="+password);
            con = DriverManager.getConnection(pgurl);
            System.out.println(db_name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }


    public static ResultSet runQuery(String query) throws SQLException {

        con = getConnection();
        assert con != null;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        rs.next();
//        System.out.println(rs.getString("constraint_name"));
//        while (rs.next()) {
////            System.out.println(rs.getString(6)+rs.getString(15));
//            System.out.println(rs.getString(1));
//        }
        return rs;
    }
}
