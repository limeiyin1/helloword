package com.redfinger.manager.modules.facility.dto;

public class GameDto {
	private String padIp;
	private String packgeName;
	private String run;
	private String runName;
	private String descripble;
	
	
	public GameDto() {
		super();
	}

	
	
	
	
	public GameDto(String padIp, String packgeName, String run, String runName,
			String descripble) {
		super();
		this.padIp = padIp;
		this.packgeName = packgeName;
		this.run = run;
		this.runName = runName;
		this.descripble = descripble;
	}





	public String getRunName() {
		return runName;
	}

	public void setRunName(String runName) {
		this.runName = runName;
	}

	public String getPadIp() {
		return padIp;
	}
	public void setPadIp(String padIp) {
		this.padIp = padIp;
	}
	public String getPackgeName() {
		return packgeName;
	}
	public void setPackgeName(String packgeName) {
		this.packgeName = packgeName;
	}
	public String getRun() {
		return run;
	}
	public void setRun(String run) {
		this.run = run;
	}
	public String getDescripble() {
		return descripble;
	}
	public void setDescripble(String descripble) {
		this.descripble = descripble;
	}
	

}
