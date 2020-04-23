package lestrange;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SimpleClassLoader extends ClassLoader {
	private final byte[] bytecode;

	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		return defineClass(name, bytecode, 0, bytecode.length);
	}

	public SimpleClassLoader(InputStream inputStream) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		int nextValue = 0;
		try {
			while ((nextValue = inputStream.read()) != -1) {
				byteStream.write(nextValue);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		bytecode = byteStream.toByteArray();
	}
}