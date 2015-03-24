package com.cellstorage.struct;
public class UserReminder{
	private String Msg_User_Id;
	private String Msg_Reminder_Id;
	private int Msg_Is_New;
	private String Msg_Content;
	public String getMsg_Reminder_Id() {
		return Msg_Reminder_Id;
	}
	public void setMsg_Reminder_Id(String msg_Reminder_Id) {
		Msg_Reminder_Id = msg_Reminder_Id;
	}
	public int getMsg_Is_New() {
		return Msg_Is_New;
	}
	public void setMsg_Is_New(int msg_Is_New) {
		Msg_Is_New = msg_Is_New;
	}
	public String getMsg_Content() {
		return Msg_Content;
	}
	public void setMsg_Content(String msg_Content) {
		Msg_Content = msg_Content;
	}
	public UserReminder(String msg_User_Id,String msg_Reminder_Id,int msg_Is_New,String msg_Content){
		Msg_User_Id = msg_User_Id;
		Msg_Reminder_Id = msg_Reminder_Id;
		Msg_Is_New = msg_Is_New;
		Msg_Content = msg_Content;
	}
	public String getMsg_User_Id() {
		return Msg_User_Id;
	}
	public void setMsg_User_Id(String msg_User_Id) {
		Msg_User_Id = msg_User_Id;
	}
}