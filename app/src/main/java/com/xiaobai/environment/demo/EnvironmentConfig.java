package com.xiaobai.environment.demo;


import com.xiaobai.compile.annotation.Environment;
import com.xiaobai.compile.annotation.Module;

public class EnvironmentConfig {

    /**
     * 整个 App 的环境
     */
    @Module
    private class App {
        @Environment(url = "https://gank.io/api/", alias = "正式",flavor = "product",defaultFlavor = BuildConfig.FLAVOR,isRelease = true)
        private String online;
        @Environment(url = "https://gank.io/api/", alias = "测试",flavor = "stage",defaultFlavor = BuildConfig.FLAVOR)
        private String test;
    }

    /**
     * 特殊模块 Music 的环境
     */
    @Module(alias = "音乐")
    private class Music {
        @Environment(url = "https://www.codexiaomai.top/api/", alias = "正式",flavor = "product",defaultFlavor = BuildConfig.FLAVOR,isRelease = true)
        private String online;

        @Environment(url = "http://test.codexiaomai.top/api/", alias = "测试",flavor = "stage",defaultFlavor = BuildConfig.FLAVOR)
        private String test;
    }

    /**
     * 特殊模块 News 的环境
     */
    @Module(alias = "新闻")
    private class News {
        @Environment(url = "http://news/release/", alias = "正式",flavor = "product",defaultFlavor = BuildConfig.FLAVOR,isRelease = true)
        private String release;

        @Environment(url = "http://news/test/", alias = "测试",flavor = "stage",defaultFlavor = BuildConfig.FLAVOR)
        private String test;

    }

    /**
     * 分享模块
     */
    @Module(alias = "分享")
    private class Share {
        @Environment(url = "http://www.share.com", alias = "正式",flavor = "product",defaultFlavor = BuildConfig.FLAVOR,isRelease = true)
        private String online;

        @Environment(url = "http://test.share.com", alias = "测试",flavor = "stage",defaultFlavor = BuildConfig.FLAVOR)
        private String test;
    }
}
