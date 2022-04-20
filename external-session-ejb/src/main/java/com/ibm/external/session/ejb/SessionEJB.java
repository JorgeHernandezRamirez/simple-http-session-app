package com.ibm.external.session.ejb;

import javax.ejb.Remote;

@Remote
public interface SessionEJB {
    Integer count();
}
