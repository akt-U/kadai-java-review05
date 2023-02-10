package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class Review05 {

    public static void main(String[] args) {

            Connection con = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "12297iumzltoy");

            String sql = "SELECT * FROM person WHERE id = ? LIMIT 1";
            pstmt = con.prepareStatement(sql);
            System.out.println("検索キーワードを入力してください>");
            int num = keyInNum();

            pstmt.setInt(1,num);

            rs = pstmt.executeQuery();

            while(rs.next()) {
                String name = rs.getString("Name");
                int age = rs.getInt("age");
                System.out.println(name);
                System.out.println(age);

            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに以上が発生しました。");
            e.printStackTrace();
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                }catch(SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました");
                    e.printStackTrace();
                }
            }
            if(pstmt != null) {
                try {
                    pstmt.close();
                }catch(SQLException e) {
                    System.err.println("PrepareStatementを閉じるときにエラーが発生しました");
                    e.printStackTrace();
                }
            }
            if(con != null) {
                try {
                    con.close();
                }catch(SQLException e) {
                    System.err.println("データベース接続時にエラーが発生しました");
                    e.printStackTrace();
                }
            }
        }
    }


    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        }catch(IOException e) {

        }
        return line;
        }
private static int keyInNum() {
    int result = 0;
    try {
        result = Integer.parseInt(keyIn());
    } catch (NumberFormatException e) {
    }
    return result;
}
    }


