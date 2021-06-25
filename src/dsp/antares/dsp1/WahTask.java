package dsp.antares.dsp1;

public class WahTask extends DSPTask {
	private static final int DEFAULT_F0 = 3000,
			DEFAULT_SF = 44100;
	private static final float DEFAULT_Q = 0.1f;
	
	public WahTask(FileMan fileMan, boolean stereo,
			int f0, int samplingFreq, float Q) {
		super(fileMan, new Wah(f0, samplingFreq, Q, stereo));
	}
	public WahTask(FileMan fileMan, boolean stereo) {
		this(fileMan, stereo, DEFAULT_F0, DEFAULT_SF, DEFAULT_Q);
	}
}
