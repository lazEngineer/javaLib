package com.laz.lib.serialization.kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;





import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryo.serializers.MapSerializer;
import com.laz.lib.serialization.Person;

public class KryoTest {
	public static void main(String[] args) throws FileNotFoundException {
		Kryo kryo = new Kryo();
		Output output = null;
		output = new Output(new FileOutputStream("d:/1.txt"));
		Person p = new Person();
		p.setName("2222");
		kryo.writeObject(output, p);
		output.close();
	}
}