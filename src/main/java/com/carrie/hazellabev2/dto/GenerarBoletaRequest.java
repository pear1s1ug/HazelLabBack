package com.carrie.hazellabev2.dto;

public class GenerarBoletaRequest {
    private Long usuarioId;
    private String metodoPago;
    private String metodoEnvio;
    
    // Constructores
    public GenerarBoletaRequest() {}
    
    public GenerarBoletaRequest(Long usuarioId, String metodoPago, String metodoEnvio) {
        this.usuarioId = usuarioId;
        this.metodoPago = metodoPago;
        this.metodoEnvio = metodoEnvio;
    }
    
    // Getters y Setters
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    
    public String getMetodoEnvio() { return metodoEnvio; }
    public void setMetodoEnvio(String metodoEnvio) { this.metodoEnvio = metodoEnvio; }
}