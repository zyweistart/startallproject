/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.office.dao.impl;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.office.dao.FeedbackDao;
import com.hightern.office.model.Feedback;

@Repository("feedbackDao")
public class FeedbackDaoImpl extends BaseDaoImpl<Feedback, Long> implements
        FeedbackDao {
   
    public FeedbackDaoImpl() {
        super(Feedback.class, "feedback");
    }
    
}
