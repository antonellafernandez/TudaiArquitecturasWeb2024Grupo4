package com.example.microservicio_monopatin.entity;

import com.example.microservicio_gps.entity.Gps;
import com.example.microservicio_viaje.entity.Viaje;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long gpsId;
    private Long kmRecorridosTotales;
    private Boolean disponible;

    @OneToOne(mappedBy = "monopatin")
    private Viaje viajeActivo;
    /*
        @OneToOne(mappedBy = "monopatinActual")
        private Usuario usuario;

      */
    @OneToOne
    @JoinColumn(name = "gps_id")
    private Gps gps;

    public void pausarMonopatin() {
        this.disponible = false;  // Cambia el estado a no disponible
    }
}