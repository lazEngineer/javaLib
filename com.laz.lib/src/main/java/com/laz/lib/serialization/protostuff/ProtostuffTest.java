package com.laz.lib.serialization.protostuff;

import io.protostuff.JsonIOUtil;
import io.protostuff.JsonOutput;
import io.protostuff.JsonXIOUtil;
import io.protostuff.JsonXOutput;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.XmlIOUtil;
import io.protostuff.XmlXIOUtil;
import io.protostuff.runtime.RuntimeSchema;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.laz.lib.serialization.Child;
import com.laz.lib.serialization.Person;

public class ProtostuffTest {
	public static void main(String[] args) {
		Person p = new Person();
		p.setName("2222");
		Child d = new Child("sddddddddddddddddddddddddddddddddddddddddddddddddddddddddddds");
		StringBuffer sb = new StringBuffer();
		for (int i = 0 ;i<1000;i++) {
			sb.append(i+"a");
		}
		d.setName(sb.toString());
		p.setChild(d);

		byte[] b = serializerXml(p);
		System.out.println(new String(b));
		Person p2 = deserializeXml(b,Person.class);
		System.out.println(p2.getName());
		System.out.println(p2.getChild().getName());
		// byte[] b1 = serializer(p);
//		// System.out.println(new String(b1));
//		Schema schema = RuntimeSchema.getSchema(Person.class);
//		byte[] b = toByteArray(p,1024);
//		System.out.println(format(new String(b)));
//		Person p2 = new Person();
//		try {
//			mergeFrom(b,1024, p2);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(p2.getName());

		// byte[] bXml = serializerXml(p);
		// System.out.println(new String(bXml));
		
		List<String> a = new ArrayList<String>();
		a.add("1");
		a.add("2");
		for (String str:a) {
			if ("1".equals(str))
				a.remove(str);
		}
		System.out.println(a);
	}
	protected static <T> void mergeFrom(byte[] data, int size, T message)
			throws IOException {
		Schema schema = RuntimeSchema.getSchema(message.getClass());
		ByteArrayInputStream in = new ByteArrayInputStream(data, 0, size);
		JsonIOUtil.mergeFrom(in, message, schema, false,
				LinkedBuffer.allocate(size));
	}

	protected static <T> byte[] toByteArray(T message, int size) {
		final LinkedBuffer buffer = LinkedBuffer.allocate(size);
		try {
			Schema schema = RuntimeSchema.getSchema(message.getClass());
			return JsonXIOUtil.toByteArray(message, schema, false, buffer);
		} finally {
			buffer.clear();
		}
	}
	
	public static <T> T deserializeXml(byte[] data, Class<T> cls) {
		try {
			Schema schema = RuntimeSchema.getSchema(cls);
			T c = cls.newInstance();
			ByteArrayInputStream in = new ByteArrayInputStream(data, 0,
					data.length);
			XmlIOUtil.mergeFrom(in, c, schema);
			return c;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}


	public static <T> T deserializeJson(byte[] data, Class<T> cls) {
		try {
			Schema schema = RuntimeSchema.getSchema(cls.getClass());
			T c = cls.newInstance();
			ByteArrayInputStream in = new ByteArrayInputStream(data, 0,
					data.length + 1);
			JsonIOUtil.mergeFrom(in, c, schema, false);
			return c;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public static <T> byte[] serializer(T o) {
		Schema schema = RuntimeSchema.getSchema(o.getClass());
		return ProtostuffIOUtil.toByteArray(o, schema,
				LinkedBuffer.allocate(256));
	}

	public static <T> byte[] serializerJson(T o) {
		Schema schema = RuntimeSchema.getSchema(o.getClass());
		return JsonIOUtil.toByteArray(o, schema, false,
				LinkedBuffer.allocate(2560));
	}

	public static <T> byte[] serializerXml(T o) {
		Schema schema = RuntimeSchema.getSchema(o.getClass());
		byte[] xml = XmlIOUtil.toByteArray(o, schema);
		return xml;
	}

	/**
	 * 得到格式化json数据 退格用\t 换行用\r
	 */
	public static String format(String jsonStr) {
		int level = 0;
		StringBuffer jsonForMatStr = new StringBuffer();
		for (int i = 0; i < jsonStr.length(); i++) {
			char c = jsonStr.charAt(i);
			if (level > 0
					&& '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
				jsonForMatStr.append(getLevelStr(level));
			}
			switch (c) {
			case '{':
			case '[':
				jsonForMatStr.append(c + "\n");
				level++;
				break;
			case ',':
				jsonForMatStr.append(c + "\n");
				break;
			case '}':
			case ']':
				jsonForMatStr.append("\n");
				level--;
				jsonForMatStr.append(getLevelStr(level));
				jsonForMatStr.append(c);
				break;
			default:
				jsonForMatStr.append(c);
				break;
			}
		}

		return jsonForMatStr.toString();

	}

	private static String getLevelStr(int level) {
		StringBuffer levelStr = new StringBuffer();
		for (int levelI = 0; levelI < level; levelI++) {
			levelStr.append("\t");
		}
		return levelStr.toString();
	}

	public static <T> T deserializer(byte[] bytes, Class<T> clazz) {

		T obj = null;
		try {
			obj = clazz.newInstance();
			Schema schema = RuntimeSchema.getSchema(obj.getClass());
			ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return obj;
	}

}
