package com.liu.common.utils;


public class GenerateId {
	public static String generate(){
		//获取毫秒数1565833737058
		String nowDate = Long.toString(new java.util.Date().getTime());
		//随机数0.6192735909847162
		String random = Double.toString(Math.random());
		//对象java.lang.Object@539510
		Object o = new Object();
		//连接 15658337370580.6192735909847162java.lang.Object@539510
		String idRedom = nowDate + random + o.toString();
		return Encoder.summaryMD5_16(idRedom).toUpperCase();
	}

	private static final String mathRandom(final long length) {
		double len = Math.pow(10D, length);
		long result = (long) (len * Math.random() + 1);

		// 补齐随机数长度
		while (len / result > 10) {
			result *= 10;
		}
		return Long.toString(result);
	}
	
	public static void main(String[] args) {
		String s = generate();
		System.out.println(s);
	}
}
