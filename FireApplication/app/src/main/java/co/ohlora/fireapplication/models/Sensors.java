package co.ohlora.fireapplication.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016-07-28.
 */
public class Sensors {
    public String fire;
    public String image1;
    public String co;
    public String member;
    public String smoke;
    public String temperature;
    public String uid;

    public Sensors(){

    }

    public Sensors(String fire, String image1, String member, String smoke, String temperature, String co, String uid){
        this.fire = fire;
        this.image1 = image1;
        this.member = member;
        this.smoke = smoke;
        this.temperature = temperature;
        this.co = co;
        this.uid = uid;
    }

    public String getFire(){
        return fire;
    }

    public void setFire(String fire){
        this.fire = fire;
    }

    public String getImage1(){
        return image1;
    }

    public void setImage1(String image1){
        this.image1 = image1;
    }

    public String getCo(){
        return co;
    }

    public void setCo(String co){
        this.co = co;
    }

    public String getMember(){
        return member;
    }

    public void setMember(String member){
        this.member = member;
    }

    public String getTemperature(){
        return temperature;
    }

    public void setTemperature(String temperature){
        this.temperature = temperature;
    }

    public String getSmoke(){
        return smoke;
    }

    public void setSmoke(String smoke){
        this.smoke = smoke;
    }

    public String getUserId() {return uid;}

    public void setUid(String uid) {this.uid = uid;}

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fire", fire);
        result.put("image1", image1);
        result.put("co", co);
        result.put("member", member);
        result.put("smoke", smoke);
        result.put("temperature", temperature);

        return result;
    }

}