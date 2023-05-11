package com.example.bilabonnement.model;

public class MemberType {

    private int member_type_id;

    private String member_type;


    public MemberType() {
    }

    public MemberType(int member_type_id, String member_type) {
        this.member_type_id = member_type_id;
        this.member_type = member_type;
    }

    public int getMember_type_id() {
        return member_type_id;
    }

    public void setMember_type_id(int member_type_id) {
        this.member_type_id = member_type_id;
    }

    public String getMember_type() {
        return member_type;
    }

    public void setMember_type(String member_type) {
        this.member_type = member_type;
    }


}
