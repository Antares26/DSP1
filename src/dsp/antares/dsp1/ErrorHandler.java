package dsp.antares.dsp1;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ErrorHandler {
	public static void handleError(Exception ex) {
		if (ex instanceof FileNotFoundException) {
			System.out.println(
					"ERROR FATAL: Imposible encontrar un archivo.");
			
			System.err.println(ex.toString());
			System.exit(0x2);
		}
		else if (ex instanceof IOException) {
			System.err.println("Error de IO: " + ex.toString());
			System.exit(0x3);
		}
		else if (ex instanceof ArgParser.ArgParserException) {
			int errCode = 
					((ArgParser.ArgParserException)ex).getErrorCode();
			System.err.println
				("FATAL ERROR: Error en argumentos ingresados");
			
			switch(errCode) {
			case ArgParser.ArgParserException.ERR_INVALID_SYNTAX:
				System.err.println("Sintaxis inválida: \n\t( " +
						ex.getMessage() + " )");
				break;
			default:
				System.err.println("Error desconocido.");
			}
		}
		else if (ex instanceof DataMan.DataManException) {
			int errCode =
					((DataMan.DataManException)ex).getErrorCode();
			switch(errCode) {
			case DataMan.DataManException.ERR_INVALID_OP:
				System.err.println("Operación inválida.");
			case DataMan.DataManException.ERR_INVALID_OPTS_REV:
				System.err.println("Opciones de Reverb inválidas.");
			case DataMan.DataManException.ERR_INVALID_OPTS_WAH:
				System.err.println("Opciones de Wah inválidas.");
			
			default:
				System.err.println("Más info: [ " + ex.getMessage() + " ]");
			}
		}
	}
}
