package club.fullstack.argv;

import java.util.Set;

public class CliParam {
    private String name;
    private boolean isOptional = false;
    private boolean isFlag = false;
    private boolean needValue = false;
    private Set<Object> options;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public void setOptional(boolean optional) {
        isOptional = optional;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public boolean isNeedValue() {
        return needValue;
    }

    public void setNeedValue(boolean needValue) {
        this.needValue = needValue;
    }

    public Set<Object> getOptions() {
        return options;
    }

    public void setOptions(Set<Object> options) {
        this.options = options;
    }

    public CliParam(String name, boolean isOptional, boolean isFlag) {
        this.name = name;
        this.isOptional = isOptional;
        this.isFlag = isFlag;
        this.needValue = false;
    }

    public CliParam(String name, boolean isOptional, boolean isFlag, boolean needValue) {
        this.name = name;
        this.isOptional = isOptional;
        this.isFlag = isFlag;
        this.needValue = needValue;
    }

    public CliParam(String name, boolean isOptional, boolean isFlag, boolean needValue, Set<Object> options) {
        this.name = name;
        this.isOptional = isOptional;
        this.isFlag = isFlag;
        this.needValue = needValue;
        this.options = options;
    }
}
