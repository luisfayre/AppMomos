package com.tap.momos;

/**
 * Created by luisf on 01/06/2017.
 */
public class ShowDataItems {

    private String Image_URL,Image_Title;   //put this name same as Database Fields

    public ShowDataItems(String image_URL, String image_Title) {
        Image_URL = image_URL;
        Image_Title = image_Title;
    }

    public ShowDataItems()
    {
        //Require a Empty Constructor
    }

    public String getImage_URL() {
        return Image_URL;
    }

    public void setImage_URL(String image_URL) {
        Image_URL = image_URL;
    }

    public String getImage_Title() {
        return Image_Title;
    }

    public void setImage_Title(String image_Title) {
        Image_Title = image_Title;
    }
}
