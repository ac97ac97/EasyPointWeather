package example.sdbi.com.easypointweather.gson;

/**
 * Created by Administrator on 2018/1/22.
 */

public class AQI {
    public AQICity city;
    public class AQICity{
        public String aqi;
        public String pm25;
    }
}
