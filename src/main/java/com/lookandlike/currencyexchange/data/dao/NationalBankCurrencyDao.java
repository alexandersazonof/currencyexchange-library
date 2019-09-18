package com.lookandlike.currencyexchange.data.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = { "Cur_Name", "Date", "Cur_ID" })
public class NationalBankCurrencyDao {

    @JsonProperty("Cur_Scale")
    private int scale;

    @JsonProperty("Cur_Abbreviation")
    private String currency;

    @JsonProperty("Cur_OfficialRate")
    private float rate;
}
