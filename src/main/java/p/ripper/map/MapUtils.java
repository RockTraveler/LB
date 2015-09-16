package p.ripper.map;

import org.docx4j.model.datastorage.XPathEnhancerParser.main_return;

/**
 * Created by ripper on 15-9-15.
 */
public class MapUtils {

    private static final  double EARTH_RADIUS = 6378137; //The earth's radius

    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    /**
     * according to cosine theorem to calculate the distance .
     * @param lon1 the first longitude.
     * @param lat1 the first latitude.
     * @param lon2 the second longitude.
     * @param lat2 the second latitude.
     * @return the distance , m is the.
     * */
    public static double getLongitudeLatitudeDist(double lon1, double lat1,double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);

        double radLon1 = rad(lon1);
        double radLon2 = rad(lon2);

        if (radLat1 < 0)
            radLat1 = Math.PI / 2 + Math.abs(radLat1);// south
        if (radLat1 > 0)
            radLat1 = Math.PI / 2 - Math.abs(radLat1);// north
        if (radLon1 < 0)
            radLon1 = Math.PI * 2 - Math.abs(radLon1);// west
        if (radLat2 < 0)
            radLat2 = Math.PI / 2 + Math.abs(radLat2);// south
        if (radLat2 > 0)
            radLat2 = Math.PI / 2 - Math.abs(radLat2);// north
        if (radLon2 < 0)
            radLon2 = Math.PI * 2 - Math.abs(radLon2);// west
        double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);
        double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);
        double z1 = EARTH_RADIUS * Math.cos(radLat1);

        double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);
        double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);
        double z2 = EARTH_RADIUS * Math.cos(radLat2);

        double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)+ (z1 - z2) * (z1 - z2));
        //get the distance base on cosine theorem.
        double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));
        double dist = theta * EARTH_RADIUS;
        return dist;
    }
    
      public static void main(String[] args) {
		System.out.println(getLongitudeLatitudeDist(30.5790347,103.82908759999998,33.925314,-84.370394));
		System.out.println(getLongitudeLatitudeDist(104.073768,30.611118,104.072474,30.656486));   //out 5051.863336969822 m
	}
}
