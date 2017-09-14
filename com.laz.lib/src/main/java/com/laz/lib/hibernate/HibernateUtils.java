package com.laz.lib.hibernate;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;


public class HibernateUtils {
	private static SessionFactory factory;
	static {
		getSessionFactory();
	}

	// 获得开启着的Session
	public static Session getSession() {
		if (factory == null) {
			getSessionFactory();
		}
		return factory.openSession();
	}

	// 关闭Session
	public static void closeSession(Session session) {
		if (session != null) {
			if (session.isOpen()) {
				session.close();
			}
		}
	}

	public static SessionFactory getSessionFactory() {
		try {
			// 读取hibernate.cfg.xml文件
			Configuration cfg = new Configuration().configure(HibernateUtils.class.getResource("hibernate.cfg.xml"));
			// 建立SessionFactory
			factory = cfg.buildSessionFactory();

		} catch (Exception e) {
			factory = null;
			e.printStackTrace();
		}
		return factory;
	}



	public static List<Map<String, Object>> queryList(Session session,
			String sql, Map<String, Object> params) {
		SQLQuery query = session.createSQLQuery(sql);
		for(String key:params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		System.out.println("参数："+params+" sql:"+query.getQueryString());
		List<Map<String, Object>> list = query.list();
		return list;
	}

	public static int update(org.hibernate.Session session, String sql,
			Map<String, Object> params) {
		session.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		for(String key:params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		int ok =  query.executeUpdate();
		session.getTransaction().commit();
		return ok;
	}
	public static int delete(org.hibernate.Session session, String sql,
			Map<String, Object> params) {
		session.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		for(String key:params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		int ok =  query.executeUpdate();
		session.getTransaction().commit();
		return ok;
	}
	
	public static int insert(org.hibernate.Session session, String sql,
			Map<String, Object> params) {
		session.beginTransaction();
		Query query = session.createSQLQuery(sql);
		for(String key:params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		int ok =  query.executeUpdate();
		session.getTransaction().commit();
		return ok;
	}

	public static String getSql(String sqlid) {
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		map.put("WebSocket.test",
				"select * from book22 where id=:id");
		map.put("WebSocket.tMsgInsert",
				"insert into   t_msg(id,last_msg,time,info_times,is_top,from_user_id,top_time,to_user_id) values('$P.id','$P.last_msg','$P.time','$P.info_times','$P.is_top','$P.from_user_id','$P.top_time','$P.to_user_id')");
		
		
		map.put("WebSocket.selectUser",
				"select head_img,name from  ts_user_detail where userid='$P.userId'");
		map.put("WebSocket.tMsgUpdate",
				"update t_msg set last_msg='$P.last_msg',time='$P.time',info_times='$P.info_times' where id='$P.id'");
		map.put("WebSocket.tMsgChatFlagUpdate",
				"update t_msg set chat='$P.chat' where from_user_id='$P.fromUserId' and to_user_id='$P.toUserId'"); //更新互聊标志
		map.put("WebSocket.selectMsgList2",
				"select * from t_msg where to_user_id='$P.toId' and from_user_id='$P.fromId' limit 0,1");
		map.put("WebSocket.insertChatInfo","insert into t_chat_msg(id,to_userid,data,from_userid,date) values('$P.id','$P.to_userid','$P.data','$P.from_userid','$P.date') ");
		map.put("WebSocket.insertDingInfo","insert into t_ding_msg(id,to_id,msg,from_id,time,chat_msg_id) values('$P.id','$P.toId','$P.msg','$P.fromId','$P.time','$P.chatMsgId') ");
		
		map.put("WebSocket.selectUserId", "select id from ts_user where account='$P.name' and password='$P.pwd'");
		//删除聊天消息
		map.put("WebSocket.deleteChatInfo","delete from t_chat_msg where id='$P.id'");
		map.put("WebSocket.undoMsgList","update t_msg set last_msg='对方撤销一条消息'  where from_user_id='$P.from' and  to_user_id='$P.to'");
		
		//群聊
		map.put("WebSocket.tGroupMsgInsert",
				"insert into   t_msg(id,last_msg,time,info_times,is_top,from_user_id,top_time,to_user_id,groupId) values('$P.id','$P.last_msg','$P.time','$P.info_times','$P.is_top','$P.from_user_id','$P.top_time','$P.to_user_id','$P.groupId')");
		map.put("WebSocket.insertGroupChatInfo","insert into ts_group_chat_msg(id,userId,msg,groupdId,date) values('$P.id','$P.userId','$P.msg','$P.groupdId','$P.date') ");
		map.put("WebSocket.insertGroupChatInfo","insert into ts_group_chat_msg(id,userId,msg,groupId,date) values('$P.id','$P.userId','$P.msg','$P.groupdId','$P.date') ");
		map.put("WebSocket.selectGroupMsg",
				"select * from t_msg where groupId='$P.toId'  limit 0,1");
		//根据id,查询用户信息
		map.put("WebSocket.selectUserInfo","select name from ts_user_detail where userid='$P.userId'");
		
		//清空普通聊天未读消息次数
		map.put("WebSocket.clearInfoTime","update t_msg set info_times='0' where from_user_id='$P.fromUserId' and to_user_id='$P.toUserId' ");
		//清空群聊天未读消息次数
		map.put("WebSocket.clearGroupInfoTime","update t_msg set info_times='0' where  groupId='$P.toUserId' ");
		return map.get(sqlid);
	}
}
