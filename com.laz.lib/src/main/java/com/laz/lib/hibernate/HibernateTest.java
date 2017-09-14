package com.laz.lib.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateTest {
	public static void main(String[] args) {
		for(int i=0;i<1;i++) {
			
			Session session = HibernateUtils.getSession();
			Transaction tc =null;
			try {
				tc= session.beginTransaction();
				{
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("id", UUID.randomUUID().toString());
					params.put("name", "lz2");
					String sql = "insert into t_test(id,name) values(:id, :name)";
					Query query = session.createSQLQuery(sql);
					for(String key:params.keySet()) {
						query.setParameter(key, params.get(key));
					}
					int ok =  query.executeUpdate();
				}
			
				{
					Map<String,Object> params = new HashMap<String,Object>();
					String sql = "update  t_test set name='dddd' where name='lz2'";
					Query query = session.createSQLQuery(sql);
					for(String key:params.keySet()) {
						query.setParameter(key, params.get(key));
					}
					int ok =  query.executeUpdate();
				}
				tc.commit();
			} catch (Exception e){
				tc.rollback();
			}
		
		}
	}
}
