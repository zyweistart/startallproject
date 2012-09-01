package org.platform.wwwroot.service.impl;

import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;
import org.framework.service.impl.RootServiceImpl;

import org.platform.wwwroot.dao.TemplateDao;
import org.platform.wwwroot.entity.Template;
import org.platform.wwwroot.service.TemplateService;

@Service("templateService")
public final class TemplateServiceImpl extends RootServiceImpl<Template,String> 
implements TemplateService {

	@SuppressWarnings("unused")
	private TemplateDao templateDao;
	
	public TemplateServiceImpl(@Qualifier("templateDao")TemplateDao templateDao) {
		super(templateDao);
		this.templateDao=templateDao;
	}

}
