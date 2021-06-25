package dsp.antares.dsp1;

import java.io.FileNotFoundException;
import java.io.IOException;

public class DataMan {
	private boolean stereo;
	
	private FileMan fileMan;
	private String inFilePath, outFilePath;
	private String op, dspOptions;
	private DSPTask dspTask;
	
	public static final String MODE_REVERB = "reverb";
	public static final String MODE_WAH = "wah";
	
	private static final String DSPOPTIONS_DELIMITER = "-";
	
	public DataMan(String inFilePath, String outFilePath,
			String op, boolean stereo, String dspOptions)
		throws FileNotFoundException, IOException {
		
		this.stereo = stereo;
		this.inFilePath = inFilePath;
		this.outFilePath = outFilePath;
		this.op = op;
		this.dspOptions = dspOptions;
		this.dspTask = null;
		
		fileMan = new FileMan(inFilePath, outFilePath);
	}
	
	public String getOutFilePath() {
		return outFilePath;
	}
	
	public void performOp() throws DataManException, IOException {
		buildTask();
		dspTask.doTask();
	}
	
	private void buildTask() throws 
		DataManException, IOException {
		
		if (op.equalsIgnoreCase(MODE_REVERB)) {

			if (!dspOptions.equalsIgnoreCase("default")) {
				float alfa;
				try {
					alfa = Float.parseFloat(dspOptions);
				} catch (NumberFormatException ex) {
					throw new DataManException(
							DataManException.ERR_INVALID_OPTS_REV,
							ex.toString());
				}
				
				dspTask = new ReverbTask(fileMan, stereo, alfa);
			}
			else
				dspTask = new ReverbTask(fileMan, stereo);
			
			System.out.println("Aplicando Reverb a " + inFilePath 
					+ " y guardando en " + outFilePath + " ...");
		}
		else if (op.equalsIgnoreCase(MODE_WAH)) {
			if (!dspOptions.equalsIgnoreCase("default")) {
				String[] opts = 
						dspOptions.split(DSPOPTIONS_DELIMITER);
				
				int f0, sf; float Q;
				try {
					f0 = Integer.parseInt(opts[0]);
					sf = Integer.parseInt(opts[1]);
					Q = Float.parseFloat(opts[2]);
				} catch (NumberFormatException ex) {
					throw new DataManException(
							DataManException.ERR_INVALID_OPTS_WAH,
							ex.toString());
				}
				
				dspTask = new WahTask(fileMan, stereo,
						f0, sf, Q);
			}
			else
				dspTask = new WahTask(fileMan, stereo);
			
			System.out.println("Aplicando Wah a " + inFilePath 
					+ " y guardando en " + outFilePath + " ...");			
		}
		else 
			throw new DataManException(
					DataManException.ERR_INVALID_OP,
					"Error: Modo Inválido. Entra reverb ó wah");
	}
	
	public class DataManException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int errCode;
		
		public static final int ERR_INVALID_OPTS_REV = 1,
			ERR_INVALID_OPTS_WAH = 2, ERR_INVALID_OP = 3;
		
		public DataManException(int errCode, String msg) {
			super(msg);
			this.errCode = errCode;
		}
		
		public int getErrorCode() {
			return errCode;
		}
	}
}
