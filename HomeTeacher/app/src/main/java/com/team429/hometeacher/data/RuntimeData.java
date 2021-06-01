package com.team429.hometeacher.data;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.team429.hometeacher.data.contracts.HistoryContract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RuntimeData {

    private static final String FIRST_RUN = "first_run";
    private static final String DIFFICULTY = "difficulty";
    private static final String TARGET = "target";
    private static Connection conn = null;
    private static PreparedStatement ps=null;
    private static ResultSet rs=null;//查询结果

    private RuntimeData(){ };
    public static void insert(String question,String answer, int err_times){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                conn = DBOpenHelper.getConn();
                String sql = "INSERT INTO error_book (date,question,answer,err_times) VALUES(?,?,?,?);";
                try {
                    ps =(PreparedStatement) conn.prepareStatement(sql);
                    ps.setString(1, getDateString());
                    ps.setString(2,question);
                    ps.setString(3,answer);
                    ps.setInt(4,err_times);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }});
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void Update(String property,int i,String date){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                conn = DBOpenHelper.getConn();
                String sql1 = "update history set " + property + " = ? where date = ?;";
                try {
                    ps = (PreparedStatement) conn.prepareStatement(sql1);
                    ps.setString(1, String.valueOf(i));
                    ps.setString(2, getDateString());
                    ps.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }});
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public static void Init(@NonNull Context context){

        DateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        preferences = context.getSharedPreferences("home_teacher", Context.MODE_PRIVATE);
        editor = preferences.edit();

        // 初始化
        FirstRun = new MutableLiveData<>();
        Difficulty = new MutableLiveData<>();
        Target = new MutableLiveData<>();
        Done = new MutableLiveData<>();
        Correct = new MutableLiveData<>();

        FirstRun.setValue(preferences.getBoolean(FIRST_RUN, false));
        Difficulty.setValue(preferences.getInt(DIFFICULTY, 1));

        // try initialize today info
        ResultSet today = null;
        try {
            today = getHistoryToday();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       // 从数据库读数据
        try {
            Target.setValue(today.getInt("target_num"));
            Done.setValue(today.getInt("done_num"));
            Correct.setValue(today.getInt("correct_num"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // 绑定事件
        Difficulty.observeForever(i -> {
            editor.putInt(DIFFICULTY, i);
            editor.apply();
        });
        Target.observeForever(i -> {
            editor.putInt(TARGET, i);
            editor.apply();
            Update(HistoryContract.HistoryEntry.COLUMN_TARGET_NUM,i,getDateString());
        });
        Done.observeForever(i -> {
            Update(HistoryContract.HistoryEntry.COLUMN_DONE_NUM,i,getDateString());
        });
        Correct.observeForever(i -> {
            Update(HistoryContract.HistoryEntry.COLUMN_CORRECT_NUM,i,getDateString());
        });
        if (!FirstRun.getValue()) {
            editor.putBoolean(FIRST_RUN, true);
            editor.apply();
        }
    }
    public static ResultSet getHistoryToday() throws InterruptedException{
        Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    conn = DBOpenHelper.getConn();
                    String sql1 = "select * from history where date=?";
                    try {
                        ps =(PreparedStatement) conn.prepareStatement(sql1);
                        ps.setString(1,getDateString());
                        rs= ps.executeQuery();
                       if(!rs.next()){
                            String sql2 = "INSERT INTO history (date,correct_num,done_num,target_num) VALUES(?,?,?,?);";
                            ps =(PreparedStatement) conn.prepareStatement(sql2);
                            ps.setString(1, getDateString());
                            ps.setInt(2,0);
                            ps.setInt(3,0);
                            ps.setInt(4,30);
                            ps.executeUpdate();
                            ps =(PreparedStatement) conn.prepareStatement(sql1);
                            ps.setString(1,getDateString());
                            rs= ps.executeQuery();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }});
            t1.start();
            t1.join();
        ResultSet today = rs;
        return today;
    }
    public static String getDateString(){
        return DateFormat.format(new Date(System.currentTimeMillis()));
    }
    public static MutableLiveData<Boolean> FirstRun ;
    public static MutableLiveData<Integer> Difficulty;
    public static MutableLiveData<Integer> Target;
    public static MutableLiveData<Integer> Done;
    public static MutableLiveData<Integer> Correct;
    public static SimpleDateFormat DateFormat;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
}
