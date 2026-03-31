package com.app.measurementservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityInputDTO {

    private QuantityDTO thisQuantityDTO;
    private QuantityDTO thatQuantityDTO;
}