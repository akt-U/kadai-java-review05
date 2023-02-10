package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnectSample05 {

    public static void main(String[] args) {
        //データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement spstmt = null;
        PreparedStatement ipstmt    =null;
        ResultSet rs = null;

        try {
            //1.ドライバのクラスをjava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

            //2.DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "12297iumzltoy");

            //4.DBとやり取りする窓口PreparedStatementオブジェクトの作成
            //検索用SQLおよび検索用PreparedStatementオブジェクトを取得
            String selectSql = "SELECT * FROM city where CountryCode = ?";
            spstmt = con.prepareStatement(selectSql);

            //更新するCountryCoodeを入力
            System.out.print("CountryCodeを入力してください＞");
            String str1 = keyIn();

            //入力されたCountryCodeをPreparedStatementオブジェクトにせっと
            spstmt.setString(1, str1);

            //5,6.Select文の実行と結果を格納・代入
            rs = spstmt.executeQuery();

            //7-1.更新前の結果を表示する
            System.out.println("更新前");
            while(rs.next()) {
                //Name列の値を取得
                String name = rs.getString("Name");
                //Country列の値を取得
                String countryCode = rs.getString("CountryCode");
                //District列の値を取得
                String district = rs.getString("District");
                //Population列の値を取得
                int population = rs.getInt("Population");
                //取得した値を表示
                System.out.println(name +"\t" + countryCode + "\t" + district + "\t" + population);
            }

            //7-2.更新処理を行う
            System.out.println("更新処理実行");

            //更新用SQLおよび更新用PreparedStatementオブジェクトを取得
            String insertSql = "INSERT INTO city (Name,CountryCode,District,Population) VALUES('rafah',?,'rafah',?)";
            ipstmt = con.prepareStatement(insertSql);

            //更新するpopulationを入力
            System.out.print("Populationを数字で入力してください>");
            int num1 = keyInNum();

         // 入力されたPopulationとCountryCodeをPreparedStatementオブジェクトにセット
            ipstmt.setString(1, str1);
            ipstmt.setInt(2, num1);

            //update処理の実行およびこうしんされた行数を取得
            int count = ipstmt.executeUpdate();
            System.out.println("更新行数:" + count);

            //7-3.更新後の結果を表示する
            rs.close();//更新後の検索のためいったん閉じる
            System.out.println("更新後");
            //検索の再実行と結果を格納/代入
            rs = spstmt.executeQuery();
            while(rs.next()) {
                //Name列の値を取得
                String name = rs.getString("Name");
                //CountryCode列の値を取得
                String countryCode = rs.getString("CountryCode");
                //District列の値を取得
                String district = rs.getString("District");
                int population = rs.getInt("Population");
                //取得した値を表示
                System.out.println(name + "\t" + countryCode + district + "\t" + population);
            }
        }catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            // 8. 接続を閉じる
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (ipstmt != null) {
                try {
                    ipstmt.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (spstmt != null) {
                try {
                    spstmt.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
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
        } catch (IOException e) {

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

