package org.com.sunsheen.bigdata.hadoop.test.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*  
 * 继承Mapper类需要定义四个输出、输出类型泛型： 
 * 四个泛型类型分别代表： 
 * KeyIn        Mapper的输入数据的Key，这里是每行文字的起始位置（0,11,...） 
 * ValueIn      Mapper的输入数据的Value，这里是每行文字 
 * KeyOut       Mapper的输出数据的Key，这里是序列化对象UserEntity 
 * ValueOut     Mapper的输出数据的Value，不返回任何值 
 *  
 * Writable接口是一个实现了序列化协议的序列化对象。 
 * 在Hadoop中定义一个结构化对象都要实现Writable接口，使得该结构化对象可以序列化为字节流，字节流也可以反序列化为结构化对象。 
 * LongWritable类型:Hadoop.io对Long类型的封装类型 
 */  
public class UserMapper extends Mapper<LongWritable, Text, UserEntity, NullWritable> {  
  
    private UserEntity userEntity = new UserEntity();  
      
    @Override  
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, UserEntity, NullWritable>.Context context)  
            throws IOException, InterruptedException {  
  
        //将每行的数据以空格切分数据，获得每个字段数据 1 135****9365 林*彬 2484 北京市昌平区北七家东三旗村  
        String[] fields = value.toString().split("\t");  
          
        // 赋值userEntity  
        userEntity.set(Integer.parseInt(fields[0]), fields[1], fields[2],Double.parseDouble(fields[3]), fields[4],0.00);  
          
        // 将对象序列化  
        context.write(userEntity,NullWritable.get());  
    }  

}
