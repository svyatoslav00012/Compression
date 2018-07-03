package model.Haffman_Code.haffmanTree;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HaffmanTree implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<Node> leafs;
	private ArrayList<Character> chars;

	public HaffmanTree(ArrayList<Pair<Character, Integer>> chars) {
		this.chars = new ArrayList<>();
		leafs = new ArrayList<>();
		for (Pair<Character, Integer> p : chars) {
			this.chars.add(p.getKey());
			this.leafs.add(new Node(p.getValue()));
		}
		build();
	}

	public char getChar(Node n) {
		int ind = leafs.indexOf(n);
		if (ind == -1) {
			System.err.println("NO SUCH NODE IN LEAFS");
			return '~';
		}
		return chars.get(ind);
	}

	public void build() {
		PriorityQueue<Node> q = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				if (o1.getPossibility() > o2.getPossibility())
					return 1;
				else if (o1.getPossibility() < o2.getPossibility())
					return -1;
				return 0;
			}
		});
		for (Node l : leafs)
			q.add(l);
		while (q.size() > 1) {
			Node r = q.poll();
			Node l = q.poll();
			Node parent = new Node(l, r);

			q.add(parent);
		}

	}

	public HashMap<Character, Bits> getByteMap() {
		HashMap<Character, Bits> m = new HashMap<>(chars.size());
		for (int i = 0; i < chars.size(); ++i) {
			m.put(chars.get(i), getBits(i));
		}
		return m;
	}

	public HashMap<Bits, Character> getCharacterMap() {
		HashMap<Bits, Character> m = new HashMap<>(chars.size());
		for (int i = 0; i < chars.size(); ++i) {
			m.put(getBits(i), chars.get(i));
		}
		return m;
	}

	private Bits getBits(int leafIndex) {
		Bits bits = new Bits();
		Node n = leafs.get(leafIndex);
		while (n.getParent() != null) {
			bits.add(0, n.isOne());
			n = n.getParent();
		}
		return bits;
	}

	public Node getRoot() {
		return getRoot(leafs.get(0));
	}

	public Node getRoot(Node root) {
		return root.getParent() == null ? root : getRoot(root.getParent());
	}

	public byte[] toByteArray() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(this);
			out.flush();
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException ignored) {}
		}
		return null;
	}

	public ArrayList<Node> getLeafs() {
		return leafs;
	}

	public void setLeafs(ArrayList<Node> leafs) {
		this.leafs = leafs;
	}

	public ArrayList<Character> getChars() {
		return chars;
	}

	public void setChars(ArrayList<Character> chars) {
		this.chars = chars;
	}
}
