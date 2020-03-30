package com.recycle.backend.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserRecycleKey implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 751609992275364489L;

    @Column(name = "userid")
    Long userid;
    
    @Column(name = "typeid")
    Long typeid;

    public UserRecycleKey(Long userid, Long typeid) {
        this.userid = userid;
        this.typeid = typeid;
    }

    public UserRecycleKey(){}

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getTypeid() {
        return typeid;
    }

    public void setTypeid(Long typeid) {
        this.typeid = typeid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((typeid == null) ? 0 : typeid.hashCode());
        result = prime * result + ((userid == null) ? 0 : userid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserRecycleKey other = (UserRecycleKey) obj;
        if (typeid == null) {
            if (other.typeid != null)
                return false;
        } else if (!typeid.equals(other.typeid))
            return false;
        if (userid == null) {
            if (other.userid != null)
                return false;
        } else if (!userid.equals(other.userid))
            return false;
        return true;
    }


        
}
