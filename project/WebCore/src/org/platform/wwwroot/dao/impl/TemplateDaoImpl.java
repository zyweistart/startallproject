package org.platform.wwwroot.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.wwwroot.dao.TemplateDao;
import org.platform.wwwroot.entity.Template;

@Repository("templateDao")
public final class TemplateDaoImpl extends RootDaoImpl<Template,String>implements TemplateDao {

	public TemplateDaoImpl() {
		super(Template.class);
	}

}
