package com.flavio.android.megasena.interfaces;

import android.content.Context;

public interface subscriber<T> {

    void alert(T obj);
    Context context();
}
