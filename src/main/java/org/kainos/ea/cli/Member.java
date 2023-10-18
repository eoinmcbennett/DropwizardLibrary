package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class Member {
    @JsonIgnore
    private int memberId;
    private String first_name;
    private String last_name;
    private String address;
    private String email;
    private String phone;
    private Date registerDate;

    public Member(int memberId, String first_name, String last_name, String address, String phone, String email, Date registerDate) {
        this.memberId = memberId;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.email = email;
        this.registerDate = registerDate;
    }

    @JsonCreator
    public Member(@JsonProperty("first_name") String first_name,
                  @JsonProperty("last_name") String last_name,
                  @JsonProperty("address") String address,
                  @JsonProperty("phone") String phone,
                  @JsonProperty("email") String email,
                  @JsonProperty("registerDate") Date registerDate) {
        this.memberId = -1;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.registerDate = registerDate;
    }

    @JsonProperty
    public int getMemberId() {
        return this.memberId;
    }

    @JsonProperty
    public String getFirst_name() {
        return first_name;
    }

    @JsonProperty
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @JsonProperty
    public String getLast_name() {
        return last_name;
    }

    @JsonProperty
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @JsonProperty
    public String getAddress() {
        return address;
    }

    @JsonProperty
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty
    public Date getRegisterDate() {
        return registerDate;
    }

    @JsonProperty
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @JsonProperty
    public String getPhone() {
        return phone;
    }

    @JsonProperty
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
