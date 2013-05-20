package lorian.graph.android;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

public class Util {
	
	private static final int BYTES_PER_FLOAT = Float.SIZE / 8;
	private static final int BYTES_PER_DOUBLE = Double.SIZE / 8;
	public static FloatBuffer toFloatBuffer(float[] src)
	{
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(src.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder());

		FloatBuffer Buf = byteBuffer.asFloatBuffer();
		Buf.put(src);
		Buf.flip();
		return Buf;
	}
	public static DoubleBuffer toDoubleBuffer(double[] src)
	{
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(src.length * BYTES_PER_DOUBLE).order(ByteOrder.nativeOrder());

		DoubleBuffer Buf = byteBuffer.asDoubleBuffer();
		Buf.put(src);
		Buf.flip();
		return Buf;
	}
}
