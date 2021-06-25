package dsp.antares.dsp1;

public abstract class DSP {
	//private static final int sampleCountStop = 2446377;
	private BufferSet bufferLeft;
	private BufferSet bufferRight;
	
	private int bufSize;
	private boolean stereo;
	private boolean curLeft;
	
	// DEBUG
	private final boolean debug = true;
	
	// SAMPLE_CM en kSa
	private final double SAMPLE_CM = 100000;
	private final double SAMPLE_K_CM = SAMPLE_CM / 1000;
	
	private long cur_ms;
	private int sampleCount;
	
	public DSP(int bufSize, boolean stereo) {
		this.bufSize = bufSize;
		this.stereo = stereo;
		this.curLeft = true;
		
		bufferLeft = new BufferSet(bufSize);
		if (stereo)
			bufferRight = new BufferSet(bufSize);
		
		//DEBUG
		sampleCount = 0;
		cur_ms = System.currentTimeMillis();
	}
	
	public int execDSP(int sample) {
		int y = processDS(sample);
		rollBuffer(sample, y);
		
		//DEBUG
		if (debug) {
			sampleCount++;
			
			if ((sampleCount % SAMPLE_CM) == 0) {
				long newTime = System.currentTimeMillis();
				int rate = 
					(int) ( SAMPLE_K_CM / 
							(
									((double)(newTime - cur_ms))
									/ ((double)1000)
							) );
				cur_ms = newTime;
				System.out.println("Current rate: " + rate + " kSa/s");
			}
		}
		
		return y;
	}
	protected abstract int processDS(int sample);
	
	protected final BufferSet getCurBufferSet() {
		if (curLeft)
			return bufferLeft;
		else
			return bufferRight;
	}
	private void rollBuffer(int currentSample, int currentY) {
		BufferSet curSet = getCurBufferSet();
		
		curSet.rollBuffer(currentSample, currentY);
		flipChannel();
	}
	private void flipChannel() {
		if (!stereo)
			return;
		curLeft = !curLeft;
	}
	
	protected final class BufferSet {
		private int[] sampleBuffer;
		private int[] outBuffer;
		
		public BufferSet(int bufSize) {
			sampleBuffer = new int[bufSize];
			outBuffer = new int[bufSize];
			
			for (int i = 0; i < bufSize; i++) {
				sampleBuffer[i] = 0;
				outBuffer[i] = 0;
			}
		}
		
		public int getSample(int index) {
			return sampleBuffer[index];
		}
		public int getOut(int index) {
			return outBuffer[index];
		}
		
		private void rollBuffer(int currentSample, int currentY) {
			
			for (int i = (bufSize - 1); i > 0; i--) {
				sampleBuffer[i] = sampleBuffer[i - 1];
				outBuffer[i] = outBuffer[i - 1];
			}
			sampleBuffer[0] = currentSample;
			outBuffer[0] = currentY;
		}
	}
}
