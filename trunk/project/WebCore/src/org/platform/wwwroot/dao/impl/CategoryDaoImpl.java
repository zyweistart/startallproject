package org.platform.wwwroot.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.wwwroot.dao.CategoryDao;
import org.platform.wwwroot.entity.Category;

@Repository("categoryDao")
public final class CategoryDaoImpl extends RootDaoImpl<Category,String>implements CategoryDao {

	public CategoryDaoImpl() {
		super(Category.class);
	}

}
