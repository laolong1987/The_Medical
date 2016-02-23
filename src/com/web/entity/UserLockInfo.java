package com.web.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_accchange_info")
public class UserLockInfo {
	@Id
	@Basic(optional = false)
	@Column(name="id" )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="ewid")
	private String ewid;
	
	@Column(name="lock_type")
	private int lockType;
	
	@Column(name="lock_date")
	private String lockDate;
	
	@Column(name="reason")
	private String reason;
	
	@Column(name="unlock_date")
	private String unlockDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEwid() {
		return ewid;
	}

	public void setEwid(String ewid) {
		this.ewid = ewid;
	}

	public int getLockType() {
		return lockType;
	}

	public void setLockType(int lockType) {
		this.lockType = lockType;
	}

	public String getLockDate() {
		return lockDate;
	}

	public void setLockDate(String lockDate) {
		this.lockDate = lockDate;
	}
	
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUnlockDate() {
        return unlockDate;
    }

    public void setUnlockDate(String unlockDate) {
        this.unlockDate = unlockDate;
    }
	
}
