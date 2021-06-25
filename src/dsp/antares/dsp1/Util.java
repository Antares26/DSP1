package dsp.antares.dsp1;

public class Util {
	public static int byteArrayTo32Int(int[] array, boolean littleEndian) {
		int result = 0;
		
		for (int i = 0; i < 4; i++) {
			if (littleEndian) {
				result |= ( array[i] << (8 * i) );
			} else {
				// Big Endian
				result |= ( array[i] << (8 * (3 - i)) );
			}
		}
		
		return result;
	}
	public static void IntToByteArray(int int32, byte[] targetArray, 
			boolean littleEndian) {
		
		for (int i = 0; i < 4; i++) {
			if (littleEndian) {
				targetArray[i] = (byte) ( int32 >> (8 * i) );
			} else {
				// Big Endian
				targetArray[i] = (byte) ( int32 >> (8 * (3 - i)) );
			}
		}
	}
}
