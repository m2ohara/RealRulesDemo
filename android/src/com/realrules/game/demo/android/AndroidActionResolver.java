package com.realrules.game.demo.android;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;

import com.realrules.data.IActionResolver;

public class AndroidActionResolver implements IActionResolver {

    Handler uiThread;
    Context appContext;
    static String dbPath;
    static String dbName = "GameAppDB.sqlite";

    public AndroidActionResolver(Context appContext) {
            uiThread = new Handler();
            this.appContext = appContext;
            dbPath = appContext.getFilesDir().getAbsolutePath().replace("files", "databases")+File.separator + dbName;
            try {
				new DataBaseHelper(appContext).createDataBase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }

    @Override
    public Connection getConnection() {
            String url = "jdbc:sqlite:"+dbPath;
            try {
                    Class.forName("org.sqlite.JDBC");
                    return DriverManager.getConnection(url);
//            } catch (InstantiationException e) {
//                    Log.e("sql", e.getMessage());
//            } catch (IllegalAccessException e) {
//                    Log.e("sql", e.getMessage());
            } catch (ClassNotFoundException e) {
                    Log.e("sql", e.getMessage());
            } catch (SQLException e) {
                    Log.e("sql", e.getMessage());
            }
            return null;
    }
    
    
    class DataBaseHelper extends SQLiteOpenHelper{
   	 
        //The Android's default system path of your application database.
        private String DB_PATH;
     
        private static final String DB_NAME = "GameAppDB.sqlite";
     
        private SQLiteDatabase myDataBase; 
     
        private final Context appContext;
     
        /**
         * Constructor
         * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
         * @param context
         */
        public DataBaseHelper(Context context) {
     
        	super(context, DB_NAME, null, 1);
            this.appContext = context;
            this.DB_PATH = appContext.getFilesDir().getAbsolutePath().replace("files", "databases")+File.separator;
        }	
     
      /**
         * Creates a empty database on the system and rewrites it with your own database.
         * */
        public void createDataBase() throws IOException{
     
        	boolean dbExist = checkDataBase();
     
        	if(dbExist){
        		//do nothing - database already exist
        	}else{
     
        		//By calling this method and empty database will be created into the default system path
                   //of your application so we are gonna be able to overwrite that database with our database.
            	this.getReadableDatabase();
     
            	try {
     
        			copyDataBase();
     
        		} catch (IOException e) {
     
            		throw new Error("Error copying database");
     
            	}
        	}
     
        }
     
        /**
         * Check if the database already exist to avoid re-copying the file each time you open the application.
         * @return true if it exists, false if it doesn't
         */
        private boolean checkDataBase(){
     
        	SQLiteDatabase checkDB = null;
     
        	try{
        		String myPath = DB_PATH + DB_NAME;
        		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
     
        	}catch(SQLiteException e){
     
        		//database does't exist yet.
     
        	}
     
        	if(checkDB != null){
     
        		checkDB.close();
     
        	}
     
        	return checkDB != null ? true : false;
        }
     
        /**
         * Copies your database from your local assets-folder to the just created empty database in the
         * system folder, from where it can be accessed and handled.
         * This is done by transfering bytestream.
         * */
        private void copyDataBase() throws IOException{
     
        	//Open your local db as the input stream
        	InputStream myInput = appContext.getAssets().open(DB_NAME);
     
        	// Path to the just created empty db
        	String outFileName = DB_PATH + DB_NAME;
     
        	//Open the empty db as the output stream
        	OutputStream myOutput = new FileOutputStream(outFileName);
     
        	//transfer bytes from the inputfile to the outputfile
        	byte[] buffer = new byte[1024];
        	int length;
        	while ((length = myInput.read(buffer))>0){
        		myOutput.write(buffer, 0, length);
        	}
     
        	//Close the streams
        	myOutput.flush();
        	myOutput.close();
        	myInput.close();
     
        }
     
        public void openDataBase() throws SQLException{
     
        	//Open the database
            String myPath = DB_PATH + DB_NAME;
        	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
     
        }
     
        @Override
    	public synchronized void close() {
     
        	    if(myDataBase != null)
        		    myDataBase.close();
     
        	    super.close();
     
    	}
     
    	@Override
    	public void onCreate(SQLiteDatabase db) {
     
    	}
     
    	@Override
    	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     
    	}
     
            // Add your public helper methods to access and get content from the database.
           // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
           // to you to create adapters for your views.
     
    }
}
