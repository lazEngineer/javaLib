package com.laz.demo.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMessage;

public class JMSConsumerTopic {
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;// 默认连接用户名
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;// 默认连接密码
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;// 默认连接地址

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;// 连接工厂
		Connection connection = null;// 连接

		Session session;// 会话 接受或者发送消息的线程
		Destination destination;// 消息的目的地

		MessageConsumer messageConsumer;// 消息的消费者

		// 实例化连接工厂
		connectionFactory = new ActiveMQConnectionFactory(
				JMSConsumerTopic.USERNAME, JMSConsumerTopic.PASSWORD,
				JMSConsumerTopic.BROKEURL);
String[] array = new String[]{"a","b"};
		try {
			// 通过连接工厂获取连接
			connection = connectionFactory.createConnection();
			// 启动连接
			connection.start();
			// 创建session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// 创建一个连接HelloWorld的消息队列
			destination = session
					.createTopic("example.MyTopic");
			// 创建消息消费者
			messageConsumer = session.createConsumer(destination);

			while (true) {
				 Message msg = messageConsumer
					.receive(100000);
				if (msg instanceof ActiveMQMessage) {
					ActiveMQMessage textMessage = (ActiveMQMessage) messageConsumer
							.receive(100000);
					if (textMessage != null) {
						System.out.println("收到的消息:" + textMessage.toString());
					} else {
						break;
					}
				}
				if (msg instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) messageConsumer
							.receive(100000);
					if (textMessage != null) {
						System.out.println("收到的消息:" + textMessage.getText());
					} else {
						break;
					}
				}

			}

		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
}
