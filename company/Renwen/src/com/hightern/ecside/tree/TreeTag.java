package com.hightern.ecside.tree;

import com.hightern.ecside.table.bean.Table;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.tag.TableTag;
import com.hightern.ecside.table.tag.TagUtils;

public class TreeTag extends TableTag {

    private static final long serialVersionUID = -1446029469682167925L;
    private String parentAttribute;
    private String identifier;

    public void setParentAttribute(String parentAttribute) {
        this.parentAttribute = parentAttribute;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void addTableAttributes(TableModel model, Table table) {
        table.addAttribute(TreeConstants.PARENT_ATTRIBUTE, TagUtils.evaluateExpressionAsString("parentAttribute", parentAttribute, this, pageContext));
        table.addAttribute(TreeConstants.IDENTIFIER, TagUtils.evaluateExpressionAsString("identifier", identifier, this, pageContext));

        table.setShowPagination(Boolean.FALSE);
        table.setFilterRowsCallback("com.hightern.ecside.tree.ProcessTreeRowsCallback");
        table.setSortRowsCallback("com.hightern.ecside.tree.ProcessTreeRowsCallback");
    }

    @Override
    public void release() {
        parentAttribute = null;
        identifier = null;
        super.release();
    }
}
