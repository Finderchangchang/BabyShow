package liuliu.custom.method.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 解析xml的相关类
 * Created by LiuWeiJie on 2015/7/29 0029.
 * Email:1031066280@qq.com
 */
public class SaxDoc extends DefaultHandler {
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (curElem != null) {
            curElem.SetData(curElem.GetData() + new String(ch, start, length));
        }

        super.characters(ch, start, length);
    }

    private NodeModel topElem = null, curElem = null;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        if (topElem == null) {
            topElem = CreateDataElem(localName);
            topElem.SetParent(null);

            for (int i = 0; i < attributes.getLength(); i++) {
                topElem.SetAttribute(attributes.getQName(i),
                        attributes.getValue(i));
            }

            curElem = topElem;
        } else {
            NodeModel tempElem;
            tempElem = CreateDataElem(localName);
            tempElem.SetParent(curElem);
            for (int i = 0; i < attributes.getLength(); i++) {
                tempElem.SetAttribute(attributes.getQName(i),
                        attributes.getValue(i));
            }
            curElem.AddChild(tempElem);
            curElem = tempElem;
        }

        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (curElem != null)
            curElem = curElem.GetParent();

        super.endElement(uri, localName, qName);
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    public NodeModel getRootNode() {
        return topElem;
    }

    public NodeModel CreateDataElem(String tagName) {
        NodeModel elem = new NodeModel();
        elem.SetTagName(tagName);
        return elem;
    }
}
