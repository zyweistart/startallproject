package org.framework.utils.flv;

public class ConvertFlvThreadProxy {
	
	public static void convert(String mencoderPath,String ffmpegPath,String inputFileName, String convertFileName) {
		ConvertFlvThread convertThread = new ConvertFlvThread();
		convertThread.setMencoderPath(mencoderPath);
		convertThread.setFfmpegPath(ffmpegPath);
		convertThread.setInputFileName(inputFileName);
		convertThread.setConvertFileName(convertFileName);
		convertThread.start();
	}

}
