package com.example.elpaseov4.pojoModel;

import java.io.Serializable;

public class NodeDatePojo implements Serializable {
    private String id;
    private NodePojo node;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NodePojo getNode() {
        return node;
    }

    public void setNode(NodePojo node) {
        this.node = node;
    }
}
