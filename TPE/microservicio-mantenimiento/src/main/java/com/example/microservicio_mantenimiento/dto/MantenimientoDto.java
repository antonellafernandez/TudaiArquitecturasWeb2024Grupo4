package com.example.microservicio_mantenimiento.dto;

public class MantenimientoDto {
    private Long umbralKm;
    private Long umbralTiempo;

    // Getters y Setters
    public Long getUmbralKm() {
        return umbralKm;
    }

    public void setUmbralKm(Long umbralKm) {
        this.umbralKm = umbralKm;
    }

    public Long getUmbralTiempo() {
        return umbralTiempo;
    }

    public void setUmbralTiempo(Long umbralTiempo) {
        this.umbralTiempo = umbralTiempo;
    }
}