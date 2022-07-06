package com.example.demo16.myssm.myspringmvc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet{
    private Map<String, Object> BeanMap = new HashMap<>();
    public DispatcherServlet() throws ParserConfigurationException, IOException, SAXException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("application.xml");
        // 创建documentBuilderFactory对象
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        // 创建documentBuilder对象
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        // 获取document对象
        Document document = documentBuilder.parse(resourceAsStream);
        // 获取所有的bean节点
        NodeList beanNodeList = document.getElementsByTagName("bean");
        for(int i = 0;i < beanNodeList.getLength();i ++) {
            Node beannode = beanNodeList.item(i);
            if (beannode.getNodeType() == Node.ELEMENT_NODE) {
                Element beanElement = (Element) beannode;
                String BeanId = beanElement.getAttribute("id");
                String ClassName = beanElement.getAttribute("class");
                Object o = Class.forName(ClassName).newInstance();
                BeanMap.put(BeanId,o);
            }
        }
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        // 假设url是 ：http://localhost:8080/hello.do
        // 那么ServletPath是：/hello.do
        // 我的思路是第一步/hello.do --> hello
        String servletPath = req.getServletPath();
        String substring = servletPath.substring(1);
        int i = substring.lastIndexOf(".do");
        String substring1 = substring.substring(0, i);

        //System.out.println(substring1);

    }
}
