/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.kernel.util;

public class MD5 {
	
	/*
	 * Convert a 32-bit number to a hex string with ls-byte first
	 */
	static String hex_chr = "0123456789abcdef";
	
	/*
	 * Add integers, wrapping at 2^32
	 */
	private static int add(int x, int y) {
		return (x & 0x7FFFFFFF) + (y & 0x7FFFFFFF) ^ x & 0x80000000 ^ y & 0x80000000;
	}
	
	/*
	 * Take a string and return the hex representation of its MD5.
	 */
	public static String calcMD5(String str) {
		final int[] x = MD5.str2blks_MD5(str);
		int a = 0x67452301;
		int b = 0xEFCDAB89;
		int c = 0x98BADCFE;
		int d = 0x10325476;
		for (int i = 0; i < x.length; i += 16) {
			final int olda = a;
			final int oldb = b;
			final int oldc = c;
			final int oldd = d;
			a = MD5.ff(a, b, c, d, x[i + 0], 7, 0xD76AA478);
			d = MD5.ff(d, a, b, c, x[i + 1], 12, 0xE8C7B756);
			c = MD5.ff(c, d, a, b, x[i + 2], 17, 0x242070DB);
			b = MD5.ff(b, c, d, a, x[i + 3], 22, 0xC1BDCEEE);
			a = MD5.ff(a, b, c, d, x[i + 4], 7, 0xF57C0FAF);
			d = MD5.ff(d, a, b, c, x[i + 5], 12, 0x4787C62A);
			c = MD5.ff(c, d, a, b, x[i + 6], 17, 0xA8304613);
			b = MD5.ff(b, c, d, a, x[i + 7], 22, 0xFD469501);
			a = MD5.ff(a, b, c, d, x[i + 8], 7, 0x698098D8);
			d = MD5.ff(d, a, b, c, x[i + 9], 12, 0x8B44F7AF);
			c = MD5.ff(c, d, a, b, x[i + 10], 17, 0xFFFF5BB1);
			b = MD5.ff(b, c, d, a, x[i + 11], 22, 0x895CD7BE);
			a = MD5.ff(a, b, c, d, x[i + 12], 7, 0x6B901122);
			d = MD5.ff(d, a, b, c, x[i + 13], 12, 0xFD987193);
			c = MD5.ff(c, d, a, b, x[i + 14], 17, 0xA679438E);
			b = MD5.ff(b, c, d, a, x[i + 15], 22, 0x49B40821);
			a = MD5.gg(a, b, c, d, x[i + 1], 5, 0xF61E2562);
			d = MD5.gg(d, a, b, c, x[i + 6], 9, 0xC040B340);
			c = MD5.gg(c, d, a, b, x[i + 11], 14, 0x265E5A51);
			b = MD5.gg(b, c, d, a, x[i + 0], 20, 0xE9B6C7AA);
			a = MD5.gg(a, b, c, d, x[i + 5], 5, 0xD62F105D);
			d = MD5.gg(d, a, b, c, x[i + 10], 9, 0x02441453);
			c = MD5.gg(c, d, a, b, x[i + 15], 14, 0xD8A1E681);
			b = MD5.gg(b, c, d, a, x[i + 4], 20, 0xE7D3FBC8);
			a = MD5.gg(a, b, c, d, x[i + 9], 5, 0x21E1CDE6);
			d = MD5.gg(d, a, b, c, x[i + 14], 9, 0xC33707D6);
			c = MD5.gg(c, d, a, b, x[i + 3], 14, 0xF4D50D87);
			b = MD5.gg(b, c, d, a, x[i + 8], 20, 0x455A14ED);
			a = MD5.gg(a, b, c, d, x[i + 13], 5, 0xA9E3E905);
			d = MD5.gg(d, a, b, c, x[i + 2], 9, 0xFCEFA3F8);
			c = MD5.gg(c, d, a, b, x[i + 7], 14, 0x676F02D9);
			b = MD5.gg(b, c, d, a, x[i + 12], 20, 0x8D2A4C8A);
			a = MD5.hh(a, b, c, d, x[i + 5], 4, 0xFFFA3942);
			d = MD5.hh(d, a, b, c, x[i + 8], 11, 0x8771F681);
			c = MD5.hh(c, d, a, b, x[i + 11], 16, 0x6D9D6122);
			b = MD5.hh(b, c, d, a, x[i + 14], 23, 0xFDE5380C);
			a = MD5.hh(a, b, c, d, x[i + 1], 4, 0xA4BEEA44);
			d = MD5.hh(d, a, b, c, x[i + 4], 11, 0x4BDECFA9);
			c = MD5.hh(c, d, a, b, x[i + 7], 16, 0xF6BB4B60);
			b = MD5.hh(b, c, d, a, x[i + 10], 23, 0xBEBFBC70);
			a = MD5.hh(a, b, c, d, x[i + 13], 4, 0x289B7EC6);
			d = MD5.hh(d, a, b, c, x[i + 0], 11, 0xEAA127FA);
			c = MD5.hh(c, d, a, b, x[i + 3], 16, 0xD4EF3085);
			b = MD5.hh(b, c, d, a, x[i + 6], 23, 0x04881D05);
			a = MD5.hh(a, b, c, d, x[i + 9], 4, 0xD9D4D039);
			d = MD5.hh(d, a, b, c, x[i + 12], 11, 0xE6DB99E5);
			c = MD5.hh(c, d, a, b, x[i + 15], 16, 0x1FA27CF8);
			b = MD5.hh(b, c, d, a, x[i + 2], 23, 0xC4AC5665);
			a = MD5.ii(a, b, c, d, x[i + 0], 6, 0xF4292244);
			d = MD5.ii(d, a, b, c, x[i + 7], 10, 0x432AFF97);
			c = MD5.ii(c, d, a, b, x[i + 14], 15, 0xAB9423A7);
			b = MD5.ii(b, c, d, a, x[i + 5], 21, 0xFC93A039);
			a = MD5.ii(a, b, c, d, x[i + 12], 6, 0x655B59C3);
			d = MD5.ii(d, a, b, c, x[i + 3], 10, 0x8F0CCC92);
			c = MD5.ii(c, d, a, b, x[i + 10], 15, 0xFFEFF47D);
			b = MD5.ii(b, c, d, a, x[i + 1], 21, 0x85845DD1);
			a = MD5.ii(a, b, c, d, x[i + 8], 6, 0x6FA87E4F);
			d = MD5.ii(d, a, b, c, x[i + 15], 10, 0xFE2CE6E0);
			c = MD5.ii(c, d, a, b, x[i + 6], 15, 0xA3014314);
			b = MD5.ii(b, c, d, a, x[i + 13], 21, 0x4E0811A1);
			a = MD5.ii(a, b, c, d, x[i + 4], 6, 0xF7537E82);
			d = MD5.ii(d, a, b, c, x[i + 11], 10, 0xBD3AF235);
			c = MD5.ii(c, d, a, b, x[i + 2], 15, 0x2AD7D2BB);
			b = MD5.ii(b, c, d, a, x[i + 9], 21, 0xEB86D391);
			a = MD5.add(a, olda);
			b = MD5.add(b, oldb);
			c = MD5.add(c, oldc);
			d = MD5.add(d, oldd);
		}
		return MD5.rhex(a) + MD5.rhex(b) + MD5.rhex(c) + MD5.rhex(d);
	}
	
	/*
	 * These functions implement the basic operation for each round of the algorithm.
	 */
	private static int cmn(int q, int a, int b, int x, int s, int t) {
		return MD5.add(MD5.rol(MD5.add(MD5.add(a, q), MD5.add(x, t)), s), b);
	}
	
	private static int ff(int a, int b, int c, int d, int x, int s, int t) {
		return MD5.cmn(b & c | ~b & d, a, b, x, s, t);
	}
	
	private static int gg(int a, int b, int c, int d, int x, int s, int t) {
		return MD5.cmn(b & d | c & ~d, a, b, x, s, t);
	}
	
	private static int hh(int a, int b, int c, int d, int x, int s, int t) {
		return MD5.cmn(b ^ c ^ d, a, b, x, s, t);
	}
	
	private static int ii(int a, int b, int c, int d, int x, int s, int t) {
		return MD5.cmn(c ^ (b | ~d), a, b, x, s, t);
	}
	
	private static String rhex(int num) {
		String str = "";
		for (int j = 0; j <= 3; j++) {
			str = str + MD5.hex_chr.charAt(num >> j * 8 + 4 & 0x0F) + MD5.hex_chr.charAt(num >> j * 8 & 0x0F);
		}
		return str;
	}
	
	/*
	 * Bitwise rotate a 32-bit number to the left
	 */
	private static int rol(int num, int cnt) {
		return num << cnt | num >>> 32 - cnt;
	}
	
	/*
	 * Convert a string to a sequence of 16-word blocks, stored as an array. Append padding bits and the length, as described in the MD5 standard.
	 */
	private static int[] str2blks_MD5(String str) {
		final int nblk = (str.length() + 8 >> 6) + 1;
		final int[] blks = new int[nblk * 16];
		int i = 0;
		for (i = 0; i < nblk * 16; i++) {
			blks[i] = 0;
		}
		for (i = 0; i < str.length(); i++) {
			blks[i >> 2] |= str.charAt(i) << i % 4 * 8;
		}
		blks[i >> 2] |= 0x80 << i % 4 * 8;
		blks[nblk * 16 - 2] = str.length() * 8;
		
		return blks;
	}
	
	public static void main(String[] args) {
		System.out.println(MD5.calcMD5("111"));
	}
}
