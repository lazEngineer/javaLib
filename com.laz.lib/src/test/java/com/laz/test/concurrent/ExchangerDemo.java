package com.laz.test.concurrent;

import java.util.concurrent.Exchanger;

public class ExchangerDemo {
	public static void main(String[] args) {
		Exchanger exchanger = new Exchanger();

		ExchangerRunnable exchangerRunnable1 = new ExchangerRunnable(exchanger,
				"Acc");

		ExchangerRunnable exchangerRunnable2 = new ExchangerRunnable(exchanger,
				"Bcc");
		ExchangerRunnable exchangerRunnable3 = new ExchangerRunnable(exchanger,
				"Ccc");
		ExchangerRunnable exchangerRunnable4 = new ExchangerRunnable(exchanger,
				"Dcc");

		new Thread(exchangerRunnable1).start();
		new Thread(exchangerRunnable2).start();
		new Thread(exchangerRunnable3).start();
		new Thread(exchangerRunnable4).start();
	}
}

class ExchangerRunnable implements Runnable {

	Exchanger exchanger = null;
	Object object = null;

	public ExchangerRunnable(Exchanger exchanger, Object object) {
		this.exchanger = exchanger;
		this.object = object;
	}

	public void run() {
		try {
			Object previous = this.object;

			this.object = this.exchanger.exchange(this.object);

			System.out.println(Thread.currentThread().getName() + " exchanged "
					+ previous + " for " + this.object);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}