package dsp.antares.dsp1;

public class Main {
	private static DataMan dataMan;
	
	public static void main(String[] args) {
		try {
			dataMan = ArgParser.parseArgsToData(args);
			dataMan.performOp();
			
			System.out.println("Todo listo!\nCheckea " + 
					dataMan.getOutFilePath());
		} catch (Exception ex) {
			ErrorHandler.handleError(ex);
		}
	}
}
