package liuliu.custom.method.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析xml用到的model
 * Created by LiuWeiJie on 2015/7/29 0029.
 * Email:1031066280@qq.com
 */
public class NodeModel {
    private String strTagName = ""; // <TagName></TagName>
    private String strData = ""; // <![CDATA[ ]]>
    private NodeModel _parent = null;
    private NodeModel _next = null;
    private NodeModel _pre = null;
    private NodeModel _child_head = null;
    private boolean isheadnode = false;

    private Map<String, String> mapAttribute = new HashMap<String, String>();
    protected ArrayList<NodeModel> _childern = new ArrayList<NodeModel>();

    public NodeModel() {
        _child_head = new NodeModel(true);
        _childern.add(_child_head);

    }

    public NodeModel(boolean headnode) {
        if (!headnode) {
            _child_head = new NodeModel();
            return;
        }
        isheadnode = headnode;
    }

    public boolean IsHeadNode() {
        return isheadnode;
    }

    public void SetTagName(String tag) {
        strTagName = tag;
    }

    public String GetTagName() {
        return strTagName;
    }

    public void SetData(String data) {
        strData = data;
    }

    public String GetData() {
        return strData;
    }

    public void SetParent(NodeModel parent) {
        _parent = parent;
    }

    public NodeModel GetParent() {
        return _parent;
    }

    public NodeModel GetNext() {
        return _next;
    }

    public void SetNext(NodeModel next) {
        _next = next;
    }

    public NodeModel GetPre() {
        return _pre;
    }

    public void SetPre(NodeModel pre) {
        _pre = pre;
    }

    public NodeModel GetChildernHead() {
        return _child_head;
    }

    public void SetAttribute(String name, String value) {
        mapAttribute.put(name, value);
    }

    public String GetAttribute(String name) {

        return mapAttribute.get(name);
    }

    public void AddChild(NodeModel child) {
        NodeModel tail = _childern.get(_childern.size() - 1);
        tail._next = child;
        child._pre = tail;
        child._next = null;
        child._parent = this;
        _childern.add(child);
    }
}
