package org.com.sunsheen.bigdata.avro.demo;

import java.io.IOException;

import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class AvroReducer<K> extends
		Reducer<AvroKey<K>, AvroValue<K>, AvroKey<K>, NullWritable> {

	@Override
	protected void reduce(AvroKey<K> key, Iterable<AvroValue<K>> values,
			Context context) throws IOException, InterruptedException {
		for (AvroValue<K> val : values) {
			context.write(new AvroKey<K>(val.datum()), NullWritable.get());
		}
	}

}
