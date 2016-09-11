package co.ohlora.fireapplication.models;

/**
 * Created by Administrator on 2016-09-02.
 */
public class Users {
    public String useraddress;
    public String useremail;
    public String userid;
    public String userphone;

    public Users(){

    }
    public Users(String useraddress, String useremail, String userid, String userphone){
        this.useraddress = useraddress;
        this.useremail = useremail;
        this.userid = userid;
        this.userphone = userphone;
    }
}
