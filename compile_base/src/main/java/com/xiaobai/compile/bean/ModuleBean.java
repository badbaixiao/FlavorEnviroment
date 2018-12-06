package com.xiaobai.compile.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class ModuleBean implements Serializable {
    private String name;

    private String alias;

    private List<EnvironmentBean> environments;

    public ModuleBean() {
        this("", "");
    }

    public ModuleBean(String name, String alias) {
        this(name, alias, new ArrayList<EnvironmentBean>());
    }

    public ModuleBean(String name, String alias, List<EnvironmentBean> environments) {
        this.name = name;
        this.alias = alias;
        this.environments = environments;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias == null ? "" : alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<EnvironmentBean> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<EnvironmentBean> environments) {
        this.environments = environments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModuleBean that = (ModuleBean) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;
        return environments != null ? environments.equals(that.environments) : that.environments == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (environments != null ? environments.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ModuleBean{" +
                "name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", environments=" + environments +
                '}';
    }
}