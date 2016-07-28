package co.ohlora.firemanapp.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016-07-28.
 */
public class Sensors {
    public String fire;
    public String image1;
    public String image2;
    public String member;
    public String smoke;
    public String temperature;

    public Sensors(){

    }

    public Sensors(String fire, String image1, String image2, String member, String smoke, String temperature){
        this.fire = fire;
        this.image1 = image1;
        this.image2 = image2;
        this.member = member;
        this.smoke = smoke;
        this.temperature = temperature;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fire", fire);
        result.put("image1", image1);
        result.put("image2", image2);
        result.put("member", member);
        result.put("smoke", smoke);
        result.put("temperature", temperature);

        return result;
    }

}
