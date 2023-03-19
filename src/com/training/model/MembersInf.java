package com.training.model;

public class MembersInf {
	private String membersID;
	private String membersPwd;
	private String membersName;
	public String getMembersID() {
		return membersID;
	}
	public void setMembersID(String membersID) {
		this.membersID = membersID;
	}
	public String getMembersPwd() {
		return membersPwd;
	}
	public void setMembersPwd(String membersPwd) {
		this.membersPwd = membersPwd;
	}
	public String getMembersName() {
		return membersName;
	}
	public void setMembersName(String membersName) {
		this.membersName = membersName;
	}
	@Override
	public String toString() {
		return "MembersInf [membersID=" + membersID + ", membersPwd=" + membersPwd + ", membersName=" + membersName
				+ "]";
	}
	
	
}
