package com.cellstorage.db;

import com.cellstorage.struct.UserReminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBtableReminderItem extends DBHelper {
	private final static String TABLE_NAME="device_reminder_name";//表名
	
	private final static String USER_ID="user_id";//用户id
	private final static String REMINDER_ID="reminder_id";//提醒id
	private final static String REMINDER_MSG_IS_NEW="reminder_msg_is_new";//是否是新消息
	private final static String REMINDER_CONTENT="reminder_content";//提醒内容
	
	public DBtableReminderItem(Context context)
	{
		super(context);
	}
	public void createDBtable(){
		SQLiteDatabase db=this.getWritableDatabase(); 
		String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+TABLE_NAME+"';";
		Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToNext()){
            int count = cursor.getInt(0);
            if(count>0){

            }else{
            	String sql0="Create table "+TABLE_NAME+"("+
            			USER_ID+" TEXT,"+
            			REMINDER_ID+" TEXT,"+
            			REMINDER_MSG_IS_NEW+" INTEGER,"+
            			REMINDER_CONTENT+" TEXT,primary key(USER_ID,REMINDER_ID))";
        		super.createDBtable(sql0);
            }
         }
        if(cursor != null){
        	cursor.close();
        }

	}

	public Cursor select()
	{
		return super.select(TABLE_NAME);
	}
	public Cursor selectByAttribute(String userId)
	{
		return super.selectByAttribute(TABLE_NAME,USER_ID,userId);
	}
	
	public long insert(UserReminder mUserReminder)
	{
		ContentValues cv=new ContentValues(); 
		cv.put(USER_ID, mUserReminder.getMsg_User_Id());
		cv.put(REMINDER_ID, mUserReminder.getMsg_Reminder_Id());
		cv.put(REMINDER_MSG_IS_NEW, mUserReminder.getMsg_Is_New());
		cv.put(REMINDER_CONTENT, mUserReminder.getMsg_Content());
		
		return super.insert(TABLE_NAME, cv);
	}
	
	public void delete(String userId,String reminderId)
	{
		String [] whereValue = {userId,reminderId};
		String where = USER_ID + "=? and " + REMINDER_ID+"=?";
		super.delete(TABLE_NAME, where, whereValue);
	}
	
	public void update(UserReminder mUserReminder,String userId,String reminderId)
	{
		String [] whereValue = {userId,reminderId};
		String where = USER_ID + "=? and " + REMINDER_ID+"=?";
		ContentValues cv=new ContentValues(); 
		cv.put(USER_ID, mUserReminder.getMsg_User_Id());
		cv.put(REMINDER_ID, mUserReminder.getMsg_Reminder_Id());
		cv.put(REMINDER_MSG_IS_NEW, mUserReminder.getMsg_Is_New());
		cv.put(REMINDER_CONTENT, mUserReminder.getMsg_Content());
		super.update(TABLE_NAME, where, whereValue, cv);
	}
}
