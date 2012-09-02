package com.hightern.kernel.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.hightern.system.model.Operator;

public class OnLineOperatorListener implements HttpSessionBindingListener {
	
	private Operator operator;
	
	public OnLineOperatorListener() {

	}
	
	public OnLineOperatorListener(Operator operator) {
		this.operator = operator;
	}
	
	@SuppressWarnings("rawtypes")
	public Map getMap(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		Map map = (Map) session.getServletContext().getAttribute("operatorList");
		if (map == null) {
			map = new HashMap();
		}
		return map;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void valueBound(HttpSessionBindingEvent event) {
		final Map map = getMap(event);
		if (!map.containsKey(operator.getId())) {
			map.put(operator.getId(), operator.getTrueName());
		}
		event.getSession().getServletContext().setAttribute("operatorList", map);
	}
	
	@SuppressWarnings("rawtypes")
	public void valueUnbound(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		Map map = (Map) application.getAttribute("operatorList");
		map.remove(operator.getId());
	}
	
	public Operator getOperator() {
		return operator;
	}
	
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
}
