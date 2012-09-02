/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.office.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.office.dao.FeedbackDao;
import com.hightern.office.model.Feedback;
import com.hightern.office.service.FeedbackService;

@Service("feedbackService")
@Transactional
public class FeedbackServiceImpl extends BaseServiceImpl<Feedback, Long>
        implements FeedbackService {

    @SuppressWarnings("unused")
    private final FeedbackDao feedbackDao;

    @Autowired(required = false)
    public FeedbackServiceImpl(@Qualifier("feedbackDao") FeedbackDao feedbackDao) {
        super(feedbackDao);
        this.feedbackDao = feedbackDao;
    }
    
}
