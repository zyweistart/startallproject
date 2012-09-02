/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.website.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.model.PagingQuery;
import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.website.dao.InformationDao;
import com.hightern.website.model.Information;
import com.hightern.website.service.InformationService;

@Service("informationService")
@Transactional
public class InformationServiceImpl extends BaseServiceImpl<Information, Long>
        implements InformationService {

    private final InformationDao informationDao;

    @Autowired(required = false)
    public InformationServiceImpl(@Qualifier("informationDao") InformationDao informationDao) {
        super(informationDao);
        this.informationDao = informationDao;
    }

    private final static String JPQL_findInformationByMenu="select o from information o where o.menuId=?";
	@Override
	public boolean findInformationByMenu(Long menuId) {
		List<Information> informations=informationDao.queryForObject(JPQL_findInformationByMenu,new Object[]{menuId});
		if(informations!=null){
			if(!informations.isEmpty()){
				return false;
			}
		}
		return true;
	}
	@Override
	public List<Information> findInformationByMenuId(Information info) {
		return informationDao.getCollection("select o from information o", info,new String[]{"releaseTime"}, true);
	}
	@Override
	public List<Information> findInformationByPage(HttpServletRequest request,
			Information info) {
		PagingQuery pq=new PagingQuery(request);
		info.setPageNo(pq.getCurrentPage());
		info.setPageSize(pq.getLimit());
		pq.PageCompute(informationDao.getCount(null,info));
		return informationDao.paginated(info);
	}
    
}
