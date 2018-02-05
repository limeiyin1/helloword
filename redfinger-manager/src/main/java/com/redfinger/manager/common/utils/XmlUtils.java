package com.redfinger.manager.common.utils;

import java.io.File;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlUtils {

	@SuppressWarnings({ "unchecked" })
	public static <T> T stringToObject(String str, Class<T> valueType) {
		try {
			JAXBContext context = JAXBContext.newInstance(valueType);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object object = unmarshaller.unmarshal(new StringReader(str));
			return (T) object;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked" })
	public static <T> T stringToObject(File file, Class<T> valueType) {
		try {
			JAXBContext context = JAXBContext.newInstance(valueType);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object object = unmarshaller.unmarshal(file);
			return (T) object;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
