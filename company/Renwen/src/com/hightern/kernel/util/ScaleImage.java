package com.hightern.kernel.util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

// 生成等比例高质量缩略图
public class ScaleImage {
	private int width;
	
	private int height;
	
	private int scaleWidth;
	
	double support = 3.0;
	
	double PI = 3.14159265358978;
	
	double[] contrib;
	
	double[] normContrib;
	
	double[] tmpContrib;
	
	int startContrib, stopContrib;
	
	int nDots;
	
	int nHalfDots;
	
	public static void main(String[] args) throws Exception {
		final ScaleImage image = new ScaleImage();
		image.saveImageAsJpg("E://图片//b1272.jpg", "E://图片//scale//temp.jpg", 300, 150);
	}
	
	// fromFileStr原图片地址,saveToFileStr生成缩略图地址,formatWideth生成图片宽度,formatHeight高度
	public void saveImageAsJpg(String fromFileStr, String saveToFileStr, int formatWideth, int formatHeight) throws Exception {
		BufferedImage srcImage;
		final File saveFile = new File(saveToFileStr);
		final File fromFile = new File(fromFileStr);
		srcImage = javax.imageio.ImageIO.read(fromFile); // construct image
		final int imageWideth = srcImage.getWidth(null);
		final int imageHeight = srcImage.getHeight(null);
		int changeToWideth = 0;
		int changeToHeight = 0;
		if (imageWideth > 0 && imageHeight > 0) {
			// flag=true;
			if (imageWideth / imageHeight >= formatWideth / formatHeight) {
				if (imageWideth > formatWideth) {
					changeToWideth = formatWideth;
					changeToHeight = imageHeight * formatWideth / imageWideth;
				} else {
					changeToWideth = imageWideth;
					changeToHeight = imageHeight;
				}
			} else {
				if (imageHeight > formatHeight) {
					changeToHeight = formatHeight;
					changeToWideth = imageWideth * formatHeight / imageHeight;
				} else {
					changeToWideth = imageWideth;
					changeToHeight = imageHeight;
				}
			}
		}
		
		srcImage = imageZoomOut(srcImage, changeToWideth, changeToHeight);
		ImageIO.write(srcImage, "JPEG", saveFile);
	}
	
	public BufferedImage imageZoomOut(BufferedImage srcBufferImage, int w, int h) {
		width = srcBufferImage.getWidth();
		height = srcBufferImage.getHeight();
		scaleWidth = w;
		
		if (DetermineResultSize(w, h) == 1) { return srcBufferImage; }
		CalContrib();
		final BufferedImage pbOut = HorizontalFiltering(srcBufferImage, w);
		final BufferedImage pbFinalOut = VerticalFiltering(pbOut, h);
		return pbFinalOut;
	}
	
	/** */
	/**
	 * 决定图像尺寸
	 */
	private int DetermineResultSize(int w, int h) {
		double scaleH, scaleV;
		scaleH = (double) w / (double) width;
		scaleV = (double) h / (double) height;
		// 需要判断一下scaleH，scaleV，不做放大操作
		if (scaleH >= 1.0 && scaleV >= 1.0) { return 1; }
		return 0;
		
	} // end of DetermineResultSize()
	
	private double Lanczos(int i, int inWidth, int outWidth, double Support) {
		double x;
		
		x = (double) i * (double) outWidth / inWidth;
		
		return Math.sin(x * PI) / (x * PI) * Math.sin(x * PI / Support) / (x * PI / Support);
		
	}
	
	private void CalContrib() {
		nHalfDots = (int) (width * support / scaleWidth);
		nDots = nHalfDots * 2 + 1;
		try {
			contrib = new double[nDots];
			normContrib = new double[nDots];
			tmpContrib = new double[nDots];
		} catch (final Exception e) {
			System.out.println("init   contrib,normContrib,tmpContrib" + e);
		}
		
		final int center = nHalfDots;
		contrib[center] = 1.0;
		
		double weight = 0.0;
		int i = 0;
		for (i = 1; i <= center; i++) {
			contrib[center + i] = Lanczos(i, width, scaleWidth, support);
			weight += contrib[center + i];
		}
		
		for (i = center - 1; i >= 0; i--) {
			contrib[i] = contrib[center * 2 - i];
		}
		
		weight = weight * 2 + 1.0;
		
		for (i = 0; i <= center; i++) {
			normContrib[i] = contrib[i] / weight;
		}
		
		for (i = center + 1; i < nDots; i++) {
			normContrib[i] = normContrib[center * 2 - i];
		}
	}
	
	// 处理边缘
	private void CalTempContrib(int start, int stop) {
		double weight = 0;
		
		int i = 0;
		for (i = start; i <= stop; i++) {
			weight += contrib[i];
		}
		
		for (i = start; i <= stop; i++) {
			tmpContrib[i] = contrib[i] / weight;
		}
		
	}
	
	private int GetRedValue(int rgbValue) {
		final int temp = rgbValue & 0x00ff0000;
		return temp >> 16;
	}
	
	private int GetGreenValue(int rgbValue) {
		final int temp = rgbValue & 0x0000ff00;
		return temp >> 8;
	}
	
	private int GetBlueValue(int rgbValue) {
		return rgbValue & 0x000000ff;
	}
	
	private int ComRGB(int redValue, int greenValue, int blueValue) {
		
		return (redValue << 16) + (greenValue << 8) + blueValue;
	}
	
	// 行水平滤波
	private int HorizontalFilter(BufferedImage bufImg, int startX, int stopX, int start, int stop, int y, double[] pContrib) {
		double valueRed = 0.0;
		double valueGreen = 0.0;
		double valueBlue = 0.0;
		int valueRGB = 0;
		int i, j;
		
		for (i = startX, j = start; i <= stopX; i++, j++) {
			valueRGB = bufImg.getRGB(i, y);
			valueRed += GetRedValue(valueRGB) * pContrib[j];
			valueGreen += GetGreenValue(valueRGB) * pContrib[j];
			valueBlue += GetBlueValue(valueRGB) * pContrib[j];
		}
		
		valueRGB = ComRGB(Clip((int) valueRed), Clip((int) valueGreen), Clip((int) valueBlue));
		return valueRGB;
		
	}
	
	// 图片水平滤波
	private BufferedImage HorizontalFiltering(BufferedImage bufImage, int iOutW) {
		final int dwInW = bufImage.getWidth();
		final int dwInH = bufImage.getHeight();
		int value = 0;
		final BufferedImage pbOut = new BufferedImage(iOutW, dwInH, BufferedImage.TYPE_INT_RGB);
		
		for (int x = 0; x < iOutW; x++) {
			
			int startX;
			int start;
			final int X = (int) ((double) x * (double) dwInW / iOutW + 0.5);
			int y = 0;
			
			startX = X - nHalfDots;
			if (startX < 0) {
				startX = 0;
				start = nHalfDots - X;
			} else {
				start = 0;
			}
			
			int stop;
			int stopX = X + nHalfDots;
			if (stopX > dwInW - 1) {
				stopX = dwInW - 1;
				stop = nHalfDots + dwInW - 1 - X;
			} else {
				stop = nHalfDots * 2;
			}
			
			if (start > 0 || stop < nDots - 1) {
				CalTempContrib(start, stop);
				for (y = 0; y < dwInH; y++) {
					value = HorizontalFilter(bufImage, startX, stopX, start, stop, y, tmpContrib);
					pbOut.setRGB(x, y, value);
				}
			} else {
				for (y = 0; y < dwInH; y++) {
					value = HorizontalFilter(bufImage, startX, stopX, start, stop, y, normContrib);
					pbOut.setRGB(x, y, value);
				}
			}
		}
		
		return pbOut;
		
	}
	
	private int VerticalFilter(BufferedImage pbInImage, int startY, int stopY, int start, int stop, int x, double[] pContrib) {
		double valueRed = 0.0;
		double valueGreen = 0.0;
		double valueBlue = 0.0;
		int valueRGB = 0;
		int i, j;
		
		for (i = startY, j = start; i <= stopY; i++, j++) {
			valueRGB = pbInImage.getRGB(x, i);
			
			valueRed += GetRedValue(valueRGB) * pContrib[j];
			valueGreen += GetGreenValue(valueRGB) * pContrib[j];
			valueBlue += GetBlueValue(valueRGB) * pContrib[j];
		}
		
		valueRGB = ComRGB(Clip((int) valueRed), Clip((int) valueGreen), Clip((int) valueBlue));
		return valueRGB;
		
	}
	
	private BufferedImage VerticalFiltering(BufferedImage pbImage, int iOutH) {
		final int iW = pbImage.getWidth();
		final int iH = pbImage.getHeight();
		int value = 0;
		final BufferedImage pbOut = new BufferedImage(iW, iOutH, BufferedImage.TYPE_INT_RGB);
		
		for (int y = 0; y < iOutH; y++) {
			int startY;
			int start;
			final int Y = (int) ((double) y * (double) iH / iOutH + 0.5);
			
			startY = Y - nHalfDots;
			if (startY < 0) {
				startY = 0;
				start = nHalfDots - Y;
			} else {
				start = 0;
			}
			
			int stop;
			int stopY = Y + nHalfDots;
			if (stopY > (iH - 1)) {
				stopY = iH - 1;
				stop = nHalfDots + iH - 1 - Y;
			} else {
				stop = nHalfDots * 2;
			}
			
			if (start > 0 || stop < nDots - 1) {
				CalTempContrib(start, stop);
				for (int x = 0; x < iW; x++) {
					value = VerticalFilter(pbImage, startY, stopY, start, stop, x, tmpContrib);
					pbOut.setRGB(x, y, value);
				}
			} else {
				for (int x = 0; x < iW; x++) {
					value = VerticalFilter(pbImage, startY, stopY, start, stop, x, normContrib);
					pbOut.setRGB(x, y, value);
				}
			}
			
		}
		
		return pbOut;
		
	}
	
	int Clip(int x) {
		if (x < 0) { return 0; }
		if (x > 255) { return 255; }
		return x;
	}
}