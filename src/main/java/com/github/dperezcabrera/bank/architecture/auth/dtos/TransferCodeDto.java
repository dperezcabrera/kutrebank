package com.github.dperezcabrera.bank.architecture.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferCodeDto {

    private String code;
}
