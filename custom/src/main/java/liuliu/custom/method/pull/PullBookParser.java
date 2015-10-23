package liuliu.custom.method.pull;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 未使用的解析xml的pull方法
 * Created by LiuWeiJie on 2015/8/7 0007.
 * Email:1031066280@qq.com
 */
public class PullBookParser {
    //根据输入流解析文件
    public List<Book> parse(InputStream is) throws Exception {
        List<Book> books = null;
        Book book = null;
        XmlPullParser parser = Xml.newPullParser(); //由android.util.Xml创建一个XmlPullParser实例
        parser.setInput(is, "UTF-8");               //设置输入流 并指明编码方式

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    books = new ArrayList<Book>();
                    break;
                case XmlPullParser.START_TAG:
                    if (!parser.getName().equals("InvokeReturn")) {
                        if (parser.getName().equals("Success")) {
                            parser.next();
                            Log.i("json-------", "Success:" + parser.getText().toString());
                        } else if (parser.getName().equals("Time")) {
                            parser.next();
                            Log.i("json-------", "Time:" + parser.getText().toString());
                        } else if (parser.getName().equals("Object")) {
                            book = new Book();
                        } else {
                            if (parser.getName().equals("CompanyId")) {
                                parser.next();
                                Log.i("json-------", "CompanyId" + parser.getText().toString());
                            } else if (parser.getName().equals("CompanyPassword")) {
                                parser.next();
                                Log.i("json-------", "CompanyPassword" + parser.getText().toString());
                            }
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("Object")) {
                        books.add(book);
                        book = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return books;
    }

    //通过List生成xml
    public String serialize(List<Book> books) throws Exception {
        XmlSerializer serializer = Xml.newSerializer(); //由android.util.Xml创建一个XmlSerializer实例
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);   //设置输出方向为writer
        serializer.startDocument("UTF-8", true);
        serializer.startTag("", "books");
        for (Book book : books) {
            serializer.startTag("", "book");
//            serializer.attribute("", "id", book.getId() + "");
            serializer.startTag("", "name");
//            serializer.text(book.getName());
            serializer.endTag("", "name");
            serializer.endTag("", "book");
        }
        serializer.endTag("", "books");
        serializer.endDocument();

        return writer.toString();
    }
}
