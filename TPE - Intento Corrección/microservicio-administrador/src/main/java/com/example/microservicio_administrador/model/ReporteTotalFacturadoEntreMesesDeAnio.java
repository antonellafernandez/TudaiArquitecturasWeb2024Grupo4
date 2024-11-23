package com.example.microservicio_administrador.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReporteTotalFacturadoEntreMesesDeAnio {
    private Double totalFacturado;
    private Long anio;
    private Long mesInicio;
    private Long mesFin;
}
