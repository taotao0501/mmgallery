package com.imooc.mgallery.utils;

import com.imooc.mgallery.entity.Painting;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据源类，用于将XML文件解析为Java对象
 */
public class XmlDataSource {
    // 通过static keyword保证数据全局唯一
    private static List<Painting> data = new ArrayList<>();
    private static String dataFile;

    static {
        //编译后,本类.class与 painting.xml都在头一个目录下，因此就得到painting.xml文件完整物理路径
        //这里要注意，要写成painting.xml
        dataFile = XmlDataSource.class.getResource("painting.xml").getPath();
        //dataFile = XmlDataSource.class.getResource("../painting.xml").getPath();
        reload();
    }

    // 将读取Painting.xml的文件成列表的代码写成一个方法
    private static void reload() {
        //考虑到路径可能有空格，导致 dataFile有特殊字符
        //URLDecoder decoder = new URLDecoder(); 静态方法可以直接调用
        try {
            dataFile = URLDecoder.decode(dataFile, "UTF-8");
            //System.out.println(dataFile);
            //利用Dom4j对XML进行解析
            SAXReader reader = new SAXReader();
            Document document = reader.read(dataFile);
            data.clear();
            // Xpath获取painting节点
            List<Node> nodes = document.selectNodes("/root/painting");
            for (Node node : nodes) {
                Element ele = (Element) node;
                String id = ele.attributeValue("id");
                String pname = ele.elementText("pname");

                Painting pt = new Painting();
                pt.setId(Integer.parseInt(id));
                pt.setPname(pname);
                pt.setCategory(Integer.parseInt(ele.elementText("category")));
                pt.setPrice(Integer.parseInt(ele.elementText("price")));
                pt.setPreview(ele.elementText("preview"));
                pt.setDescription(ele.elementText("description"));
                data.add(pt);

            }
        } catch (UnsupportedEncodingException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有油画Painting集合
     *
     * @return
     */
    public static List<Painting> getRawData() {
        return data;
    }

    /**
     * 增加新的油画数据
     *
     * @param painting
     */
    public static void append(Painting painting) {
        SAXReader reader = new SAXReader();
        Writer writer = null;
        try {
            Document document = reader.read(dataFile);
            Element root = document.getRootElement();
            Element pt = root.addElement("painting");
            pt.addAttribute("id", String.valueOf(data.size() + 1));
            pt.addElement("pname").setText(painting.getPname());
            pt.addElement("category").setText(painting.getCategory().toString());
            pt.addElement("price").setText(painting.getPrice().toString());
            pt.addElement("preview").setText(painting.getPreview());
            pt.addElement("description").setText(painting.getDescription());
            writer = new OutputStreamWriter(new FileOutputStream(dataFile), "UTF-8");
            document.write(writer);
            System.out.println(dataFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            reload();//保证内存与文件一致
        }

    }

    public static void update(Painting painting) {
        SAXReader reader = new SAXReader();
        Writer writer = null;
        try {
            Document document = reader.read(dataFile);
            //节点路径[@属性名=属性值]
            // /root/painting[@id=x]
            List<Node> nodes = document.selectNodes("/root/painting[@id=" + painting.getId() + "]");
            if(nodes.size() == 0) {
                throw new RuntimeException("id=" + painting.getId() + "编号不存在");
            }
            Element p = (Element) nodes.get(0);
            p.selectSingleNode("pname").setText(painting.getPname());
            p.selectSingleNode("category").setText(painting.getCategory().toString());
            p.selectSingleNode("price").setText(painting.getPrice().toString());
            p.selectSingleNode("preview").setText(painting.getPreview());
            p.selectSingleNode("description").setText(painting.getDescription());
            writer = new OutputStreamWriter(new FileOutputStream(dataFile),"UTF-8");
            document.write(writer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            reload();
        }
    }

    public static void delete(Integer id){
        SAXReader reader = new SAXReader();
        Writer writer = null;
        try {
            Document document = reader.read(dataFile);
            List<Node> nodes = document.selectNodes("/root/painting[@id=" + id + "]");
            if(nodes.size() == 0) {
                throw new RuntimeException("id=" + id + "编号油画不存在");
            }
            Element p = (Element)nodes.get(0);
//            删除操作
            p.getParent().remove(p);
            writer = new OutputStreamWriter(new FileOutputStream(dataFile),"UTF-8");
            document.write(writer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            reload();
        }
    }


    public static void main(String[] args) {
//        List<Painting> ps = XmlDataSource.getRawData();
//        for(Painting p:ps){
//            System.out.println(p.getPreview());
//        }
        Painting p = new Painting();
        p.setPname("测试油画");
        p.setCategory(2);
        p.setPrice(3000);
        p.setPreview("upload/HP1.jpg");
        p.setDescription("哈利波特照片");
        XmlDataSource.append(p);
    }
}
