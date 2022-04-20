package com.ibm.external.session.ejb;

import javax.ejb.Stateless;

@Stateless
public class SessionEJBBean implements SessionEJB {

    private Integer counter;

    public SessionEJBBean() {
        counter = 0;
    }

    @Override
    public Integer count() {
        return counter++;
    }
}
