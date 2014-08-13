package com.itechart.core;

import com.itechart.core.concurrent.SingleThread;

public interface Client {
    boolean add(SingleThread thread);
    boolean remove(SingleThread thread);
}
