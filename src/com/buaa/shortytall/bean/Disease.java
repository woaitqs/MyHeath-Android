package com.buaa.shortytall.bean;

public class Disease {

    public Disease(String name, String drugs, String description, String tips) {
        super();
        this.name = name;
        this.drugs = drugs;
        this.description = description;
        this.tips = tips;
    }

    private String name;
    private String drugs;
    private String description;
    private String tips;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrugs() {
        return drugs;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

}
