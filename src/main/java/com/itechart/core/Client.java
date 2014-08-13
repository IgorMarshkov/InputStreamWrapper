package com.itechart.core;

import com.itechart.core.concurrent.RunnableTask;

public interface Client {
    boolean add(RunnableTask thread);
    boolean remove(RunnableTask thread);
}
