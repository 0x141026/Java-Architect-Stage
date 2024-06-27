package com.njz.utils.excelpdf.dto;

// 资金明细实体类
public class FundDetail {
    private String transferDate;
    private String payer;
    private String payerAccount;
    private String payeeAccount;
    private double balance;

    // 构造函数、getter 和 setter 省略
    public FundDetail(String transferDate, String payer, String payerAccount, String payeeAccount, double balance) {
        this.transferDate = transferDate;
        this.payer = payer;
        this.payerAccount = payerAccount;
        this.payeeAccount = payeeAccount;
        this.balance = balance;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
