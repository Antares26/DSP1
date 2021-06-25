package dsp.antares.dsp1;

public class Wah extends DSP {
	private int centerFreq, samplingFreq;
	private float Rp;
	
	private static final int BUF_SIZE = 2;
	
	public Wah(int centerFreq, int samplingFreq, float Rp, boolean stereo) {
		super(BUF_SIZE, stereo);
		
		this.centerFreq = centerFreq;
		this.samplingFreq = samplingFreq;
		this.Rp = Rp;
	}
	
	@Override
	public int processDS(int sample){
		BufferSet bufSet = getCurBufferSet();
		
		int y = (int) ( 
				sample - bufSet.getSample(BUF_SIZE - 1)
				+ 2 * Rp * Math.cos(2*Math.PI*centerFreq*(1 / samplingFreq))
					* bufSet.getOut((BUF_SIZE - 1)/2)
				+ Math.pow(Rp,  2) * bufSet.getOut(BUF_SIZE - 1) 
				);
		
		return y;
	}
}
