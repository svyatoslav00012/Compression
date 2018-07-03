package model;

public interface Compressor {

	Byte[] encode(String message);

	String decode(Byte[] encMessage);

}
