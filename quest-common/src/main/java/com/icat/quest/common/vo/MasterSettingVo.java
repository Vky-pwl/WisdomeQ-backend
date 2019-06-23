package com.icat.quest.common.vo;

public class MasterSettingVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer settingId;
	private String settingName;
	private Boolean active;
	
	public MasterSettingVo() {
	}

	public MasterSettingVo(Integer settingId, String settingName, Boolean active) {
		super();
		this.settingId = settingId;
		this.settingName = settingName;
		this.active = active;
	}

	public Integer getSettingId() {
		return settingId;
	}

	public void setSettingId(Integer settingId) {
		this.settingId = settingId;
	}

	public String getSettingName() {
		return settingName;
	}

	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "MasterSettingVo [settingId=" + settingId + ", settingName=" + settingName + ", active=" + active + "]";
	}

		
}
