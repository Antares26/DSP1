package dsp.antares.dsp1;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ArgParser {
	
	public static DataMan parseArgsToData(String[] args) 
		throws ArgParserException, FileNotFoundException, 
			IOException{
		
		if (args.length < 3)
			throw new 
			ArgParserException(ArgParserException.ERR_INVALID_SYNTAX, 
					"Uso: dsp1 <inputFile> <dsp>" + 
			" <outputFile> (stereo=true) (dspOptions=default)");
		
		String inFilePath = new String(args[0]);
		String outFilePath = new String(args[2]);
		String mode = new String(args[1]);
		boolean stereo;
		String dspOptions;
		
		if (args.length >= 4)
			stereo = args[3].equalsIgnoreCase("true");
		else
			stereo = true;
		
		if (args.length >= 5)
			dspOptions = args[4];
		else
			dspOptions = "default";
		
		return new DataMan(inFilePath, outFilePath, mode, stereo,
				dspOptions);
	}
	public static class ArgParserException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private int errCode;
		public static final int ERR_INVALID_SYNTAX = 1;
		
		public ArgParserException(int errCode, String errMsg) {
			super(errMsg);
			
			this.errCode = errCode;
		}
		
		public int getErrorCode() {
			return errCode;
		}
	}
}
