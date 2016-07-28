package co.ohlora.userapp.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016-07-15.
 */
public class User {
    public String userid;
    public String useremail;
    public String useraddress;
    public String userphone;

    public User(){

    }

    public User(String userid, String useremail, String useraddress, String userphone){
        this.userid = userid;
        this.useremail = useremail;
        this.useraddress = useraddress;
        this.userphone = userphone;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("userid",userid);
        result.put("useraddress",useraddress);
        result.put("userphone",userphone);

        return result;
    }
}
