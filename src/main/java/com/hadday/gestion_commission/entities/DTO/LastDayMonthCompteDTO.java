package com.hadday.gestion_commission.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LastDayMonthCompteDTO {
    private Long id;
    private String dateComplet;
    private String description;
}
