package model.Haffman_Code;

import model.Haffman_Code.haffmanTree.Bits;
import model.Haffman_Code.haffmanTree.HaffmanTree;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class EncodedMessage extends ArrayList<Byte>{

//	private ArrayList<Bits> bitsArray;
	private Bits message;
//
//	public EncodedMessage(ArrayList<Bits> bits){
//		this.bitsArray = bits;
//		convertToBytes();
//	}

	public EncodedMessage(Bits encMessage, HaffmanTree tree) {
		this.message = encMessage;
		convertToBytes(tree);
	}

	private void convertToBytes(HaffmanTree tree){
		clear();
		writeTree(tree);
		writeSize();
		writeMessage();
	}

	private void writeTree(HaffmanTree tree){
		byte[] treeInBytes = tree.toByteArray();
		for(int i = 0; i < treeInBytes.length; ++i)
			add(treeInBytes[i]);
	}

	private void writeSize(){
		byte[] sizeBytes = ByteBuffer.allocate(4).putInt(message.size()).array();
		for(int i = 0; i < sizeBytes.length; ++i)
			add(sizeBytes[i]);
	}

	private void writeMessage(){
		addAll(Arrays.asList(message.getNormalized().toByteArray()));
	}


//	private void writeMessage(String message, HashMap<Character, Bits> byteMap){
//		Bits buffer = new Bits();
//		for(Bits bits : bitsArray){
//			buffer.addAll(bits);
//		}
//		buffer.normalizeToByte();
//		addAll(buffer.toByteArray());
//	}
//
//	public ArrayList<Bits> getBitsArray() {
//		return bitsArray;
//	}
//
//	public void setBitsArray(ArrayList<Bits> bitsArray) {
//		this.bitsArray = bitsArray;
//	}
}
