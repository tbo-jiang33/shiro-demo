package Util;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @auther: jtb
 * @date: 2019/6/12 22:11
 * @description:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "root")
class Root{
    @XmlElement(name = "girl")
    private List<Girl> girls;

    public String toString(){
        StringBuilder sb=new StringBuilder();
        for(Girl girl:girls){
            sb.append(girl.toString());
        }
        return sb.toString();
    }

    public List<Girl> getGirls() {return girls;}
    public void setGirls(List<Girl> girls) {this.girls = girls;}
}