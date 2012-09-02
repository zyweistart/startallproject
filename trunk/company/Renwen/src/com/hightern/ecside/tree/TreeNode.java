package com.hightern.ecside.tree;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings({"unchecked", "serial"})
public final class TreeNode extends HashMap {

    private static Log logger = LogFactory.getLog(TreeNode.class);
    private Object identifier;
    private Object bean;
    private TreeNode parent;
    private List children;
    private int depth;
    private boolean open;

    public TreeNode() {
        super();
    }

    public TreeNode(Object bean, Object identifier, int depth) throws
            IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        if (bean instanceof Map) {
            this.putAll((Map) bean);
        } else {
            PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean.getClass());
            for (int i = 0; i < descriptors.length; i++) {
                this.put(descriptors[i].getName(), BeanUtils.getProperty(bean, descriptors[i].getName()));
            }
        }
        setBean(bean);
        this.identifier = identifier;
        this.depth = depth;
    }

    public void addChild(Object child) {
        if (children == null) {
            children = new ArrayList();
        }

        children.add(child);
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;

        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);

        for (int i = 0; i < descriptors.length; i++) {
            try {
                String propertyName = descriptors[i].getDisplayName();
                Object val = BeanUtils.getProperty(bean, propertyName);
                this.put(propertyName, val);
            } catch (Exception e) {
                logger.error("TreeNode.setBean() Problem", e);
            }
        }
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Object getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Object object) {
        identifier = object;
    }

    @Override
    public boolean equals(Object obj) {
        TreeNode node = (TreeNode) obj;

        return (super.equals(obj) || this.identifier.equals(node.getIdentifier()));
    }
}
