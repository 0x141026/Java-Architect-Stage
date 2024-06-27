package com.njz.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class GroupMember {
    private String groupId;

    private String groupName;

    private String leaderName;

    public GroupMember() {
    }
    public GroupMember(String groupId, String groupName, String leaderName, int size) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.leaderName = leaderName;
        this.size = size;
    }

    private int size;
    @XmlElement(name = "groupId")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    @XmlElement(name = "groupName")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    @XmlElement(name = "leaderName")
    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
