package com.pucit.proj.DataModel;

import com.pucit.proj.R;

import java.util.ArrayList;
import java.util.List;

public class Country {
    private Integer picID;
    private String name;
    private String currencyCode;

    public Country(Integer picID, String name, String currencyCode){
        this.picID=picID;
        this.name=name;
        this.currencyCode=currencyCode;
    }

    public static List<Country> getCountryList(){
        List<Country> cList= new ArrayList<Country>();
        cList.add(new Country(R.drawable.pakistan,"Pakistan","PKR"));
        cList.add(new Country(R.drawable.turkey,"Turkey","IRR"));
        cList.add(new Country(R.drawable.saudiarab,"Saudi Arabia","SAR"));
        cList.add(new Country(R.drawable.china,"China","CNY"));
        cList.add(new Country(R.drawable.iran,"Iran","IRR"));
        cList.add(new Country(R.drawable.uk,"UK","GBP"));
        cList.add(new Country(R.drawable.usa,"USA","USD"));
        cList.add(new Country(R.drawable.india,"India","INR"));
        cList.add(new Country(R.drawable.ukrain,"Ukraine","UAH"));
        cList.add(new Country(R.drawable.canada,"Canada","CAD"));
        return cList;
    }

    public Integer getPicID() {
        return picID;
    }

    public String getName() {
        return name;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
