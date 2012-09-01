package org.platform.wwwroot.service.impl;

import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;
import org.framework.service.impl.RootServiceImpl;

import org.platform.wwwroot.dao.CategoryDao;
import org.platform.wwwroot.entity.Category;
import org.platform.wwwroot.service.CategoryService;

@Service("categoryService")
public final class CategoryServiceImpl extends RootServiceImpl<Category,String> 
implements CategoryService {

	@SuppressWarnings("unused")
	private CategoryDao categoryDao;
	
	public CategoryServiceImpl(@Qualifier("categoryDao")CategoryDao categoryDao) {
		super(categoryDao);
		this.categoryDao=categoryDao;
	}

}
