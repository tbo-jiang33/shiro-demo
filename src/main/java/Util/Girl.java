package Util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @auther: jtb
 * @date: 2019/6/12 22:10
 * @description:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "girl")
class Girl {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "age")
    private String age;

    @XmlElement(name = "cup")
    private String cup;

    public String toString(){
        return name+","+age+","+cup+"\n";
    }

    public String getAge() {return age;}
    public void setAge(String age) {this.age = age;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getCup() {return cup;}
    public void setCup(String cup) {this.cup = cup;}
}