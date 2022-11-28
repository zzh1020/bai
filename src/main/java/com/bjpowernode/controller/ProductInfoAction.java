package com.bjpowernode.controller;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductInfoSelect;
import com.bjpowernode.service.ProductInfoService;
import com.bjpowernode.utils.FileNameUtil;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    public static  final int pagesize=5;

    //异步上传图片名称
    String saveFilename="";
    @Autowired
    ProductInfoService productInfoService;
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
      List<ProductInfo> list=productInfoService.getAll();
      request.setAttribute("list",list);
      return "product";
    }
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo info=null;
        Object vo=request.getSession().getAttribute("prodVo");
        if (vo!=null){
            info=productInfoService.splitPageSelect((ProductInfoSelect) vo,pagesize);
            request.getSession().removeAttribute("prodVo");
        }else {
            info=productInfoService.splitPage(1,pagesize);
        }

        request.setAttribute("info",info);
        return "product";
    }
    //ajax分页翻页处理
    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(ProductInfoSelect vo, HttpSession session){
        //取得当前page参数页面的数据
        PageInfo info=productInfoService.splitPageSelect(vo,pagesize);
        session.setAttribute("info",info);
    }
    //异步ajax文件上传处理
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request){
        //提取生产文件名和图片上传的后缀
        saveFilename= FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到图片存储的路径
        String path=request.getServletContext().getRealPath("/image_big");
        //转存
        try {
            pimage.transferTo(new File(path+File.separator+saveFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回客户端json对象，封装图片的路径，实现页面回显
        JSONObject object=new JSONObject();
        object.put("imgurl",saveFilename);
        return object.toString();
    }
    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){
        info.setpImage(saveFilename);
        info.setpDate(new Date());
        int num=-1;
        try {
            num=productInfoService.save(info);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(num>0){
            request.setAttribute("msg","新增成功");
        }else {
            request.setAttribute("msg","新增失败");
        }
        //  清空变量中的内容
        saveFilename="";
        return "redirect:/prod/split.action";
    }
    @RequestMapping("/one")
    public String one(int pid,ProductInfoSelect vo, Model model,HttpSession session){
        ProductInfo info=productInfoService.getById(pid);
        model.addAttribute("prod",info);
        session.setAttribute("prodVo",vo);
        return "update";
    }
    @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        if (!saveFilename.equals("")){
            info.setpImage(saveFilename);
        }
        int num=-1;
        try {
            num=productInfoService.update(info);
            if(num>0){
                request.setAttribute("msg","更新成功");
            }else {
                request.setAttribute("msg","更新失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //清空saveFilename
        saveFilename="";
        return "redirect:/prod/split.action";
    }
    @RequestMapping("/delete")
    public String delete(int pid,ProductInfoSelect vo,HttpServletRequest request){
        int num=-1;
        try {
            num=productInfoService.delete(pid);
            if(num>0){
                request.setAttribute("msg","删除成功");
                request.getSession().setAttribute("deleteVo",vo);
            }else {
                request.setAttribute("msg","删除失败");
            }
        } catch (Exception e) {
            request.setAttribute("msg","当前商品不可删除");
        }

        return "forward:/prod/deleteAjaxSplit.action";
    }
    //删除使用的分页处理
    @ResponseBody
    //弹框显示中文格式
    @RequestMapping( value = "/deleteAjaxSplit",produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        PageInfo info=null;
        Object vo=request.getSession().getAttribute("deleteVo");
        if(vo!=null){
            info=productInfoService.splitPageSelect((ProductInfoSelect)vo,pagesize);
            request.getSession().removeAttribute("deleteVo");
        }else {
            info=productInfoService.splitPage(1,pagesize);
        }
        //如果用请求会加载不出来
        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");
    }
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,ProductInfoSelect vo,HttpServletRequest request){
        String []ps=pids.split(",");
        try {
            int num=productInfoService.deleteBatch(ps);
            if(num>0){
                request.setAttribute("msg","批量删除成功");
                request.getSession().setAttribute("deleteVo",vo);
            }else {
                request.setAttribute("msg","批量删除失败");
            }
        } catch (Exception e) {
            request.setAttribute("msg","当前商品不可删除");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoSelect vo,HttpSession session){
        List<ProductInfo> list=productInfoService.selectCondition(vo);
        session.setAttribute("list",list);
    }
}
