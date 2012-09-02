package com.hightern.consult.util;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.hightern.consult.model.Newstype;
import com.hightern.consult.service.NewstypeService;

@Service("newstypeInfo")
public class NewstypeInfo {

	@Resource(name="newstypeService")
	private NewstypeService newstypeService;
	
	public static NewstypeInfo getFromApplicationContext(ApplicationContext ctx) {
		return (NewstypeInfo) ctx.getBean("newstypeInfo");
	}

	 //首页菜单导航
    public List<Newstype> getMenus(){
    	return newstypeService.getMenus(new Long(0));
    }
	
	/***
	 * 获取导航名(二级菜单)
	 * @return
	 */
	public List<Newstype> getMapName(Long id)
	{
		return newstypeService.getMapName(id);
	}
	
	public List<Newstype> findByPid(Long pid){
		return newstypeService.findByPid(pid);
	}
	

	/***
	 * 获取菜单导航
	 * tstart 为  99的数据
	 * @return
	 */
	public List<Newstype> Mapcs(){
		return newstypeService.findByPid(new Long(0));
	}
	
}  
