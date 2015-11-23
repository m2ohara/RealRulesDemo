package com.realrules.game.demo.android;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.realrules.data.IActionResolver;

public class AndroidActionResolver implements IActionResolver {

    Handler uiThread;
    Context appContext;
    String dbPath;
    static String dbName = "GameAppDB.db";

    public AndroidActionResolver(Context appContext) {
            uiThread = new Handler();
            this.appContext = appContext;
            dbPath = appContext.getFilesDir().getAbsolutePath().replace("files", "databases")+File.separator + dbName;
    }

    @Override
    public Connection getConnection() {
            String url = "jdbc:sqldroid:"+dbPath;
            try {
                    Class.forName("org.sqldroid.SQLDroidDriver").newInstance();
                    return DriverManager.getConnection(url);
            } catch (InstantiationException e) {
                    Log.e("sql", e.getMessage());
            } catch (IllegalAccessException e) {
                    Log.e("sql", e.getMessage());
            } catch (ClassNotFoundException e) {
                    Log.e("sql", e.getMessage());
            } catch (SQLException e) {
                    Log.e("sql", e.getMessage());
            }
            return null;
    }

}
