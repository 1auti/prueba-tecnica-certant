package com.lautaro_cenizo.service.impl;

import com.lautaro_cenizo.entity.repuesto.Repuesto;
import com.lautaro_cenizo.entity.repuesto.RespuestoRepository;
import com.lautaro_cenizo.service.RespuestoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class RespuetoServiceImpl implements RespuestoService {

    private final  RespuestoRepository repuestoRepository;
    private static final int STOCK_MINIMO = 5;

    public RespuetoServiceImpl(RespuestoRepository repuestoRepository) {
        this.repuestoRepository = repuestoRepository;
    }


    @Override
    public void alertaStockBajo() {
        List<Repuesto> repuestosBajoStock = repuestoRepository
                .findAll()
                .stream()
                .filter(repuesto -> repuesto.getStock() <= repuesto.getStockMinimo())
                .toList();

        if (!repuestosBajoStock.isEmpty()) {
            log.warn("Repuestos con stock bajo: {}", repuestosBajoStock);
        }
    }

}
