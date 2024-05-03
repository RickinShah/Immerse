package spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class HlsFormat {
	private String videoResolution;
	private String videoBitrate;
	private String audioBitrate;
	private String resolutionName;
}
