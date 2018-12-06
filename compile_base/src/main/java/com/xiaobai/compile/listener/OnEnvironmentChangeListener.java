package com.xiaobai.compile.listener;

import com.xiaobai.compile.bean.EnvironmentBean;
import com.xiaobai.compile.bean.ModuleBean;


public interface OnEnvironmentChangeListener {

    void onEnvironmentChanged(ModuleBean module, EnvironmentBean oldEnvironment, EnvironmentBean newEnvironment);
}