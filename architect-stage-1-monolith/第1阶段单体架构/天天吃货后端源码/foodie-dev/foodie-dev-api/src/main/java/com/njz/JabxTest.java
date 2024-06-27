package com.njz;

import com.njz.bean.GroupMember;
import com.njz.bean.Njz;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static javax.xml.bind.Marshaller.JAXB_ENCODING;

public class JabxTest {
    public String java2Xml(Njz njz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Njz.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(JAXB_ENCODING, "GBK");

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(njz, stringWriter);
        String xml = stringWriter.toString().replaceFirst(" standalone=\"[^\"]*\"", "");
        return xml;
    }
    public Njz xml2Java(String xml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Njz.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Njz njz = (Njz) unmarshaller.unmarshal(new StringReader(xml));
        return njz;
    }

    public static void main(String[] args) throws JAXBException {
        Njz njz = new Njz();
        njz.setAge(28);
        njz.setChineseName("niejianzhou");
        njz.setEnglishName("aaron");
        njz.setHeight(172f);
        njz.setWeight(63.5f);
        List<GroupMember> members = new ArrayList<>();
        members.add(new GroupMember("1001", "组1", "李红", 3));
        members.add(new GroupMember("1002", "组2", "张三", 4));
        members.add(new GroupMember("1003", "组3", "王五", 5));
        njz.setGroupMembers(members);
        JabxTest jabxTest = new JabxTest();
        String xml = jabxTest.java2Xml(njz);
        System.out.println(xml);
        //
        String xmlString = "<?xml version=\"1.0\" encoding=\"GBK\"?>" +
                "<njzRoot>" +
                "    <age>28</age>" +
                "    <chineseName>niejianzhou</chineseName>" +
                "    <englishName>aaron</englishName>" +
                "    <groups>" +
                "        <groupId>1001</groupId>" +
                "        <groupName>组1</groupName>" +
                "        <leaderName>李红</leaderName>" +
                "        <size>3</size>" +
                "    </groups>" +
                "    <groups>" +
                "        <groupId>1002</groupId>" +
                "        <groupName>组2</groupName>" +
                "        <leaderName>张三</leaderName>" +
                "        <size>4</size>" +
                "    </groups>" +
                "    <groups>" +
                "        <groupId>1003</groupId>" +
                "        <groupName>组3</groupName>" +
                "        <leaderName>王五</leaderName>" +
                "        <size>5</size>" +
                "    </groups>" +
                "    <height>172.0</height>" +
                "    <weight>63.5</weight>" +
                "</njzRoot>";
        Njz njzTmp = jabxTest.xml2Java(xmlString);
        System.out.println(njzTmp.toString());
    }
}
