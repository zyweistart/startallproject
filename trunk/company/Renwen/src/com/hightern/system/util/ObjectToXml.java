package com.hightern.system.util;

import java.util.Collection;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hightern.system.model.Operator;
import com.hightern.system.model.Posts;
import com.hightern.system.model.Role;

public class ObjectToXml {
	private static final String NODES = "nodes";
	private static final String NODE = "node";
	private static final String ID = "id";
	private static final String NAME = "name";
	
	/**
	 * 将职务列表生成配置文件
	 * 
	 * @param collection
	 * @return {@link String}
	 */
	@SuppressWarnings("rawtypes")
	public static String PostsXML(Collection collection) {
		Posts posts = null;
		/** 建立document对象 * */
		final Document document = DocumentHelper.createDocument();
		/** 建立XML文档的根nodes * */
		final Element nodesElement = document.addElement(ObjectToXml.NODES);
		if (collection != null) {
			final Iterator iterator = collection.iterator();
			while (iterator.hasNext()) {
				posts = (Posts) iterator.next();
				/** 加入一个node节点 * */
				final Element nodeElement = nodesElement.addElement(ObjectToXml.NODE);
				nodeElement.addAttribute(ObjectToXml.ID, String.valueOf(posts.getPostCode()));
				nodeElement.addAttribute(ObjectToXml.NAME, posts.getPostName());
			}
		}
		return document.asXML();
	}
	
	/**
	 * 将角色列表生成配置文件
	 * 
	 * @param collection
	 * @return {@link String}
	 */
	@SuppressWarnings("rawtypes")
	public static String RoleXML(Collection collection) {
		Role role = null;
		/** 建立document对象 * */
		final Document document = DocumentHelper.createDocument();
		/** 建立XML文档的根nodes * */
		final Element nodesElement = document.addElement(ObjectToXml.NODES);
		if (collection != null) {
			final Iterator iterator = collection.iterator();
			while (iterator.hasNext()) {
				role = (Role) iterator.next();
				/** 加入一个node节点 * */
				final Element nodeElement = nodesElement.addElement(ObjectToXml.NODE);
				nodeElement.addAttribute(ObjectToXml.ID, String.valueOf(role.getRoleCode()));
				nodeElement.addAttribute(ObjectToXml.NAME, role.getRoleName());
			}
		}
		return document.asXML();
	}
	
	/**
	 * 将用户列表生成配置文件
	 * 
	 * @param collection
	 * @return {@link String}
	 */
	@SuppressWarnings("rawtypes")
	public static String OperatorXML(Collection collection) {
		Operator operator = null;
		/** 建立document对象 * */
		final Document document = DocumentHelper.createDocument();
		/** 建立XML文档的根nodes * */
		final Element nodesElement = document.addElement(ObjectToXml.NODES);
		if (collection != null) {
			final Iterator iterator = collection.iterator();
			while (iterator.hasNext()) {
				operator = (Operator) iterator.next();
				/** 加入一个node节点 * */
				final Element nodeElement = nodesElement.addElement(ObjectToXml.NODE);
				nodeElement.addAttribute(ObjectToXml.ID, String.valueOf(operator.getCode()));
				nodeElement.addAttribute(ObjectToXml.NAME, operator.getTrueName());
			}
		}
		return document.asXML();
	}
	
}
