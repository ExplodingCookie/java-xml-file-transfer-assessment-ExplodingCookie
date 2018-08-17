package com.cooksys.java_file_transfer_assessement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageSender {
	
	@XmlAttribute(name="name")
	private String personName;
	
	@XmlAttribute(name="date")
	private String date;
	
	@XmlAttribute(name="filename")
	private String filename;
	
	@XmlElement(name="bytes")
	private byte[] bytes;
	
	public MessageSender () {}
	
	public MessageSender (String personName, int year, int month, int day, String filename, byte[] bytes) {
		this.personName = personName;
		this.filename = filename;
		this.bytes = bytes;
		this.date = year + "-" + formatMonthDay(month) + "-" + formatMonthDay(day);
	}
	
	public String formatMonthDay (int input) {
		String start = "";
		if(input < 10) {
			start = "0";
		}
		
		return start + input;
	}
	
	@Override
	public String toString () {
		return "Sender [" + personName + ", " + date + ", " + filename + "]";
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
