package Util;

import org.junit.Test;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;


//@Controller
public class XmlToBeanDemo {

    @SuppressWarnings("unchecked")
    public static <T> T converyToJavaBean(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }
    @Test
    public void convertBean() {

        Girl root = converyToJavaBean(resultStr, Girl.class);
        System.out.println(root.toString());
    }
    private String  resultStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>  \n" +
            "<root>  \n" +
            "<girl><name>lily</name><cup>D</cup><age>18</age></girl>  \n" +
            "</root>  " ;

}
