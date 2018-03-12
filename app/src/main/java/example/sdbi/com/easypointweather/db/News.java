package example.sdbi.com.easypointweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/3/12.
 */

public class News extends DataSupport{
    private int id;
    private int img;
    private String newsContent;

    public News() {
    }

    public News(int img, String newsContent, String fromAdress, String date) {
        this.img = img;
        this.newsContent = newsContent;
        this.fromAdress = fromAdress;
        this.date = date;
    }

    private String fromAdress;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getFromAdress() {
        return fromAdress;
    }

    public void setFromAdress(String fromAdress) {
        this.fromAdress = fromAdress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
