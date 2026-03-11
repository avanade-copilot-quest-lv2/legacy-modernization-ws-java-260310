package com.example.bookstore.dao.impl;

import java.util.*;
import java.io.*;
import java.sql.Date;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.example.bookstore.constant.AppConstants;
import com.example.bookstore.dao.ShoppingCartDAO;
import com.example.bookstore.util.HibernateUtil;
import com.example.bookstore.util.DbUtil;

public class ShoppingCartDAOImpl implements ShoppingCartDAO, AppConstants {

    
    public int save(Object cartItem) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(cartItem);
            tx.commit();
            return 0;
        } catch (Exception e) {
            if (tx != null) {
                try { tx.rollback(); } catch (Exception e2) { }
            }
            e.printStackTrace();
            return 9;
        } finally {
            if (session != null) {
                try { session.close(); } catch (Exception e) { }
            }
        }
    }

    
    public List findBySessionId(String sessionId) {
        Session session = null;
        List results = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM ShoppingCart WHERE sessionId = :sid");
            query.setParameter("sid", sessionId);
            results = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                try { session.close(); } catch (Exception e) { }
            }
        }
        return results;
    }

    
    public List findByCustomerId(String customerId) {
        Session session = null;
        List results = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM ShoppingCart WHERE customerId = :cid");
            query.setParameter("cid", customerId);
            results = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                try { session.close(); } catch (Exception e) { }
            }
        }
        return results;
    }

    
    public int deleteBySessionId(String sessionId) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DbUtil.getConnection();
            stmt = conn.createStatement();

            stmt.executeUpdate("DELETE FROM shopping_cart WHERE session_id = '" + sessionId + "'");
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 9;
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }

    
    public int deleteByCustomerId(String customerId) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DbUtil.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM shopping_cart WHERE customer_id = '" + customerId + "'");
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 9;
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }
}
