package com.redfinger.manager.modules.award.pogo;

import com.redfinger.manager.common.domain.RfAwardPool;

public class POAwardPool extends RfAwardPool {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String awardCodeMode;
	private Integer awardNumber;

	public String getAwardCodeMode() {
		return awardCodeMode;
	}

	public void setAwardCodeMode(String awardCodeMode) {
		this.awardCodeMode = awardCodeMode;
	}

	public Integer getAwardNumber() {
		return awardNumber;
	}

	public void setAwardNumber(Integer awardNumber) {
		this.awardNumber = awardNumber;
	}

}
