package org.framework.utils.flv;

public class ConvertFlvThread extends Thread {

	private String mencoderPath;
	
	private String ffmpegPath;
	
	private String inputFileName;
	
	private String convertFileName;
	
	@Override
	public void run() {
		ConvertFlv.getInstance().convert(getMencoderPath(),getFfmpegPath(),getInputFileName(),getConvertFileName());
	}

	public String getMencoderPath() {
		return mencoderPath;
	}

	public void setMencoderPath(String mencoderPath) {
		this.mencoderPath = mencoderPath;
	}

	public String getFfmpegPath() {
		return ffmpegPath;
	}

	public void setFfmpegPath(String ffmpegPath) {
		this.ffmpegPath = ffmpegPath;
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getConvertFileName() {
		return convertFileName;
	}

	public void setConvertFileName(String convertFileName) {
		this.convertFileName = convertFileName;
	}

}