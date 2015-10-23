package liuliu.custom.method.xml;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * xml解析类
 */
public class Jxml {
    private String m_strDoc;
    private NodeModel rootNode = null;
    private NodeModel headNode = null;
    private NodeModel pointer = null;

    public Jxml() {

    }

    public boolean Load(String szFileName) {

        return true;
    }

    public boolean SetDoc(String strDoc) {
        try {
            SaxDoc sax = new SaxDoc();
            SAXParser sp;
            sp = SAXParserFactory.newInstance().newSAXParser();
            sp.parse(new InputSource(new StringReader(strDoc)), sax);

            headNode = sax.getRootNode();
            rootNode = new NodeModel(true);
            rootNode.SetNext(headNode);
            headNode.SetPre(rootNode);
            pointer = rootNode;

            return true;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }

        return false;
    }

    public boolean FindElem() {
        boolean result = false;
        if (pointer == null)
            return result;

        NodeModel node = pointer.GetNext();
        if (node != null) {
            result = true;
            pointer = node;
        }
        return result;
    }

    public boolean FindElem(String szName) {

        boolean result = false;
        NodeModel node = pointer.GetNext();
        while (node != null) {
            if (node.GetTagName().equalsIgnoreCase(szName)) {
                result = true;
                pointer = node;
                break;
            }
            node = node.GetNext();
        }
        return result;
    }

    public boolean IntoElem() {
        if (pointer == null)
            return false;

        pointer = pointer.GetChildernHead();
        return true;
    }

    public boolean OutOfElem() {
        if (pointer == null)
            return false;

        pointer = pointer.GetParent();
        return true;
    }

    public String GetTagName() {
        if (pointer == null)
            return "";

        return pointer.GetTagName();
    }

    public String GetData() {
        if (pointer == null)
            return "";

        return pointer.GetData();
    }

    public String GetElemContent() {

        return "";
    }

    public float GetFloatAttrib(String szAttrib) {
        if (pointer == null)
            return 0;
        return Float.parseFloat(pointer.GetAttribute(szAttrib));
    }

    public int GetIntAttrib(String szAttrib) {
        if (pointer == null)
            return 0;

        return Integer.parseInt(pointer.GetAttribute(szAttrib));
    }

    public String GetAttrib(String szAttrib) {
        if (pointer == null)
            return "";

        return pointer.GetAttribute(szAttrib);
    }

    // Create
    public boolean Save(String szFileName) {

        return true;
    }

    public String GetDoc() {
        return m_strDoc;
    }
};