package com.example.valen.fitapp.api;

public class ClientePT
{
     private String nome, email, password ;
     private String [] especialidade ;
     public ClientePT()
        {}

        public ClientePT (String nome , String email , String password  )
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


