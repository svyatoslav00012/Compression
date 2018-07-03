package test;

import model.Compressor;
import model.Haffman_Code.HaffmanCompressor;

import java.io.*;

public class HaffmanTest {

	public void test() {
		try {
			Compressor haffman = new HaffmanCompressor();
			String in = readToString();
			if (in == null) {
				System.err.println("TEST FAILED. Error reading file");
				return;
			}
			Byte[] outArr = haffman.encode(in);
			System.out.println("Input length : " + in.length() * 2);
			System.out.println("Output length : " + outArr.length);

			FileOutputStream out = new FileOutputStream("out.haffm");
			for (int i = 0; i < outArr.length; ++i)
				out.write(outArr[i]);
			out.flush();

			BufferedWriter bufOut = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream("decode.txt"),
							"UTF-8"
					)
			);
			String outData = haffman.decode(outArr);
			bufOut.write(outData);
			bufOut.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	private String readToString() {
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							new FileInputStream("input.txt"), "UTF-8"
					)
			);
			int b;
			StringBuffer sb = new StringBuffer();
			while ((b = in.read()) != -1)
				sb.append((char) b);
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
