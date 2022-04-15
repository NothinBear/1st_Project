package com.estimulo.system.common.dao;
import org.springframework.context.MessageSource;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IBatisSupportDAO extends SqlMapClientDaoSupport{
	private MessageSource messageSource;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    protected final MessageSource getMessageSource() {
        return messageSource;
    }
}
