package com.list_stream.test;

/**
 * 交易员
 */
public class Traders {

    private String name;//姓名
    private String address;//地址
    private Integer  turnover;//交易额
    private Integer year;//年限

    public Traders(String name, String address, Integer turnover, Integer year) {
        this.name = name;
        this.address = address;
        this.turnover = turnover;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Traders{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", turnover=" + turnover +
                ", year=" + year +
                '}';
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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


}
