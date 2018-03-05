package org.com.sunsheen.bigdata.avro.demo;

import java.io.IOException;

import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class AvroMapper<K> extends
		Mapper<AvroKey<K>, NullWritable, AvroKey<K>, AvroValue<K>> {

	@Override
	protected void map(AvroKey<K> key, NullWritable value, Context context)
			throws IOException, InterruptedException {
		context.write(key, new AvroValue<K>(key.datum()));
	}

}
