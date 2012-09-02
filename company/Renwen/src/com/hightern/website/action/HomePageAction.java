package com.hightern.website.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hightern.kernel.action.BaseAction;
import com.hightern.website.model.Information;
import com.hightern.website.model.Menu;
import com.hightern.website.service.InformationService;
import com.hightern.website.service.MenuService;

@Scope("prototype")
@Controller("homePageAction")
public class HomePageAction extends BaseAction<Information> {

	private static final long serialVersionUID = 1L;

	@Resource(name = "menuService")
	private MenuService menuService;
	@Resource(name = "informationService")
    private InformationService informationService;
	
	private static final String FORWARD_HOME_DETAIL="/home/detail.jsp";
	private static final String FORWARD_HOME_DETAILPIC="/home/detailpic.jsp";
	private static final String FORWARD_HOME_INFORMATION_LIST="/home/list.jsp";
	private static final String FORWARD_HOME_IMAGES_LIST="/home/listpic.jsp";
	private static final String FORWARD_HOME_MENU_LIST="/home/menu_list.jsp";
	
	private Map<Menu,List<Information>> menuByInformations;
	
	private Menu menu;
	private List<Menu> menus;
	private Information information;
	private List<Information> informations;
	
	@Override
	public String execute() {
		if(getId()!=null){
			menus=menuService.findMenuByParentId(getId());
			for(Menu m:menus){
				information=new Information();
				//发布状态
				information.setReleases(2);
				information.setMenuId(m.getId());
				information.setPageSize(3);
				getMenuByInformations().put(m, informationService.findInformationByMenuId(information));
			}
			//为空则取父级
			if(menus.isEmpty()){
				menus=menuService.findMenuByParentId(menu.getPid());
			}
		}
		 this.setParameters(FORWARD_HOME_MENU_LIST);
	     return SUCCESS;
	}
	
	public String list(){
		if(getId()!=null){
			menu=menuService.findById(getId());
			if(information==null){
				information=new Information(request,null);
			}
			information.setMenuId(getId());
			//发布状态
			information.setReleases(2);
			informations=informationService.findInformationByPage(request, information);
			menus=menuService.findMenuByParentId(getId());
			for(Menu m:menus){
				Information info=new Information();
				info.setMenuId(m.getId());
				info.setPageSize(3);
				info.setReleases(2);
				getMenuByInformations().put(m, informationService.findInformationByMenuId(info));
			}
			//为空则取父级
			if(menus.isEmpty()){
				menus=menuService.findMenuByParentId(menu.getPid());
			}
			if(menu.getPagetype()==2){
				if(informations.size()>0){
					information=informations.get(0);
					informations=null;
					this.setParameters(FORWARD_HOME_DETAIL);
					return SUCCESS;
				}
			}
		}
		if(menu.getPagetype()==4){
			//图文列表
			this.setParameters(FORWARD_HOME_IMAGES_LIST);
		}else{
			this.setParameters(FORWARD_HOME_INFORMATION_LIST);
		}
		return SUCCESS;
	}
	
	public String detail(){
		if(getId()!=null){
			information=informationService.findById(getId());
			if(information.getReleases()!=2){
				return null;
			}
			menu=menuService.findById(information.getMenuId());
			if(information.getHits()==null){
				information.setHits(1);
			}else{
				information.setHits(information.getHits()+1);
			}
			informationService.save(information);
			menus=menuService.findMenuByParentId(menu.getId());
			if(menus.isEmpty()){
				menus=menuService.findMenuByParentId(menu.getPid());
			}
		}
		if(information.getPagetype()==4){
			//图文列表
			this.setParameters(FORWARD_HOME_DETAILPIC);
		}else{
			this.setParameters(FORWARD_HOME_DETAIL);
		}
	    return SUCCESS;
	}
	
	public Map<Menu, List<Information>> getMenuByInformations() {
		if(menuByInformations==null){
			menuByInformations=new LinkedHashMap<Menu,List<Information>>();
		}
		return menuByInformations;
	}

	public void setMenuByInformations(
			Map<Menu, List<Information>> menuByInformations) {
		this.menuByInformations = menuByInformations;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	public List<Information> getInformations() {
		if(informations==null){
			informations=new ArrayList<Information>();
		}
		return informations;
	}

	public void setInformations(List<Information> informations) {
		this.informations = informations;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public static void main(String[] args) {
		int count=15;
		int col=3;
		int rem=count%3;
		int row=count/3;
		for(int i=0;i<row;i++){
			System.out.println("<tr>");
			for(int j=0;j<col;j++){
				System.out.println("\t\t<td>");
				System.out.println("\t\tcol:"+col);
				System.out.println("\t\t</td>");
			}
			System.out.println("</tr>");
		}
		if(rem>0){
			System.out.println("<tr>");
			for(int j=0;j<rem;j++){
				if(rem==1){
					System.out.println("\t\t<td col col=3>");
				}else if(rem==2){
					if(j==1){
						System.out.println("\t\t<td>");
					}else{
						System.out.println("\t\t<td col col=2>");
					}
				}
				System.out.println("\t\tcol:"+col);
				System.out.println("\t\t</td>");
			}
			System.out.println("</tr>");
		}
	}
	
}
