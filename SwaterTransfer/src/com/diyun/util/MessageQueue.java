package com.diyun.util;

public class MessageQueue {
	
    protected static final int QSIZE = Global.MESSAGE_QUEUE_SIZE;
    protected int head = 0;
    protected int tail = 0;
    protected int add = 0;
    String[] queue = new String[QSIZE];

    public MessageQueue(){
    	
    }
    
    public boolean push(String message) {
        boolean result = false;
        if (length() < QSIZE) {
            queue[add] = message;
            tail = add;
            add = ((add + 1)% QSIZE);
            result = true;
        }
        return result;
    }

    public String pop() {
        String result = null;

        if (length() > 0) {
            result = queue[head];
            if (head == tail) {
                add = tail;
            } else {
                head = ((head + 1)% QSIZE);
            }
        }

        return result;
    }

    public void cleanup() {
        head = 0;
        tail = 0;
        add = 0;
    }

    public int length() {
        int len = 0;

        if (head > tail) {
            len = QSIZE - head + tail + 1;
        } else if (head < tail) {
            len = tail - head + 1;
        } else {
            if (add != tail) {
                len = 1;
            }
        }
        return len;
    }

    public int size() {
        return QSIZE;
    }
}
