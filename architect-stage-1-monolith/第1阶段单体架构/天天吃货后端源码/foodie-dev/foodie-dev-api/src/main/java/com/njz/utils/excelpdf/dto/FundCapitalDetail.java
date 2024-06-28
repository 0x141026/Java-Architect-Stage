package com.njz.utils.excelpdf.dto;

import lombok.Data;

// 资金明细实体类
@Data
public class FundCapitalDetail {
    private String inDate;
    private String inMoney;
    private String outMoney;
    private String balance;
    private String receiveAccountNo;
    private String receiveAccountName;
    private String receiveAccountBankName;
    private String digest;
    private String postscript;

    public FundCapitalDetail(String inDate, String inMoney, String outMoney, String balance, String receiveAccountNo, String receiveAccountName, String receiveAccountBankName, String digest, String postscript) {
        this.inDate = inDate;
        this.inMoney = inMoney;
        this.outMoney = outMoney;
        this.balance = balance;
        this.receiveAccountNo = receiveAccountNo;
        this.receiveAccountName = receiveAccountName;
        this.receiveAccountBankName = receiveAccountBankName;
        this.digest = digest;
        this.postscript = postscript;
    }
}
