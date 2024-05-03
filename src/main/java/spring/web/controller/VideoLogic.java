package spring.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import spring.model.HlsFormat;
import spring.model.NetflixModel;
import spring.model.Resolution;
import spring.model.netflix_titles;
import spring.model.dao.GenericDao;
import spring.web.HlsUtility;

@Controller
public class VideoLogic {
	@Autowired
	GenericDao dao;
	
	@PostMapping("addVideo")
	public String generateHlsAndInsertInfo(@RequestParam("vid") String vid, @RequestParam("vname") String vname, HttpServletRequest request) throws IOException {
    	HlsUtility hlsUtility = new HlsUtility();

    	String image = "/data/Images/" + vname + ".jpg";
    	String outputFolder = "/data/Immerse/Videos/";
    	String inputFile = "/data/Trailers/" + vname + ".webm";

    	String pathToScript = request.getServletContext().getRealPath("/script/hls_implementation.sh");
		String uuid = hlsUtility.getDirectoryName(outputFolder);

		String maxResolution = hlsUtility.getMaxVideoResolution(inputFile);
		List<HlsFormat> hlsFormats = hlsUtility.getAllResolutions(maxResolution);
		List<Resolution> resolutions = new ArrayList<Resolution>();
		hlsUtility.createHls(inputFile, outputFolder, uuid, hlsFormats, pathToScript);

		netflix_titles getNet = dao.get(new netflix_titles(), vid);
		NetflixModel netflix = new NetflixModel(
				uuid,
				getNet.getType(),
				getNet.getTitle(),
				getNet.getDirector(),
				getNet.getCast(),
				getNet.getCountry(),
				getNet.getDate_added(),
				getNet.getRelease_year(),
				getNet.getRating(),
				getNet.getDuration(),
				getNet.getListed_in(),
				getNet.getDescription(),
				resolutions
		);
		for(HlsFormat hls : hlsFormats) {
			resolutions.add(new Resolution(hls.getResolutionName(), netflix));
		}
		
		
		Files.move(Paths.get(image), Paths.get(outputFolder + uuid + "/poster.jpg"), StandardCopyOption.REPLACE_EXISTING);
		
		dao.insert(netflix);

		return "redirect: /Immerse/videologic";
	}


}
