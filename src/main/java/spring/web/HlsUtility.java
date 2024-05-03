package spring.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import spring.model.HlsFormat;

public class HlsUtility {

	public List<HlsFormat> getAllResolutions(String resolution) {
		List<HlsFormat> hlsFormats = new ArrayList<HlsFormat>();
		
		hlsFormats.add(new HlsFormat("1920x1080", "5000k", "192k", "1080p"));
		hlsFormats.add(new HlsFormat("1280x720", "2500k", "128k", "720p"));
		hlsFormats.add(new HlsFormat("854x480", "1000k", "96k", "480p"));
		hlsFormats.add(new HlsFormat("640x360", "800k", "96k", "360p"));
		hlsFormats.add(new HlsFormat("320x240", "500k", "64k", "240p"));
		hlsFormats.add(new HlsFormat("256x144", "400k", "64k", "144p"));
		
		while(true) {
			if(!hlsFormats.get(0).getResolutionName().equals(resolution))
				hlsFormats.remove(0);
			else
				break;
		}
		
		return hlsFormats;
	}
	
	public String getMaxVideoResolution(String videoFile) throws IOException {
		String resolution = null;
			ProcessBuilder builder = new ProcessBuilder(new String[] {"ffprobe", "-v", "error", "-select_streams", "v:0", "-show_entries", "stream=height", "-of", "default=nw=1:nk=1", videoFile}).redirectErrorStream(true);
			Process process = builder.start();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			if ((resolution = reader.readLine()) != null) {
				System.out.println("Max Video Resolution: " + resolution + "p");
			}
		
			return resolution + "p";
	}
	
	public void createHls(String inputFile, String outputFolder, String uuid,List<HlsFormat> hlsFormats, String pathToScript) throws IOException {
		for(HlsFormat hls : hlsFormats) {
			new File(outputFolder + '/' + uuid + '/' + hls.getResolutionName()).mkdirs();

			System.out.println(hls.getResolutionName() + " processing....");
			ProcessBuilder builder = new ProcessBuilder(new String[] {"sh", pathToScript, inputFile, outputFolder, uuid, hls.getVideoResolution(), hls.getVideoBitrate(), hls.getAudioBitrate(), hls.getResolutionName()}).redirectErrorStream(true);
			Process process = builder.start();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String s = null;
			
			while ((s = reader.readLine()) != null) {
				System.out.println(s);
			}
			System.out.println(hls.getResolutionName() + " completed!\n");
		}
	}
	
	public String getDirectoryName(String outputFolder) {
		Boolean folderExists = true;
		String uuid = null;
		do {
			uuid = UUID.randomUUID().toString();
			folderExists = new File(outputFolder + '/' + uuid).exists();
			System.out.println("Directory Exists!");
		} while(folderExists);
		
		return uuid;
		
	}


}
