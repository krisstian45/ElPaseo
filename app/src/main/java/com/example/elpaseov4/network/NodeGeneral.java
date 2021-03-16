package com.example.elpaseov4.network;

import com.example.elpaseov4.pojoModel.NodeDatePojo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.ArrayList;

public class NodeGeneral implements Serializable {
    @JsonSerialize
    private String id;
    @JsonSerialize
    private ArrayList<NodeDatePojo> activeNodes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<NodeDatePojo> getActiveNodes() {
        return activeNodes;
    }

    public void setActiveNodes(ArrayList<NodeDatePojo> activeNodes) {
        this.activeNodes = activeNodes;
    }
}

