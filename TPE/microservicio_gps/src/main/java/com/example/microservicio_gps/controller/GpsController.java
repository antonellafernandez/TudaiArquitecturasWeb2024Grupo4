package com.example.microservicio_gps.controller;


import com.example.microservicio_gps.entity.Gps;
import com.example.microservicio_gps.service.GpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gps")
public class GpsController {

    @Autowired
    GpsService gpsService;

    @GetMapping("/")
    public ResponseEntity<List<Gps>> getAllGps() {
        List<Gps> gpsList = gpsService.getAll();
        if (gpsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(gpsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gps> getGpsById(@PathVariable("id") Long id) {
        Gps gps = gpsService.findById(id);
        if (gps == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gps);
    }

    @PostMapping("")
    public ResponseEntity<Gps> save(@RequestBody Gps gps) {
        Gps gpsNew = gpsService.save(gps);
        return ResponseEntity.ok(gpsNew);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Gps gps = gpsService.findById(id);
        if (gps == null) {
            return ResponseEntity.notFound().build();
        }
        gpsService.delete(gps);
        return ResponseEntity.noContent().build();
    }
}