package com.sistema_repositorio.sistema_supermercado.model;

 public class ResponseOk {
        private String message;
    
        public ResponseOk(String message) {
            this.message = message;
        }
    
        public String getMessage() {
            return message;
        }
    
        public void setMessage(String message) {
            this.message = message;
        }
    }
