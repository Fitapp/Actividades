package com.example.valen.fitapp.api;

public class ClienteAtleta {

    private String nome, email, password ;

    public ClienteAtleta()
    {}

    public ClienteAtleta (String nome , String email , String password )
    {
        this.nome = nome ;
        this.email = email ;
        this.password = password ;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getNome()
    {
        return this.nome;
    }

    public String getPassword()
    {
        return this.password;
    }


}
