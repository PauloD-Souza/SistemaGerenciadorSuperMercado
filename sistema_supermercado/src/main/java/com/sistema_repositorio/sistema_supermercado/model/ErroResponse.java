package com.sistema_repositorio.sistema_supermercado.model;


    public class ErroResponse {
        private String message;
    
        public ErroResponse(String message) {
            this.message = message;
        }
    
        public String getMessage() {
            return message;
        }
    
        public void setMessage(String message) {
            this.message = message;
        }
    }

