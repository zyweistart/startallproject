package org.platform.user.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.user.dao.IdentifierDao;
import org.platform.user.entity.Identifier;

@Repository("identifierDao")
public final class IdentifierDaoImpl extends RootDaoImpl<Identifier,String>implements IdentifierDao {

	public IdentifierDaoImpl() {
		super(Identifier.class);
	}

}
