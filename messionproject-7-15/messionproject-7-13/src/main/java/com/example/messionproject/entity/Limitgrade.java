package com.example.messionproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Limitgrade {
    String proid,userid,limitgrade;
    public Limitgrade(String limitgrade){
        this.limitgrade=limitgrade;
    }

}
