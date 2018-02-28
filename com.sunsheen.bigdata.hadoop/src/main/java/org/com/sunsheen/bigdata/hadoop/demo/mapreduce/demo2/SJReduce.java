package org.com.sunsheen.bigdata.hadoop.demo.mapreduce.demo2;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class SJReduce extends Reducer<Text, Text, Text, Text> {  
    public static int time = 0;
	/** 
     * 重写reduce方法 
     */  
    @Override  
    protected void reduce(Text key, Iterable<Text> values,  
            Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {  
        if (time == 0) {
        	context.write(new Text("grandchild"), new Text("grandparent"));
        	time++;
        }
        int grandchildNum=0;
        String grandchild[] = new String[10];
        int grandparentNum = 0;
        String grandparent[] = new String[10];
        Iterator ite = values.iterator();
        
        System.out.print(key+":");
        while (ite.hasNext()) {
        	String record = ite.next().toString();
        	System.out.print(record+" ");
        	
        	int len = record.length();
        	int i = 2;
        	if (len == 0) {
        		continue;
        	}
        	char relationType = record.charAt(0);
        	String childName = new String();
        	String parentName = new String();
        	//获取value-list 中的value的child
        	while (record.charAt(i) != '+'){
        		childName = childName +record.charAt(i);
        		i++;
        	}
        	i=i+1;
        	//获取value-list 中value的parent
        	while (i<len) {
        		parentName = parentName+record.charAt(i);
        		i++;
        	}
        	//左表，取出child放入grandchild
        	if (relationType=='1'){
        		grandchild[grandchildNum] = childName;
        		grandchildNum++;
        	}else {
        		//右表，取出parent放入grandparent
        		grandparent[grandparentNum] = parentName;
        		grandparentNum++;
        	}
        }
        System.out.println();
        //grandchild和grandparent数组求笛卡尔积
        if (grandchildNum!=0 && grandparentNum!=0) {
        	for (int m=0;m<grandchildNum;m++){
        		for (int n=0;n<grandparentNum;n++) {
        			context.write(new Text(grandchild[m]), new Text(grandparent[n]));
        		}
        	}
        }
    }  

}
