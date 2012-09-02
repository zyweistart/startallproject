package com.hightern.kernel.model;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页
 * @author Start
 */
public class PagingQuery{
	
	public PagingQuery(HttpServletRequest req){
		this.request=req;
	}
	
	protected transient HttpServletRequest request;
	
	private Integer start=0;

	private Integer limit=15;
	
	private String sort="o.lastmodifytime desc";
	/**
	 * 开始记录数
	 */
	public int Start() {
		if(request!=null&&start==0){
			String start=new String();
			if((start=request.getParameter("start"))!=null){
				try{
					this.start=Integer.parseInt(start);
				}catch(Exception e){
					this.start=0;
				}
			}
		}
		return start>0?(start-1)*getLimit():0;
	}
	/**
	 * 共获取几条
	 */
	public int getLimit() {
		if(request!=null&&limit==15){
			String limit=new String();
			if((limit=request.getParameter("limit"))!=null){
				try{
					this.limit=Integer.parseInt(limit);
				}catch(Exception e){
					this.limit=15;
				}
			}
		}
		return limit;
	}
	/**
	 * 默认排序
	 */
	public String Sort(){
//		if(request!=null){
//			String nSort=request.getParameter("SORTNAME");
//			if(nSort!=null&&!"".equals(nSort)){
//				String tSort=request.getParameter("$SORTTYPE");
//				sort="O."+nSort+" "+tSort;
//			}
//		}
		return sort;
	}
	/**
	 * 获取当前页
	 */
	public Integer getCurrentPage(){
		if(start==0)Start();
		Integer prePage=start==0?1:start;
		return prePage;
	}
	/**
	 * 页数计算
	 */
	public void PageCompute(Integer totoal){
		if(request!=null){
			if(start==0)Start();
			Integer prePage=start==0?1:start;
			Integer nextPage=prePage;
			Integer limit=getLimit();
			Integer totalPageNum=totoal/limit;
			if(totalPageNum==0){
				if(totoal%limit>0){
					totalPageNum++;
				}else{
					totalPageNum=1;
				}
			}else{
				if(totoal%limit>0){
					totalPageNum++;
				}
			}
			if(prePage>1){
				prePage--;
			}
			if(start<totalPageNum){
				nextPage++;
			}else if(start==totalPageNum){
				nextPage=totalPageNum;
			}
			//总记录数
			request.setAttribute("$TOTAL", totoal);
			//当前页
			request.setAttribute("$STARTPAGE", this.start);
			//当前页大小
			request.setAttribute("$PAGELIMIT", this.limit);
			//上一页
			request.setAttribute("$PREPAGE", prePage);
			//下一页
			request.setAttribute("$NEXTPAGE", nextPage);
			//总数页
			request.setAttribute("$TOTALPAGE", totalPageNum);
			//排序
//			String nSort=request.getParameter("$SORTNAME");
//			if(nSort!=null&&!"".equals(nSort)){
//				String tSort=request.getParameter("$SORTTYPE");
//				if(tSort!=null&&!"".equals(tSort)){
//					tSort="ASC";
//				}
//				sort="o."+nSort+" "+tSort;
//				request.setAttribute("$SORTNAME", nSort);
//				request.setAttribute("$SORTTYPE", tSort);
//			}
		}
	}
	
	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
	
	public void setLimit(Integer limit){
		this.limit=limit;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}
	
}