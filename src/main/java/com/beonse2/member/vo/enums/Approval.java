package com.beonse2.member.vo.enums;

public enum Approval {
    WAITING("승인대기"),
    CONFIRM("승인"),
    REJECT("승인거절");

    final String approval;

    Approval(String approval) {
        this.approval = approval;
    }
}
