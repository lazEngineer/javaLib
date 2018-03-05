package org.com.sunsheen.bigdata.hadoop.demo.mapreduce.demo3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

/**
 * 用户实体类
 * 在进程间传递对象或持久化对象的时候，就需要序列化对象成字节流，
 * 反之当要将接收到或从磁盘读取的字节流转换为对象，就要进行反序列化。
 * Writable是Hadoop的序列化格式，Hadoop定义了这样一个Writable接口。
 * 
 * 实现WritableComparable接口 实现，重写readFields()方法compareTo()方法和write()方法，分别是写入数据流以及读取数据流。
 */
public class UserEntity implements WritableComparable<UserEntity>{
	/** 序号 **/
	private int userId;
	/** 手机号码 **/
	private String mobile;
	/** 姓名 **/
	private String userName;
	/** 月收入 **/
	private double monthIncome;
	/** 家庭地址 **/
	private String address;
	/** 年收入**/
	private double yearIncome;

	/**
	 * 读取字段信息
	 */
	@Override
	public void readFields(DataInput in) throws IOException {
		this.userId = in.readInt();
		this.mobile = in.readUTF();
		this.userName = in.readUTF();
		this.monthIncome = in.readDouble();
		this.address = in.readUTF();
		this.yearIncome = in.readDouble();
	}

	/**
	 * 写入字段信息
	 */
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(userId);
		out.writeUTF(mobile);
		out.writeUTF(userName);
		out.writeDouble(monthIncome);
		out.writeUTF(address);
		out.writeDouble(yearIncome);
	}
	

	/**
	 * 无参默认构造函数
	 */
	public UserEntity() {
	}
	
	
	/**
	 * set 函数：用于对象属性赋值
	 * @param userId
	 * @param mobile
	 * @param userName
	 * @param monthIncome
	 * @param address
	 * @param yearIncome
	 */
	public void set(int userId, String mobile, String userName, double monthIncome, String address,
			double yearIncome) {
		
		this.userId = userId;
		this.mobile = mobile;
		this.userName = userName;
		this.monthIncome = monthIncome;
		this.address = address;
		this.yearIncome = yearIncome;
	}

	/**
	 * 实现compareTo()比较方法
	 * compareTo()当前对象与目标对象比较:
	 * 如果A>B，返回值为1，如果A=B，返回值为0;如果A<B，返回值为-1
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(UserEntity o) {
		// 年收入从高到低排序
		return this.yearIncome > o.yearIncome ? -1 : 1;
	}
	

	/**
	 * 重写对象的toString()方法
	 */
	@Override
	public String toString() {
		// 拼接显对象信息
		StringBuffer buffer = new StringBuffer();
		buffer.append(mobile).append("\t").append(userName).append("\t").append(monthIncome).append("\t").append(address).append("\t").append(yearIncome);
		return buffer.toString();
	}

	// getter setter方法
	public int getUserId() {
		return userId;
	}
	

	public void setUserId(int userId) {
		this.userId = userId;
	}
	

	public String getMobile() {
		return mobile;
	}
	

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	

	public String getUserName() {
		return userName;
	}
	

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	public double getMonthIncome() {
		return monthIncome;
	}
	

	public void setMonthIncome(double monthIncome) {
		this.monthIncome = monthIncome;
	}
	

	public String getAddress() {
		return address;
	}
	

	public void setAddress(String address) {
		this.address = address;
	}
	

	public double getYearIncome() {
		return yearIncome;
	}
	

	public void setYearIncome(double yearIncome) {
		this.yearIncome = yearIncome;
	}
}
