package com.team429.hometeacher.data;
import java.sql.*;
public class DBOpenHelper {

    private static String driver = "com.mysql.jdbc.Driver";//MySQL 驱动
    private static String url ="jdbc:mysql://192.168.43.48:3306/hometeach?serverTimezone=UTC&useSSL=false";
    private static String user = "root";//用户名
    private static String password = "778899";//密码
    private static Connection conn = null;

    /**
     * 连接数据库
     * */

    public static Connection getConn(){
        if(conn != null)
            return conn;

        //Connection接口代表Java程序和数据库的连接对象，只有获得该连接对象后，才能访问数据库，并操作数据表
        try {
            Class.forName("com.mysql.jdbc.Driver");//加载MySQL数据库驱动
        }catch(java.lang.ClassNotFoundException e) {//如果找不到这个类，执行下面的异常处理
            System.out.println("驱动程序配置未配置成功!!!");
        }
        try {
            conn= (Connection) DriverManager.getConnection(url,user,password);//建立和数据库的连接，并返回表示连接的Connection对象
            System.out.println("数据库连接成功!!!");
        }catch(Exception e) {//未连接成功，执行下面的异常处理
            System.out.println("数据库连接失败!!!");
        }
        return conn;
    }

    /**
     * 关闭数据库
     * */

    public static void closeAll(Connection conn, PreparedStatement ps){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 关闭数据库
     * */

    public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
