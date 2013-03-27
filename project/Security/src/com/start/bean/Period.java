package com.start.bean;

/**
 * 时间段
 * @author start
 *
 */
public class Period extends Base {

	private static final long serialVersionUID = 1L;
	
	public static final String format="HH:mm";
	
	/**
	 * 开始时间段格式：(07:00)
	 */
	private String start;
	/**
	 * 结束时间段格式：(09:00)
	 */
	private String end;
	/**
	 * 警报声音声音文件的路径
	 */
	private String sound;
	/**
	 * 哪几天重复星期一至星期日格式：1;2;3;4;5;6;7
	 */
	private String repeat;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 状态true:开启 false:关闭
	 */
	private Boolean status=false;
	
	public String getStart() {
		return start;
	}
	
	public void setStart(String start) {
		this.start = start;
	}
	
	public String getEnd() {
		return end;
	}
	
	public void setEnd(String end) {
		this.end = end;
	}
	
	public String getSound() {
		return sound;
	}
	
	public void setSound(String sound) {
		this.sound = sound;
	}
	
	public String getRepeat() {
		return repeat;
	}
	
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Boolean getStatus() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}
