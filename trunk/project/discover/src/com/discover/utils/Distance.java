package com.discover.utils;

/**
 * 距离测量
 * @author Start
 */
public class Distance {

	private static final double EARTH_RADIUS = 6378137.0;
	/**
	 * 计算两地的距离
	 * @param lng1地点1的经度
	 * @param lat1地点1的纬度
	 * @param lng2地点2的经度
	 * @param lat2地点2的经度
	 * @return
	 */
	public double getDistance(double lng1, double lat1, double lng2,double lat2) {
		double Lat1 = rad(lat1);
		double Lat2 = rad(lat2);
		double a = Lat1 - Lat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(Lat1) * Math.cos(Lat2)* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
    private  double rad(double d) {
		return d * Math.PI / 180.0;
	}

}
