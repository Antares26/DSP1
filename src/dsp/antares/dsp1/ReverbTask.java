package dsp.antares.dsp1;

public class ReverbTask extends DSPTask {
	private final static float DEFAULT_ALFA = 0.8f;
	
	public ReverbTask(FileMan fileMan, boolean stereo, 
			float alfa) {
		super(fileMan, new Reverb(alfa, stereo));
	}
	public ReverbTask(FileMan fileMan, boolean stereo) {
		this(fileMan, stereo, DEFAULT_ALFA);
	}
}
