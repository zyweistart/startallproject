package org.zyweistartframework.collections;

import java.util.HashSet;
import java.util.Iterator;

public class HashSetString extends HashSet<String> {

	private static final long serialVersionUID = -826038411730490097L;

	/**
	 * Set不分区大小写比较
	 */
	public Boolean equalsIgnoreCaseContains(String o){
		Iterator<String> e = iterator();
		if (o==null) {
		    while (e.hasNext())
			if (e.next()==null)
			    return true;
		} else {
		    while (e.hasNext())
			if (o.equalsIgnoreCase(e.next()))
			    return true;
		}
		return false;
	}
	
}