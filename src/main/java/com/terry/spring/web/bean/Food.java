package com.terry.spring.web.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Created by TerryShisan on 2018/2/9.
 */
@Data
@NoArgsConstructor
public class Food {
    private float salesMoney;
    private float costMoney;
    private float cookingTime;

}
