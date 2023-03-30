package com.sistema_repositorio.sistema_supermercado.model;

import java.util.regex.*;


public class Cliente {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        try {
            if (name == null) {
                throw new IllegalArgumentException("Nome não pode ser nulo");
            }
            if (name.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome não pode ser vazio");
            }
            if (name.matches("^[0-9]+$")) {
                throw new IllegalArgumentException("O nome do cliente não pode conter números");
            }
            this.name = name;
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao definir o nome do cliente " + e.getMessage());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public static boolean validarEmail(String email) {
        String regex = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }
        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cpf.charAt(i));
            soma += digito * (10 - i);
        }
        int resto = 11 - (soma % 11);
        int digito1 = resto > 9 ? 0 : resto;
        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            int digito = Character.getNumericValue(cpf.charAt(i));
            soma += digito * (11 - i);
        }
        resto = 11 - (soma % 11);
        int digito2 = resto > 9 ? 0 : resto;

        // Verifica se os dígitos verificadores são válidos
        return (digito1 == Character.getNumericValue(cpf.charAt(9)))
                && (digito2 == Character.getNumericValue(cpf.charAt(10)));
    }
}
