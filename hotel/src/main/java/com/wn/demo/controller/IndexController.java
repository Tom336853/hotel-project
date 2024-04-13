package com.wn.demo.controller;

import com.wn.demo.mapper.*;
import com.wn.demo.pojo.*;
import com.wn.demo.service.ProductService;
import com.wn.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class IndexController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private RoomOrderMapper roomOrderMapper;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private RestaurantOrderMapper restaurantOrderMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = {"/","/main"})
    public String login(HttpServletRequest request,Model model) {
        Admin admin=(Admin) request.getSession().getAttribute("adminSession");
        User ad=(User) request.getSession().getAttribute("userSession");
        if (admin==null){
            return "login";
        }
        if (ad!=null){
            List<Product> productList = productService.selectAll(0,10,null);
            model.addAttribute("albumList", productList);
            return "front/index";
        }
        if (admin.getDataType().equals("物资管理员")) {
            return "index2";
        }
        if (admin.getDataType().equals("财务管理员")) {
            return "index3";
        }
        if (admin.getDataType().equals("后台管理员")) {
            return "index4";
        }
        return "index";
    }

    @GetMapping(value = {"index"})
    public String index() {
        return "index";
    }

    @GetMapping("welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping(value = {"front/index"})
    public String front(Room room,HttpServletRequest request,Model model) {
        User ad=(User) request.getSession().getAttribute("userSession");
        if (ad!=null){
            model.addAttribute("user",ad);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = sdf.format(new Date());
        String endTime = sdf.format(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000L));
        room.setStartTime(startTime + " 12:00:00");
        room.setEndTime(endTime + " 12:00:00");
        List<Room> roomList = roomMapper.selectAll2(room);
        model.addAttribute("roomList", roomList);
        room.setStartTime(startTime);
        room.setEndTime(endTime);
        model.addAttribute("room", room);
        return "front/index";
    }

    @GetMapping(value = {"front/search"})
    public String search(Room room,HttpServletRequest request,Model model) {
        User ad=(User) request.getSession().getAttribute("userSession");
        if (ad!=null){
            model.addAttribute("user",ad);
        }
        String startTime = room.getStartTime();
        String endTime = room.getEndTime();
        room.setStartTime(startTime + " 12:00:00");
        room.setEndTime(endTime + " 12:00:00");
        List<Room> roomList = roomMapper.selectAll2(room);
        model.addAttribute("roomList", roomList);
        room.setStartTime(startTime);
        room.setEndTime(endTime);
        model.addAttribute("room", room);
        return "front/index";
    }

    @GetMapping(value = {"front/search2"})
    public String search2(Restaurant restaurant,HttpServletRequest request,Model model) {
        User ad=(User) request.getSession().getAttribute("userSession");
        if (ad!=null){
            model.addAttribute("user",ad);
        }
        List<Restaurant> restaurantList = restaurantMapper.selectAll2(restaurant);
        model.addAttribute("restaurantList", restaurantList);
        model.addAttribute("restaurant", restaurant);
        return "front/restaurant";
    }

    @GetMapping(value = {"front/restaurant"})
    public String album(Restaurant restaurant,HttpServletRequest request,Model model) {
        User ad=(User) request.getSession().getAttribute("userSession");
        if (ad!=null){
            model.addAttribute("user",ad);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = sdf.format(new Date());
        restaurant.setStartTime(startTime);
        List<Restaurant> restaurantList = restaurantMapper.selectAll2(restaurant);
        model.addAttribute("restaurantList", restaurantList);
        model.addAttribute("restaurant", restaurant);
        return "front/restaurant";
    }

    @GetMapping(value = {"info"})
    public String info(HttpServletRequest request,Model model) {
        User ad=(User) request.getSession().getAttribute("userSession");
        if (ad!=null){

            List<Room> roomList = roomMapper.selectAll(null);
            List<Restaurant> restaurantList = restaurantMapper.selectAll(null);

            RoomOrder roomOrder = new RoomOrder();
            roomOrder.setUserId(ad.getUserid());
            List<RoomOrder> roomOrderList = roomOrderMapper.selectAll(roomOrder);
            for (RoomOrder order: roomOrderList) {
                for (Room room: roomList) {
                    if (order.getRoomNo().equals(room.getRoomNo())) {
                        order.setRoom(room);
                    }
                }
            }

            RestaurantOrder restaurantOrder = new RestaurantOrder();
            restaurantOrder.setUserId(ad.getUserid());
            List<RestaurantOrder> restaurantOrderList = restaurantOrderMapper.selectAll(restaurantOrder);
            for (RestaurantOrder order: restaurantOrderList) {
                for (Restaurant restaurant: restaurantList) {
                    if (order.getRestNo().equals(restaurant.getRestNo())) {
                        order.setRestaurant(restaurant);
                    }
                }
            }

            model.addAttribute("user",ad);
            model.addAttribute("roomOrderList", roomOrderList);
            model.addAttribute("restaurantOrderList", restaurantOrderList);
        }else {
            return "redirect:/";
        }
        return "front/info";
    }

    @RequestMapping(value = "front/album/upFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upFile(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map2 = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        String filename = "";
        String localPath = "D:\\pet_img\\"; //存放我们上传的头像

        try {
            if (file != null) {
                //生成uuid作为文件名称
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                //获得文件类型（可以判断如果不是图片，禁止上传）
                String contentType = file.getContentType();
                //获得文件后缀名
                String suffixName = contentType.substring(contentType.indexOf("/") + 1);
                //得到 文件名(随机数+uuid+后缀)
                filename = (int) ((Math.random()) * 100000000) + uuid + "." + suffixName;

//            判断是否有这个文件夹，没有就创建
                if (!new File(localPath).exists()) {
                    new File(localPath).mkdirs();
                }
//            复制到当前文件夹
                file.transferTo(new File(localPath + filename));
            }
        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "");
            map.put("data", map2);
            map2.put("src", "/pet_img/"+filename);
            //  System.out.println("上传失败");
            return map;
        }

        map.put("code", 0);
        map.put("msg", "");
        map.put("data", map2);
        map2.put("src", "/pet_img/"+filename);
        // System.out.println("上传成功:"+filename);
        return map;
    }

    @PostMapping("/front/addRoomOrder")
    @ResponseBody
    public ResultBean addRoomOrder(RoomOrder roomOrder, HttpServletRequest request, Model model){
        User ad=(User) request.getSession().getAttribute("userSession");
        if (ad!=null){
            model.addAttribute("user",ad);
        }
        Room room = roomMapper.selectByRoomNo(roomOrder.getRoomNo());
        roomOrder.setOrderNo(System.currentTimeMillis()+"");
        roomOrder.setUserId(ad.getUserid());
        roomOrder.setPrice(room.getRoomPrice());
        roomOrder.setStartTime(roomOrder.getStartTime()+" 12:00:00");
        roomOrder.setEndTime(roomOrder.getEndTime()+" 12:00:00");
        return ResultBean.success(roomOrderMapper.add(roomOrder));
    }

    @PostMapping("/front/addRestaurantOrder")
    @ResponseBody
    public ResultBean addRestaurantOrder(RestaurantOrder restaurantOrder, HttpServletRequest request, Model model){
        User ad=(User) request.getSession().getAttribute("userSession");
        if (ad!=null){
            model.addAttribute("user",ad);
        }
        Restaurant restaurant = restaurantMapper.selectByRestNo(restaurantOrder.getRestNo());
        restaurantOrder.setOrderNo(System.currentTimeMillis()+"");
        restaurantOrder.setUserId(ad.getUserid());
        restaurantOrder.setPrice(restaurant.getRestPrice());
        return ResultBean.success(restaurantOrderMapper.add(restaurantOrder));
    }

    @GetMapping("/front/deleteRoomOrder")
    public String deleteRoomOrder(Integer id,HttpServletRequest request){
        RoomOrder roomOrder = new RoomOrder();
        roomOrder.setId(id);
        roomOrder.setStatus("已取消");
        roomOrderMapper.update(roomOrder);
        return "redirect:/info";
    }

    @GetMapping("/front/deleteRestaurantOrder")
    public String deleteRestaurantOrder(Integer id,HttpServletRequest request){
        RestaurantOrder restaurantOrder = new RestaurantOrder();
        restaurantOrder.setId(id);
        restaurantOrder.setStatus("已取消");
        restaurantOrderMapper.update(restaurantOrder);
        return "redirect:/info";
    }

}
