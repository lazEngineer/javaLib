package com.laz.lib;


import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class BTraceTest {
	private static long count;
    static{
        println("---------------------------JVM properties:---------------------------");
   
    }

    @OnMethod(
            clazz = "com.laz.lib.Calculator",
            method = "add",
            location = @Location(Kind.RETURN)
    )
    public static void trace1(int a, int b, @Return int sum) {
        println("trace1:a=" + a + ",b=" + b + ",sum=" + sum);
    }

}
