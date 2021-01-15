package com.flavio.android.megasena.interfaces;

import android.content.Context;

public interface SorteioSubcriber<T> {

    void alert(T obj);
    Context context();
}
