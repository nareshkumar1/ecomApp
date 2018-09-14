package com.ecom.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtils {

	@Autowired
	private static SessionFactory sessionFactory;
	
	public static Session openSession() {
		Session session = sessionFactory.openSession();
		return session;
	}
	
	public static void closeSession() {
		Session session = sessionFactory.getCurrentSession();
		session.close();
	}

}
