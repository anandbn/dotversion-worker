package com.dotversion.forcediff.core;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {
    public static List<String> extractKeyListFromNodeList(List<Node> nodes) {
        List<String> keyList = new ArrayList<String>();
        for (Node node: nodes) {
            keyList.add(node.getKey());
        }
        return keyList;
    }
}
