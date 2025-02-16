import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

class entropy {
    public static void main(String[] args) {
	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String filepath=null;

		try {
		System.out.print("Enter path of file to calculate entropy of: ");
			filepath = br.readLine();
		} catch (IOException ioe) {
			System.out.println("IO error trying to read filepath!" + ioe);
			System.exit(1);
		}

		File file = new File(filepath);
		try (FileInputStream fin = new FileInputStream(file)) {
			byte fileContent[] = new byte[(int)file.length()];
				
			// read data from the file into the byte array
			fin.read(fileContent);

			// create array to keep track of the frequency of bytes
			int []frequency_array = new int[256];
			int fileContentLength = fileContent.length-1;

			// count the frequency of occuring bytes
			for(int i=0; i<fileContentLength; i++) {
				byte byteValue=fileContent[i];
				frequency_array[Byte.toUnsignedInt(byteValue)]++;
			}
					
			// calculate the entropy of the file
			double entropy = 0;
			for(int i=0;i<frequency_array.length;i++) {
				if(frequency_array[i]!=0) {
					// calculate the probability of a particular byte occuring
					double probabilityOfByte=(double)frequency_array[i]/(double)fileContentLength;

					// calculate the next value to add to the ongoing entropy value
					double value = probabilityOfByte * (Math.log(probabilityOfByte) / Math.log(2));
					entropy += value;
				}
			}
			entropy *= -1;
				
			// output the entropy value that was calculated
			DecimalFormat df = new DecimalFormat("#.#####");
			System.out.println("The entropy of this file contains: " + df.format(entropy) + " bits per byte");

		}

		catch (FileNotFoundException e) {
			System.out.println("File not found: " + e);
		}

		catch (IOException ioe) {
			System.out.println("Exception while reading file: " + ioe);
		}
    }
}
