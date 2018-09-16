package com.ecom.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class HibernateUtils {

	private static SessionFactory sessionFactory;

	public HibernateUtils(SessionFactory sessionFactory) {
		HibernateUtils.sessionFactory = sessionFactory;
	}

	public static Session openSession() {
		return sessionFactory.openSession();
	}

	public static void closeSession() {
		sessionFactory.getCurrentSession().close();
	}

}
