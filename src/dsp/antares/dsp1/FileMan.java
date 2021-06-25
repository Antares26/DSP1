package dsp.antares.dsp1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileMan {
	private String inFilePath, outFilePath;
	private File inFile, outFile;
	
	FileInputStream inFileS;
	FileOutputStream outFileS;
	
	int[] buffer; int bufIndex;
	private final int BUF_BYTE_SIZE = 4; // 32 Bits
	private boolean endOfFile;
	
	public FileMan(String inputFilePath, String outputFilePath) 
			throws FileNotFoundException {
		inFilePath = inputFilePath;
		outFilePath = outputFilePath;
		
		inFile = new File(inFilePath);
		outFile = new File(outFilePath);
		
		try {
			inFileS = new FileInputStream(inFile);
			outFileS = new FileOutputStream(outFile);
		} catch (FileNotFoundException ex) {
			throw ex;
		}
		
		buffer = new int[BUF_BYTE_SIZE];
		bufIndex = 0;
		endOfFile = false;
	}
	
	public boolean moreData() {
		return !endOfFile;
	}
	
	public int getNext32Int() throws IOException {
		for(int i = 0; i < 4; i++) {
			if (!readNextByte()) {
				endOfFile = true;
				break;
			}
		}
		return Util.byteArrayTo32Int(buffer, true);
	}
	public void writeNext32Int(int data) throws IOException {
		byte[] bufData = new byte[4];
		Util.IntToByteArray(data, bufData, true);
		
		try {
			outFileS.write(bufData);
		} catch (IOException ex) {
			throw ex;
		}
	}
	public void closeAll() throws IOException {
		inFileS.close();
		outFileS.close();
	}
	
	private boolean readNextByte() throws IOException {
		try {
			if (inFileS.available() > 0) {
				if (bufIndex == BUF_BYTE_SIZE)
					bufIndex = 0;
				
				buffer[bufIndex] = /*(byte)*/inFileS.read();
				
				bufIndex++;
				return true;
			}
		}
		catch (IOException ex) {
			throw ex;
		}
		return false;
	}
}
