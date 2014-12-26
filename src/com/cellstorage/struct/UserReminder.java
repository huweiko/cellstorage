package com.cellstorage.struct;
public class UserReminder{
	private String Msg_Reminder_Id;
	private boolean Msg_Is_Show;
	private String Msg_Content;
	public String getMsg_Reminder_Id() {
		return Msg_Reminder_Id;
	}
	public void setMsg_Reminder_Id(String msg_Reminder_Id) {
		Msg_Reminder_Id = msg_Reminder_Id;
	}
	public boolean isMsg_Is_Show() {
		return Msg_Is_Show;
	}
	public void setMsg_Is_Show(boolean msg_Is_Show) {
		Msg_Is_Show = msg_Is_Show;
	}
	public String getMsg_Content() {
		return Msg_Content;
	}
	public void setMsg_Content(String msg_Content) {
		Msg_Content = msg_Content;
	}
	public UserReminder(String msg_Reminder_Id,boolean msg_Is_Show,String msg_Content){
		Msg_Reminder_Id = msg_Reminder_Id;
		Msg_Is_Show = msg_Is_Show;
		Msg_Content = msg_Content;
	}
}