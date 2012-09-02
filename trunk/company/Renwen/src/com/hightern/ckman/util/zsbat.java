package com.hightern.ckman.util;

public class zsbat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try {
			
			
			Runtime.getRuntime().exec("cmd /c start E:\\jsp\\hbwh\\WebRoot\\userfiles\\zsfiles\\zs.bat "+" E:\\jsp\\hbwh\\WebRoot\\userfiles\\zsfiles\\text.avi "+"E:\\jsp\\hbwh\\WebRoot\\userfiles\\zsfiles\\aa.jpg");
			
			System.out.println("成功");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("失败");
		}
		
		
	}

}
