package com.laz.lib.btrace;

import static com.sun.btrace.BTraceUtils.println;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Return;

@BTrace
public class EclipseBtrace {
	private static long count;
    static{
        println("---------------------------JVM properties:---------------------------");
   
    }

    @OnMethod(
            clazz = "com.sunsheen.jfids.studio.chart.dialog.ChartDialogUtils",
            method = "showDialog",
            location = @Location(Kind.CALL)
    )
    public static void trace1(Object a, Object b) {
        println("trace1:a=" + a + ",b=" + b );
    }
}
