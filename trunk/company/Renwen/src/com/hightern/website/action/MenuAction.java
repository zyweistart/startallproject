/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.website.action;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.kernel.util.JsonUtil;
import com.hightern.website.model.Menu;
import com.hightern.website.service.MenuService;

@Scope("prototype")
@Controller("menuAction")
public class MenuAction extends BaseAction<Menu> {

    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_MENU = "menu_manager.htm";
    private static final String ACTION_SHOWADD_MENU = "menu_showAdd.htm";
    private static final String FORWARD_MANAGER_MENU = "/website/manager_menu.jsp";
    private static final String FORWARD_SHOWADD_MENU = "/website/add_menu.jsp";
    private static final String FORWARD_SHOWEDIT_MENU = "/website/edit_menu.jsp";
    private static final String FORWARD_TREE_MENU="/website/tree_menu.jsp";
    @Resource(name = "menuService")
    private MenuService menuService;
    private List<Menu> menus;
    private Menu menu;
    
    public String tree(){
//    	menus=menuService.queryForObject("select o from menu o where 1=1 and o.pagetype='1'",new Object[]{});
    	menus=menuService.queryForObject("select o from menu o",new Object[]{});
		setParameters(FORWARD_TREE_MENU);
		return SUCCESS;
    }
    /**
	 * 根据菜单编号取得菜单名称
	 */
	public String ajax() throws SystemExceptions, IOException {
		response.setContentType("text/html;charset=utf-8");
		if (isNullOrEmptyString(this.getId())) {
			throw new SystemExceptions("菜单编号不可为空！");
		}
		menu = menuService.findById(this.getId());
		response.getWriter().print(JsonUtil.getJsonStringJavaPOJO(menu));
		response.getWriter().close();
		return null;
	}
	
    public String save() throws SystemExceptions {
        if (null != menu) {
            if (menu.getId() > 0) {
                this.setParameters("数据编辑成功！", ACTION_MANAGER_MENU);
            } else {
                this.setParameters("数据保存成功！", ACTION_SHOWADD_MENU);
            }
            menuService.save(menu);
        }
        return SUCCESS;
    }

    /**删除操作
     */
    public String delete() throws SystemExceptions {
        if (isNullOrEmptyString(getSelectedIds())) {
            throw new SystemExceptions("编号不能为空！");
        }
        menuService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_MENU);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     */
    public String manager() throws SystemExceptions {
        if(menu == null){
            menu = new Menu(request, null);
        }
        menu.setName(request.getParameter("name"));
        menu.setPname(request.getParameter("pname"));
        String pageType=request.getParameter("pagetype");
        if(pageType!=null&&pageType.length()>0){
        	menu.setPagetype(Integer.parseInt(pageType));
        }
        menus = menuService.paginated(menu);
        this.setParameters(FORWARD_MANAGER_MENU);
        return SUCCESS;
    }
    
    /**添加导航
     */
    public String showAdd() throws SystemExceptions {
        this.setParameters(FORWARD_SHOWADD_MENU);
        return SUCCESS;
    }

    /**编辑导航
     */
    public String showEdit() throws SystemExceptions {
        if (isNullOrEmptyString(getSelectedIds())) {
            throw new SystemExceptions("编号不能为空！");
        }
        menu = menuService.findById(idsStringToList(getSelectedIds()).get(0));
        this.setParameters(FORWARD_SHOWEDIT_MENU);
        return SUCCESS;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public Menu getMenu() {
    	if(menu == null){
    		menu = new Menu();
    	}
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}