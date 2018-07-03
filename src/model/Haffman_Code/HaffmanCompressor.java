package model.Haffman_Code;

import javafx.util.Pair;
import model.Compressor;
import model.Haffman_Code.haffmanTree.Bits;
import model.Haffman_Code.haffmanTree.HaffmanTree;
import model.Haffman_Code.haffmanTree.Node;

import java.nio.ByteBuffer;
import java.util.*;

public class HaffmanCompressor implements Compressor {

	/**
	 *	encode to Haffman tree + size (count of bits) + message bits (in bytes)
	 * @param message
	 * @return encoded message in Byte[] array
	 */
	@Override
	public Byte[] encode(String message) {
		HaffmanTree tree = new HaffmanTree(countInstanses(message));
		HashMap<Character, Bits> byteMap = tree.getByteMap();
		System.out.println("DIFFERENT CHARS : " + byteMap.size());
		for(Character c : byteMap.keySet())
			System.out.println(c + " : " + byteMap.get(c));
		Bits encMessage = new Bits();
		for(int i = 0; i < message.length(); ++i)
			encMessage.addAll(byteMap.get(message.charAt(i)));
		//System.out.println("MES : " + encMessage);
		EncodedMessage encodedMessage = new EncodedMessage(encMessage, tree);
		return encodedMessage.toArray(new Byte[encodedMessage.size()]);
	}

	@Override
	public String decode(Byte[] encMessage) {
		HaffmanTree tree =
		Bits bits = new Bits();
		int size = bytes2Int(Arrays.copyOfRange(encMessage, 0, 4));
		for(int i = 4; i < encMessage.length; ++i)
			bits.add(encMessage[i]);
		System.out.println("SIZE = " + size + " ACTUAL = " + bits.size());
		//System.out.println("MES1 : " + bits);
		Node n = tree.getRoot();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < size; ++i){
			if(bits.get(i)) {
				if (n.getRight() == null) {
					sb.append(tree.getChar(n));
					n = tree.getRoot();
				}
				n = n.getRight();
			} else {
				if (n.getLeft() == null) {
					sb.append(tree.getChar(n));
					n = tree.getRoot();
				}
				n = n.getLeft();
			}

		}
		if(n != null)
			sb.append(tree.getChar(n));

		return sb.toString();
	}

	private ArrayList<Pair<Character, Integer>> countInstanses(String message){
		HashMap<Character, Integer> chars = new HashMap<>();
		for(int i = 0; i < message.length(); ++i) {
			char c = message.charAt(i);
			Integer ct = chars.get(c);
			if(ct == null)
				ct = 0;
			chars.put(c, ct + 1);
		}
		return mapToSortedList(chars);
	}

	ArrayList<Pair<Character, Integer>> mapToSortedList(Map<Character, Integer> m){
		Set<Character> keys = m.keySet();
		ArrayList<Pair<Character, Integer>> ret = new ArrayList<>();
		for(Character c : keys){
			ret.add(new Pair<>(c, m.get(c)));
		}
		Collections.sort(ret, new Comparator<Pair<Character, Integer>>() {
			@Override
			public int compare(Pair<Character, Integer> o1, Pair<Character, Integer> o2) {
				if(o1.getValue() > o2.getValue())
					return 1;
				if(o1.getValue() < o2.getValue())
					return -1;
				return 0;
			}
		});
		return ret;
	}

	private int bytes2Int(Byte[] arr){
		byte[] primArr = new byte[arr.length];
		for(int i = 0; i < primArr.length; ++i)
			primArr[i] = arr[i];
		ByteBuffer bb = ByteBuffer.wrap(primArr);
		return bb.getInt();
	}

}
