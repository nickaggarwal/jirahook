package com.nilesh.jirahook.queue;

import java.util.List;

public interface Queue {
    public int pushMessage(String message);
    public List<String> getMessage();
}
