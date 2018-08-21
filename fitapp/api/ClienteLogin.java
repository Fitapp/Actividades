package com.example.valen.fitapp.api;

public class ClienteLogin {
    private String papel;

    public ClienteLogin(){};
    public ClienteLogin(String papel)
    {
        this.papel=papel;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }
}
