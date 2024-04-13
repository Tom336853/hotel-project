package com.wn.demo.pojo;


public class Restaurant {
    private Integer id;
    private String name;
    private String restNo;
    private Integer peopleNumber;
    private String restImg;
    private String restDesc;
    private Integer restPrice;

    private String startTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRestNo() {
        return restNo;
    }

    public void setRestNo(String restNo) {
        this.restNo = restNo;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public String getRestImg() {
        return restImg;
    }

    public void setRestImg(String restImg) {
        this.restImg = restImg;
    }

    public String getRestDesc() {
        return restDesc;
    }

    public void setRestDesc(String restDesc) {
        this.restDesc = restDesc;
    }

    public Integer getRestPrice() {
        return restPrice;
    }

    public void setRestPrice(Integer restPrice) {
        this.restPrice = restPrice;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
