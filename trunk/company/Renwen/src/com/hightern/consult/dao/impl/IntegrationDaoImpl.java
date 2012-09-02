/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.consult.dao.IntegrationDao;
import com.hightern.consult.model.Integration;

@Repository("integrationDao")
public class IntegrationDaoImpl extends BaseDaoImpl<Integration, Long>
		implements IntegrationDao {

	public IntegrationDaoImpl() {
		super(Integration.class, "integration");
	}


    public void updatexcrj(Integer xcrj){
    	this.getEntityManager()
		.createQuery(
				"update integration o set o.xcrj =0 where o.xcrj =?")
		.setParameter(1, xcrj).executeUpdate();
    }
	
	
	
	// 获取内容里的首张图片
	public String setNewsImg(String content) {
		String img = "";
		if (content.indexOf("<img") > -1) {
			img = content.substring(content.indexOf("<img"));
			img = img.substring(img.indexOf("src=") + 5);
			img = img.substring(0, img.indexOf("\""));
		}
		return img;
	}

	// 获取内容里的首张图片
	public String setNewsImg1(String content) {
		String img = "";
		if (content.indexOf("<input") > -1) {
			img = content.substring(content.indexOf("<input"));
			img = img.substring(img.indexOf("src=") + 5);
			img = img.substring(0, img.indexOf("\""));
		}
		return img;
	}

    //获取信息
    @SuppressWarnings("unchecked")
    public List<Integration> getnews()
    {
    	return this.getEntityManager().createQuery("select o from integration o where o.isSued=99 and o.parentNewsTypeId=2 order by o.id desc").setFirstResult(0).setMaxResults(3).getResultList();
    	
    }
    
    /***
     * 获取信息
     * @param id   菜单目录 
     * @param pid  父菜单目录
     * @param size  个数
     * @return   Integration
     */
    //获取信息
    @SuppressWarnings("unchecked")
    public List<Integration> getInformation(Long id,Long pid,Integer size){
    	List<Integration> integrations=this.getEntityManager().createQuery("select o from integration o where o.isSued=99 and o.newsTypeId=? and o.parentNewsTypeId=? order by o.id desc ").setParameter(1, id).setParameter(2, pid).setFirstResult(0).setMaxResults(size).getResultList();
    	return integrations;
    }
    
    
    /***
     * 获取信息
     * @param id   菜单目录 
     * @param category  展示类型内容
     * @param size  个数
     * @return   Integration
     */
    //获取信息
    @SuppressWarnings("unchecked")
    public List<Integration> getInform(Long id,Long category,Integer size){
    	List<Integration> integrations=this.getEntityManager().createQuery("select o from integration o where o.isSued=99  and o.parentNewsTypeId=? and o.category=? order by o.id desc ").setParameter(1, id).setParameter(2, category).setFirstResult(0).setMaxResults(size).getResultList();
    	return integrations;
    }
    
    
    
   /***
    *  获取资源表中的资源
    * 
    * @param id   本级ID
    * @param pid  父级ID
    * @param tzStart  判断是否为首页通知公告信息
    * @param size   获取数据条数
    * @return  List<Integration>
    */
    
   @SuppressWarnings("unchecked")
   public List<Integration> hqcontent(Long id,Long pid ,Integer tzStart ,Integer size){
       String jpql="select o from integration o where o.isSued=99  and 1=1 ";
	   if(tzStart.equals(0)){
		   jpql+="and o.tzStart=0 ";
	   }else{
		   jpql+="and o.tzStart=99 ";
	   }
	   if(id!=null){
		   jpql+="and o.newsTypeId="+id +" ";
	   }
	   if(pid!=null){
		   jpql+="and o.parentNewsTypeId="+pid+" ";
	   }
	   jpql+="  order by o.id desc";
	   
       List<Integration> integrations=this.getEntityManager().createQuery(jpql).setFirstResult(0).setMaxResults(size).getResultList();
    	
      return integrations;
    }
    
    
   /***
    * 获取通知公告
    * @param size  个数
    * @return   Integration
    */
   //获取信息
   @SuppressWarnings("unchecked")
   public List<Integration> getgg(Integer size){
   	List<Integration> integrations=this.getEntityManager().createQuery("select o from integration o where o.isSued=99 and o.tzStart=99 order by o.id desc ").setFirstResult(0).setMaxResults(size).getResultList();
   	return integrations;
   }
   
    /***
     *   获取关于创意的第一条内容信息
     * @return
     */
   @SuppressWarnings("unchecked")
   public Integration getGsnr(){
	   List<Integration> integrations=this.getEntityManager().createQuery("select o from integration o where o.isSued=99 and o.category=1 and o.parentNewsTypeId=1 order by o.id desc ").getResultList();
	  if(integrations!=null && integrations.size()>0){
		  return integrations.get(0);
	  }else{
	   return new Integration();
	  }
   }
   
   
   
   
   
}
