package com.laz.lib.serialization;

import io.protostuff.Tag;

import java.io.Serializable;

public class Person implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
	private Child child;
	
	public void setChild(Child child) {
		this.child = child;
	}
	
	public Child getChild() {
		return child;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
}
