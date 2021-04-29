package com.slsale.controller;

import com.slsale.commons.Constant;
import com.slsale.commons.PageSupport;
import com.slsale.pojo.DataDictionary;
import com.slsale.pojo.Role;
import com.slsale.pojo.User;
import com.slsale.service.DataDictionaryService;
import com.slsale.service.RoleService;
import com.slsale.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther:
 * @Date:2021/4/18
 * @Description:com.slsale.controller
 * @Version:1.0
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DataDictionaryService dataDictionaryService;


    /**
     * 功能描述：根据条件进行查询的时候 同时需要回显查询的条件 分页的时候同样需要根据条件进行分页
     */
    @RequestMapping(value = "/backend/userlist.html")
    public String userList(HttpSession session, Model model,
                           @RequestParam(value = "s_loginCode",required = false)String loginCode,
                           @RequestParam(value = "s_referCode",required =false)String referCode,
                           @RequestParam(value = "s_roleId",required =false)String roleId,
                           @RequestParam(value = "s_isStart",required = false)String isStart,
                           @RequestParam(value = "currentpage",required=false)Integer currentpage){

        Map<String,Object> baseModel =(Map<String,Object>)session.getAttribute(Constant.SESSION_BASE_MODE);
        if (baseModel == null){

            return "redirect:/";

        }else{
            List<Role> roleList = null;
            List<DataDictionary> cardTypeList = null;
            try {
                //获取角色列表
               roleList = roleService.getRoleNameAndId();

               DataDictionary dataDictionary = new DataDictionary();
               dataDictionary.setTypeCode("CARD_TYPE");
               cardTypeList = dataDictionaryService.getDataDictionaryList(dataDictionary);
            } catch (Exception e) {
                e.printStackTrace();
            }

            User user = new User();
            if(!StringUtils.isEmpty(loginCode)){
                user.setLoginCode(loginCode);
            }
            if(!StringUtils.isEmpty(referCode)){
                user.setReferCode(referCode);
            }
            if(StringUtils.isEmpty(roleId)){
                user.setRoleId(null);
            }else{
                user.setRoleId(Integer.valueOf(roleId));
            }

            if(StringUtils.isEmpty(isStart)){
                user.setIsStart(null);
            }else{
                user.setIsStart(Integer.valueOf(isStart));
            }
            PageSupport page = new PageSupport();
            List<User> userList = null;
            try {
                int totalCount = userService.getTotalCount(user);
                if(totalCount > 0){
                    page.setTotalCount(totalCount);
                    if(currentpage !=null ){
                        page.setPageNo(currentpage);
                    }
                    if(page.getPageNo()<=0){
                        page.setPageNo(1);
                    }
                    if(page.getPageNo()>page.getPageCount()){
                        page.setPageNo(page.getPageCount());
                    }
                    page.setPageNo(page.getPageNo());
                    page.setPageSize(page.getPageSize());
                    user.setStarNum((page.getPageNo()-1)*page.getPageSize());
                    user.setPageSize(page.getPageSize());
                    userList = userService.getUserListByPage(user);
                    page.setItems(userList);
                }else{
                    page.setItems(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("page",page);
            model.addAllAttributes(baseModel);
            model.addAttribute("s_loginCode",loginCode); //将查询条件回显到前台页面
            model.addAttribute("s_referCode",referCode);
            model.addAttribute("s_roleId",roleId);
            model.addAttribute("s_isStart",isStart);
            model.addAttribute("roleList",roleList);
            model.addAttribute("cardTypeList",cardTypeList);
            return "backend/userlist";
        }
    }
    /**
     * 功能描述：根据ID获取用户信息
     */
    @RequestMapping(value="/backend/getuser.html",produces= {"text/html;charset=utf-8"})
    @ResponseBody
    public Object getUser(@RequestParam(value="id")String m_id){
        if(StringUtils.isEmpty(m_id)){
            return "nodata";
        }
        Integer id = Integer.valueOf(m_id);
        User user = new User();
        user.setId(id);
        User _user = null;
        try {
            _user = userService.getUserById(user);
            JSONObject jsonObject = JSONObject.fromObject(_user);
            String jsonStr = jsonObject.toString();
            return jsonStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }
    /**
     * 功能描述：当roleId=2 即为会员的时候 发送一个ajax异步请求 到后台data_dictionary表中去查询
     */
    @RequestMapping(value = "/backend/loadUserTypeList.html",produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public Object loadUserType(@RequestParam(value = "s_role",required = false)String s_role){
        try {
            if(StringUtils.isEmpty(s_role)){
                return "nodata";
            }
            DataDictionary dataDictionary = new DataDictionary();
            dataDictionary.setTypeCode("USER_TYPE");
            List<DataDictionary> userTypeList = dataDictionaryService.getDataDictionaryList(dataDictionary);
            //将集合List转换成JSON格式的数组
            JSONArray jsonArray = JSONArray.fromObject(userTypeList);
           return jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }

    /**
     * 功能描述：图片上传功能 将客户端上的二进制字节流转换成MultipartFile封装类
     * 在服务器端通过MultipartFile接口的封装类获取到文件流
     */
    @RequestMapping(value = "/backend/upload.html")
    @ResponseBody
    public Object upload(@RequestParam(value = "m_fileInputID",required = false)MultipartFile fileInputID,
                         @RequestParam(value="m_fileInputBank",required = false)MultipartFile fileInputBank,
                         HttpServletRequest request){

        //文件上传 获取服务器端存储文件的目录
        String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");

        DataDictionary dataDictionary = new DataDictionary();
        dataDictionary.setTypeCode("INFOFILE_SIZE");
        List<DataDictionary> list = null;

        int fileSize = 0;
        try {
            list = dataDictionaryService.getDataDictionaryList(dataDictionary);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(list !=null && list.size()>0){
            fileSize = Integer.valueOf(list.get(0).getValueName());
        }

        //上传身份证
        if(fileInputID != null){
            String originalFileName = fileInputID.getOriginalFilename();
            String prefix = originalFileName.substring(originalFileName.lastIndexOf("."));

            if(fileInputID.getSize() > fileSize){
                return "1"; //文件太大
            }else if(!prefix.equals(".jpg") && !prefix.equals(".png") && !prefix.equals(".jpeg") && !prefix.equals(".pneg")){
                return "2";
            }else{
                String newFileName = UUID.randomUUID().toString().replace("-","").subSequence(0,10)+prefix;
                String url = "";
                try {
                    File file = new File(path,newFileName);
                    FileUtils.copyInputStreamToFile(fileInputID.getInputStream(),file);
                    //requestContextPath 获取的项目的根路径
                    url = request.getContextPath()+"/statics/uploadfiles/"+newFileName;
                    //System.out.println("url============"+url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return url;
            }
        }
        //上传银行卡
        if(fileInputBank != null){

            String originalFileName = fileInputBank.getOriginalFilename();
            String prefix = originalFileName.substring(originalFileName.lastIndexOf("."));

            if(fileInputBank.getSize() > fileSize){
                return "1"; //图片过大
            }else if( !prefix.equalsIgnoreCase(".jpg") && !prefix.equalsIgnoreCase(".png")
                    && !prefix.equalsIgnoreCase(".jpeg")&& !prefix.equalsIgnoreCase(".pneg")){
                return "2";
            }else{
                try {
                    String newFileName = UUID.randomUUID().toString().replace("-","").subSequence(0,10)+prefix;
                    File file = new File(path,newFileName);
                    if(!file.exists()){ //exists()方法 用于返回文件是否存在 存在返回true 不存在返回false
                        file.mkdirs();
                    }
                    FileUtils.copyInputStreamToFile(fileInputBank.getInputStream(),file);
                    //获取图片的相对路径 相对项目的根路径 用于图片的回显
                    String url = request.getContextPath()+"/statics/uploadfiles/"+newFileName;
                    return url;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
    /**
     * 功能描述：
     */
    @RequestMapping(value="/backend/logincodeisexist.html")
    @ResponseBody
    public Object loginCodeIsExist(@RequestParam(value = "user",required = false) String user){
        JSONObject jsonObject = JSONObject.fromObject(user);
        User _user = (User) JSONObject.toBean(jsonObject,User.class);
        String result = "";
        try {
            //select count(1) from au_user where loginCode = #{loginCode} and id != #{id}
            int index = userService.loginCodeIsExit(_user);
            if (index >0){
                result = "repeat";
            }else{
                result = "only";
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = "failed";
        }
        return result;
    }
}
