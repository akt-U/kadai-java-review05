package jp.co.kiramex.dbSample.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnectSample02 {

    public static void main(String[] args) {

            //3.データベース接続と結果取得のための変数宣言
            Connection con = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
            //1.ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "12297iumzltoy");

            //4.DBとやりとりする窓口(Statementオブジェクト)の作成
            stmt = con.createStatement();
            //5,6.Select文の実行と結果を格納/代入
            String sql = "SELECT*FROM country LIMIT 50";
            rs = stmt.executeQuery(sql);
            //7.結果を表示する
            while(rs.next()) {
                //Name列の値を取得
                String name = rs.getString("Name");
                //Population列の値を取得←追記
                int population = rs.getInt("population");
                //取得した値を表示
                System.out.println(name);
                System.out.println(population);

            }
            //8.接続を閉じる
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
            if(stmt != null) {
                try {
                    stmt.close();
                }catch(SQLException e) {
                    System.err.println("Statementを閉じるときにエラーが発生しました");
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

}