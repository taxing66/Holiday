package com.joiway.devin.holiday.model.net;

import com.joiway.devin.holiday.model.single.EnterpriseNetAccessEntity;

/**
 * Created by Administrator on 2016/8/11.
 */
public class GetBonusWithdrawCount extends EnterpriseNetAccessEntity{
  private String key_string_withdraw_type;
    private String key_string_money_sum;
    private String key_string_total_money;

    public String getKey_string_withdraw_type() {
        return key_string_withdraw_type;
    }

    public void setKey_string_withdraw_type(String key_string_withdraw_type) {
        this.key_string_withdraw_type = key_string_withdraw_type;
    }

    public String getKey_string_money_sum() {
        return key_string_money_sum;
    }

    public void setKey_string_money_sum(String key_string_money_sum) {
        this.key_string_money_sum = key_string_money_sum;
    }

    public String getKey_string_total_money() {
        return key_string_total_money;
    }

    public void setKey_string_total_money(String key_string_total_money) {
        this.key_string_total_money = key_string_total_money;
    }
}
