package com.xiaobai.compile.bean;

import java.io.Serializable;


public class EnvironmentBean implements Serializable {
    private String name;
    private String alias;
    private String flavor;
    private String defaultFlavor;
    private String url;
    private ModuleBean module;
    private boolean checked;

    public EnvironmentBean() {
    }

    public EnvironmentBean(String name, String url, String alias, ModuleBean module) {
        this(name, url, alias, module, false);
    }

    public EnvironmentBean(String name, String url, String alias, ModuleBean module, boolean checked) {
        this.name = name;
        this.url = url;
        this.alias = alias;
        this.module = module;
        this.checked = checked;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlias() {
        return alias == null ? "" : alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public ModuleBean getModule() {
        return module;
    }

    public void setModule(ModuleBean module) {
        this.module = module;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getDefaultFlavor() {
        return defaultFlavor;
    }

    public void setDefaultFlavor(String defaultFlavor) {
        this.defaultFlavor = defaultFlavor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnvironmentBean that = (EnvironmentBean) o;

        if (checked != that.checked) return false;
        if (flavor != that.flavor) return false;
        if (defaultFlavor != that.defaultFlavor) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return module != null ? module.equals(that.module) : that.module == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (flavor != null ? flavor.hashCode() : 0);
        result = 31 * result + (defaultFlavor != null ? defaultFlavor.hashCode() : 0);
        result = 31 * result + (module != null ? module.hashCode() : 0);
        result = 31 * result + (checked ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EnvironmentBean{" +
                "name='" + name + '\'' +
                ", flavor='" + flavor + '\'' +
                ", defaultFlavor='" + defaultFlavor + '\'' +
                ", alias='" + alias + '\'' +
                ", url='" + url + '\'' +
                ", moduleName=" + module.getName() +
                ", moduleAlias=" + module.getAlias() +
                ", checked=" + checked +
                '}';
    }
}

