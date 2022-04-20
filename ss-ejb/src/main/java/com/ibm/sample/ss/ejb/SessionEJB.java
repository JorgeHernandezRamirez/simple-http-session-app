package com.ibm.sample.ss.ejb;

import javax.ejb.Remote;

@Remote
public interface SessionEJB {
    Integer count();
}
