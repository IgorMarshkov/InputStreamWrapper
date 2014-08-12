package com.ism.core;

import com.ism.core.concurrent.SingleThread;

public interface Client {
    boolean add(SingleThread thread);
    boolean remove(SingleThread thread);
}
