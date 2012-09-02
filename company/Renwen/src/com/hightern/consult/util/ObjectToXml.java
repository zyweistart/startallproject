package com.hightern.consult.util;

import java.util.Collection;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hightern.consult.model.Newstype;

public class ObjectToXml {

	private static final String NODES = "nodes";
	private static final String NODE = "node";
	private static final String ID = "id";
	private static final String NAME = "name";

	/**
	 * 将文件夹列表生成配置文件
	 * 
	 * @param collection
	 * @return {@link String}
	 */
	@SuppressWarnings("rawtypes")
	public static String NewstypeXML(Collection collection) {
		Newstype newstype = null;
		/** 建立document对象 * */
		final Document document = DocumentHelper.createDocument();
		/** 建立XML文档的根nodes * */
		final Element nodesElement = document.addElement(ObjectToXml.NODES);
		if (collection != null) {
			final Iterator iterator = collection.iterator();
			while (iterator.hasNext()) {
				newstype = (Newstype) iterator.next();
				/** 加入一个node节点 * */
				final Element nodeElement = nodesElement.addElement(ObjectToXml.NODE);
				nodeElement.addAttribute(ObjectToXml.ID, String.valueOf(newstype.getId()));
				nodeElement.addAttribute(ObjectToXml.NAME, newstype.getName());
			}
		}
		return document.asXML();
	}
	
	
}
