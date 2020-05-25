package com.list_stream.test;

/**
 * 交易员
 */
public class Traders {

    private String name;//姓名
    private String address;//地址
    private Integer  turnover;//交易额

    public Traders(String name, String address, Integer turnover) {
        this.name = name;
        this.address = address;
        this.turnover = turnover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTurnover() {
        return turnover;
    }

    public void setTurnover(Integer turnover) {
        this.turnover = turnover;
    }

    @Override
    public String toString() {
        return "Traders{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", turnover=" + turnover +
                '}';
    }


}
