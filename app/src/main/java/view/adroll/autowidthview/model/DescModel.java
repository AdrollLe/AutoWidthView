package view.adroll.autowidthview.model;

/**
 * <p> File description: <p>
 * <p> Creator: Adroll   <p>
 * <p> Created date: 2020/7/9 <p>
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class DescModel {
    private String name;
    private String desc;
    private boolean isVip;

    public boolean getIsVip() {
        return isVip;
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
