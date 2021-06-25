package dsp.antares.dsp1;

public class Reverb extends DSP {
	private static final int BUF_SIZE = 5000;
	private float alpha;
	
	public Reverb(float alpha, boolean stereo) {
		super(BUF_SIZE, stereo);
		this.alpha = alpha;
	}
	
	@Override
	public int processDS(int sample) {
		BufferSet bufSet = getCurBufferSet();
		
		int y = (int) ( (alpha * (float)sample) 
				+ (float) bufSet.getSample(BUF_SIZE - 1)
						- (alpha * (float) bufSet.getOut(BUF_SIZE - 1)) );
		
		return y;
	}
}
