package com.lingzhan.presto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by 凌战 on 2020/5/13
 */
public class PrestoJDBC {
  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    Class.forName("com.facebook.presto.jdbc.PrestoDriver");
    Connection connection = DriverManager
        .getConnection("jdbc:presto://hnode5:8001/cassandra","sirius",null);
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery("show tables");
    while (rs.next()) {
      System.out.println(rs.getString(1));
    }
    rs.close();
    connection.close();

  }

}
