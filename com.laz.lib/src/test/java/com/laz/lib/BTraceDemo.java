package com.laz.lib;

import java.util.Random;

public class BTraceDemo {
	public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Random random = new Random();
        while (true) {
            System.out.println(calculator.add(random.nextInt(10), random.nextInt(10)));
        }
    }
}
