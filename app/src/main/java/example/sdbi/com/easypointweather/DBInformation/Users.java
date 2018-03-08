package example.sdbi.com.easypointweather.DBInformation;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/3/8.
 */

public class Users extends DataSupport{
    private int id;
    private String phoneNumber;
    private String passWord;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
