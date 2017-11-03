package com.laz.demo.velocity;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class Var {
	public static void main(String[] args) {
		simpleVelocity1();
	}

	private static void simpleVelocity1() {
		VelocitySimple velocitySimple = new VelocitySimple();
		VelocityContext vcContext = velocitySimple.createVelocityContext();
		// 使用控制台作为输出流
		BufferedWriter writer = velocitySimple.createBufferedWriter();
		Template template = velocitySimple.createTemplate("velocity.vm");
		template.merge(vcContext, writer);
		try {
			writer.flush();
			// 这里如果正式开发的话 需要将输出流关闭 但是因为这里选择是控制台 如果关闭的话 后面的测试方式的输出信息就没有了
			// writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
