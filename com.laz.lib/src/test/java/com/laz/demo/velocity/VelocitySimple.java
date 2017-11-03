package com.laz.demo.velocity;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class VelocitySimple {
	public VelocitySimple() {
		// 如果不修改velocity的资源查找路径的话 即要将模板文件放在项目的目录下 即和src属于一个目录级别
		changeVelocityResourceLoaderPath();
	}

	// 改变Velocity查找资源的路径
	private void changeVelocityResourceLoaderPath() {
		// 设定查找资源的路径为类加载器的路径 即src下面
		String pathResources = VelocitySimple.class.getClassLoader()
				.getResource("./").getPath();
		VelocityEngine velocityEngine = new VelocityEngine();
		Properties properties = new Properties();
		properties.setProperty(velocityEngine.FILE_RESOURCE_LOADER_PATH,
				pathResources);
		// 使用默认配置和自己定义的配置文件初始化velocity引擎
		Velocity.init(properties);
	}

	// 1.创建上下文 填充数据
	public VelocityContext createVelocityContext() {
		VelocityContext vcContext = new VelocityContext();
		// 填充一个listData列表和className变量的数据
		vcContext.put("listData", getListData());
		vcContext.put("className", VelocitySimple.class.getName());
		return vcContext;
	}

	// 2.根据外部摸板 创建模板对象
	public Template createTemplate(String pathTemplate) {
		Template template = null;
		// 此时的资源查找 即是在changeVelocityResourceLoaderPath()设置的路径下
		template = Velocity.getTemplate(pathTemplate, "utf-8");
		return template;
	}

	// 3.创建输出流 即将模板合并后输出到哪个地方
	public BufferedWriter createBufferedWriter() {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				System.out));
		return writer;
	}

	// 获取封装数据
	private ArrayList getListData() {
		// TODO Auto-generated method stub
		ArrayList<String> alArrayList = new ArrayList<String>();
		alArrayList.add("java语言");
		alArrayList.add("c语言");
		alArrayList.add("c++语言");
		alArrayList.add("c#语言");
		return alArrayList;
	}
}
