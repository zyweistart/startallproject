package org.zyweistartframework.persistence.proxy;

import org.zyweistartframework.context.EntityMember;
import org.zyweistartframework.persistence.EntityManager;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * 动态代理处理类
 * @author Start
 */
public class ASMObjectProxyHandle {
	/**
	 * 创建动态代理
	 * @param prototype
	 * 所属的类
	 * @param entityManager
	 * 实体管理器
	 * @param PKValue
	 * 主键值	
	 * @return
	 * 返回创建好的代理对象
	 */
	public static Object create(EntityManager entityManager,EntityMember entityMember,Object primaryKeyValue){
		Enhancer enhancer=new Enhancer();
		enhancer.setSuperclass(entityMember.getPrototype());
		enhancer.setCallbacks(
				new Callback[]{
						new ASMMethodInterceptor(entityManager, entityMember, primaryKeyValue),
						NoOp.INSTANCE});
		enhancer.setCallbackFilter(new ASMCallbackFilter());
		return enhancer.create();
	}
}
