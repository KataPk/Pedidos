package com.example.pedidos.payload.response;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class UserInfoResponse {

    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public UserInfoResponse(Long id, String email, String username, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setUsername(String username){this.username = username;}

    public void setEmail(String email) {
        this.email = email;
    }


}
