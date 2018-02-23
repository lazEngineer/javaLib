package org.com.sunsheen.bigdata.hadoop.test.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

/* 
 * Reducer需要定义四个输出、输出类型泛型： 
 * 四个泛型类型分别代表： 
 * KeyIn        Reducer的输入数据的Key，这里是序列化对象UserEntity 
 * ValueIn      Reducer的输入数据的Value，这里是NullWritable 
 * KeyOut       Reducer的输出数据的Key，这里是序列化对象UserEntity 
 * ValueOut     Reducer的输出数据的Value，NullWritable 
 */  
public class UserReducer extends Reducer<UserEntity, NullWritable, UserEntity, NullWritable>{  
  
    @Override  
    protected void reduce(UserEntity userEntity, Iterable<NullWritable> values,  
            Reducer<UserEntity, NullWritable, UserEntity, NullWritable>.Context context)  
                    throws IOException, InterruptedException {  
          
            // 年收入 = 月收入 * 12  四舍五入  
            String yearIncome = String.format("%.2f", userEntity.getMonthIncome() * 12);  
            userEntity.setYearIncome(Double.parseDouble(yearIncome));  
            context.write(userEntity, NullWritable.get());  
    }  

}
