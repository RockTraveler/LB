package p.ripper.map;

import java.math.BigDecimal;

public class MapTest {
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
	 * @param lat3 the second latitude.
	 * @return the distance , m is the
	 * */
	public static double getLantitudeLongitudeDist(double lon1, double lat1,double lon2, double lat2) {
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
		//get the distance base on the cosine theorem.
		double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));
		double dist = theta * EARTH_RADIUS;
		return dist;
	}
	
	
	public static void main(String[] args) {
		System.out.println(getLantitudeLongitudeDist(116.398196,39.913828,116.4683,39.914437)/1000);
		Double d = 5.986060862188714;
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(1, BigDecimal.ROUND_DOWN);
		System.out.println(bd);
	}
	
	
	
	
	
	
	
	
}
