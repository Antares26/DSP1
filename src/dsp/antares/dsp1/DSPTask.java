package dsp.antares.dsp1;

import java.io.IOException;

public abstract class DSPTask {
	protected int nextSample;
	protected FileMan fileMan;
	protected DSP dsp;
	
	public DSPTask(FileMan fileMan, DSP dsp) {
		this.fileMan = fileMan;
		this.dsp = dsp;
	}
	
	public void doTask() throws IOException {
		while (fileMan.moreData()) {
			nextSample = fileMan.getNext32Int();
			nextSample = dsp.execDSP(nextSample);
			fileMan.writeNext32Int(nextSample);
		}
	}
}
