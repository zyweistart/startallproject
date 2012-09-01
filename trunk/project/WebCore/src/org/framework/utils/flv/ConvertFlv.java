package org.framework.utils.flv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.framework.utils.StringUtils;

public class ConvertFlv {
	
	private static ConvertFlv instance;

	public static ConvertFlv getInstance() {
		if(instance==null){
			instance = new ConvertFlv();
		}
		return instance;
	}
	/**
	 * @param mencoderPath
	 * 视频转换工具路径
	 * @param ffmpegPath
	 * 视频图片截图工具
	 * @param inputFileName
	 * 视频原名称
	 * @param convertFileName
	 * 视频转换后的名称
	 */
	public synchronized void convert(String mencoderPath,String ffmpegPath,String inputFileName, String convertFileName){
		// 统一路径定位符
		mencoderPath=StringUtils.unifiedPath(mencoderPath);
		ffmpegPath=StringUtils.unifiedPath(ffmpegPath);
		inputFileName=StringUtils.unifiedPath(inputFileName);
		convertFileName=StringUtils.unifiedPath(convertFileName);
		//分析不包含文件名的输出路径
		String savePath = convertFileName.substring(0,convertFileName.lastIndexOf("\\") + 1);
		// 分析输出的文件名
		String saveFlvFileName = convertFileName.substring(convertFileName.lastIndexOf("\\") + 1);
		// 继续分析得出不包含扩展名的文件名
		String fileNameNotExt = saveFlvFileName.substring(0,saveFlvFileName.lastIndexOf("."));
		/**转换**/
		List<String> convertFlvCmd=new ArrayList<String>();
		convertFlvCmd.add(mencoderPath);
		convertFlvCmd.add("-vf");
		convertFlvCmd.add("scale=800:600");
		convertFlvCmd.add("-ffourcc");
		convertFlvCmd.add("FLV1");
		convertFlvCmd.add("-of");
		convertFlvCmd.add("lavf");
		convertFlvCmd.add("-lavfopts");
		convertFlvCmd.add("i_certify_that_my_video_stream_does_not_use_b_frames");
		convertFlvCmd.add("-ovc");
		convertFlvCmd.add("lavc");
		convertFlvCmd.add("-lavcopts");
		convertFlvCmd.add("vcodec=flv:vbitrate=200");
		convertFlvCmd.add("-srate");
		convertFlvCmd.add("22050");
		convertFlvCmd.add("-oac");
		convertFlvCmd.add("lavc");
		convertFlvCmd.add("-lavcopts");
		convertFlvCmd.add("acodec=mp3:abitrate=56");
		convertFlvCmd.add(inputFileName);
		convertFlvCmd.add("-o");
		convertFlvCmd.add(convertFileName);
		/**截图**/
		List<String>convertPicCmd=new ArrayList<String>();
		convertPicCmd.add(ffmpegPath);
		convertPicCmd.add("-i");
		convertPicCmd.add(inputFileName);
		convertPicCmd.add("-y");
		convertPicCmd.add("-f");
		convertPicCmd.add("image2");
		convertPicCmd.add("-ss");
		convertPicCmd.add("8");
		convertPicCmd.add("-t");
		convertPicCmd.add("0.001");
		convertPicCmd.add("-s");
		convertPicCmd.add("800x600");
		convertPicCmd.add(savePath + fileNameNotExt+ ".jpg");
		try {
			Runtime.getRuntime().exec(StringUtils.listToString(convertFlvCmd, " "));
			Runtime.getRuntime().exec(StringUtils.listToString(convertPicCmd, " "));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		String Mencoder="D:\\Temp\\EclipseJavaEE\\framework\\WebRoot\\Tools\\mencoder\\mencoder.exe";
		String Ffmpeg="D:\\Temp\\EclipseJavaEE\\framework\\WebRoot\\Tools\\ffmpeg\\ffmpeg.exe";
		ConvertFlvThreadProxy.convert(Mencoder,Ffmpeg,"d:\\1.mp4", "d:\\11.flv");
	}

}