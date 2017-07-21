package com.example.keshav.projecttcs;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AmanPC on 21-07-2017.
 */

public class DonorDeatils implements Serializable {

    public String username;
    public String city;
    public String mobile;
    public String bloodgroup;
    public String email;
    public List<AcceptRequest> requests;


    public DonorDeatils(String username, String city, String mobile, String bloodgroup, String email){
        this.username = username;
        this.city = city;
        this.mobile = mobile;
        this.bloodgroup = bloodgroup;
        this.email = email;
    }

    public String getUsername() { return username; }

    public String getCity() { return city; }

    public String getMobile() { return mobile; }

    public String getBloodgroup() { return bloodgroup; }

    public String getEmail() { return email; }

    public void setRequests(final List<AcceptRequest> requests) { this.requests = requests; }

    public List<AcceptRequest> getRequests() { return requests; }
}
