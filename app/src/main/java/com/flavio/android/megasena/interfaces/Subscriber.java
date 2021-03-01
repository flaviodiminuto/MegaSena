package com.flavio.android.megasena.interfaces;

import android.content.Context;

public interface Subscriber<T> {

    void alert(T obj);
    Context context();
}
