package com.bjpowernode.listener;

import com.bjpowernode.pojo.ProductType;
import com.bjpowernode.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;
@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //手工从spring容器取出对象
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService= (ProductTypeService) context.getBean("ProductTypeServiceImpl");
        List<ProductType> typeList=productTypeService.getAll();
        //放入全局作用域中
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
