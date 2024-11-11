package com.example.microservicio_administrador.controller;

import com.example.microservicio_administrador.feignClient.CuentaFeignClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("administradores/cuentas")
public class CuentaController {
    @Autowired
    private CuentaFeignClient cuentaFeignClient;

    /*
    3b. Como administrador quiero poder anular cuentas para inhabilitar el uso momentáneo de la misma.
    */
    @PutMapping
    public ResponseEntity<?> habilitarCuenta(@RequestBody Long id) {
        try{
            return cuentaFeignClient.habilitarCuenta(id);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping
    public ResponseEntity<?> deshabilitarCuenta(@RequestBody Long id) {
        try{
            return cuentaFeignClient.deshabilitarCuenta(id);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
