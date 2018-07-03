package model.Haffman_Code.haffmanTree;

import model.Haffman_Code.exception.BitsNotNormalizedException;

import java.util.ArrayList;
import java.util.Collections;

public class Bits extends ArrayList<Boolean> {

	public void add(int aByte) {
		String bits = String.format("%8s", Integer.toBinaryString(aByte & 0xFF)).replace(' ', '0');
		for(int i = 0; i < bits.length(); ++i)
			add(bits.charAt(i) == '1');
	}

	public Bits getNormalized() {
		Bits copy = new Bits();
		copy.addAll(this);
		Collections.copy(copy, this);
		return copy.normalize();
	}

	public Bits normalize(){
		while (!normalized())
			add(false);
		return this;
	}

	public Bits forwardNormalize(){
		while (!normalized())
			add(0, false);
		return this;
	}

	public boolean normalized() {
		return size() % 8 == 0;
	}

	public Byte[] toByteArray() {
		try {
			if (!normalized())
				throw new BitsNotNormalizedException();

			Byte[] byteArr = new Byte[size() / 8];
			for (int i = 0; i < size(); ++i) {
				if(byteArr[i / 8] == null)
					byteArr[i / 8] = (byte) 0;
				byteArr[i / 8] = (byte)(byteArr[i / 8] * 2 + (get(i) ? 1 : 0));
			}
			return byteArr;
		} catch (BitsNotNormalizedException e) {
			System.err.println("-----");
			e.printStackTrace();
		}
		return null;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(boolean b : this)
			sb.append(b ? 1 : 0);
		return sb.toString();
	}
}
