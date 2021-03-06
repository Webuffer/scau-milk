package controllers;

import java.util.*;

import play.mvc.*;
import play.data.validation.*;

import models.*;

public class Logined extends Controller {
    
    @Before
    static void addUser() {
        User user = connected();
        if(user != null) {
            renderArgs.put("user", user);
        }
    }
    
    static User connected() {
        if(renderArgs.get("user") != null) {
            return renderArgs.get("user", User.class);
        }
        String username = session.get("user");
        if(username != null) {
            return User.find("byUsername", username).first();
        } 
        return null;
    }
    
   

 //public static void words_board2(){
   //     render();
    //}

    // ~~

    public static void index() {
        if(connected() == null) {
            //跳转到登录画面
            Application.index();
        }

List<Post> postList = Post.find("order by id desc").from(0).fetch(5);
    	
       render(postList);
    }

    
    public static void logout() {
        session.clear();
        Application.index();
    }
    
    public static void email(String message) {
    	User user = connected();
    	if(user == null) {
            //跳转到登录画面
            Application.login();
        }
    	render(message);
    }

	//public static void contact_us() {
	//render();
	//}

    public static void sendEmail(@Valid @Email String fromEmail,String title, String contentTexts){
        SendEmail sendEmailInfo = new SendEmail(fromEmail, title, contentTexts);
        sendEmailInfo.send();
        email("发送成功！");
    }
    
    public static void order(String message){
    	render(message);
    }
    
    public static void saveOrder1(@Valid Ordered ordered){
    	User user = connected();
    	if(user == null) {
            //跳转到登录画面
            Application.login();
        }
		
    	if(ordered.boxCount == 0 && ordered.cupCount == 0)
    		order_milk_1("请输入正确的订购数量！");
    	if(ordered.username == null)
    		order_milk_1("您的名字！");
    	if(ordered.phone == 0)
    		order_milk_1("您的联系电话！");
    	if(ordered.address == null)
    		order_milk_1("配送地址！");
    	ordered.milk = "纯白华农酸奶";
        ordered.date = new Date();
        ordered.sended = false;
        ordered.create();

        order_milk_1("成功提交订单");
    	
    }
    
    public static void saveOrder2(@Valid Ordered ordered){
    	User user = connected();
    	if(user == null) {
            //跳转到登录画面
            Application.login();
        }
		
    	if(ordered.boxCount == 0 && ordered.cupCount == 0)
    		order_milk_2("请输入正确的订购数量！");
    	if(ordered.username == null)
    		order_milk_2("您的名字！");
    	if(ordered.phone == 0)
    		order_milk_2("您的联系电话！");
    	if(ordered.address == null)
    		order_milk_2("配送地址！");
    	ordered.milk = "华农原味酸牛奶";
        ordered.date = new Date();
        ordered.sended = false;
        ordered.create();

        order_milk_2("成功提交订单");
    	
    }
    
    public static void saveOrder3(@Valid Ordered ordered){
    	User user = connected();
    	if(user == null) {
            //跳转到登录画面
            Application.login();
        }
		
    	if(ordered.boxCount == 0 && ordered.cupCount == 0)
    		order_milk_3("请输入正确的订购数量！");
    	if(ordered.username == null)
    		order_milk_3("您的名字！");
    	if(ordered.phone == 0)
    		order_milk_3("您的联系电话！");
    	if(ordered.address == null)
    		order_milk_3("配送地址！");
    	ordered.milk = "屋顶盒装学士酸牛奶";
        ordered.date = new Date();
        ordered.sended = false;
        ordered.create();

        order_milk_3("成功提交订单");
    	
    }


    public static void order_milk_1(String mess){
        if(renderArgs.get("user") == null) {
            Application.login();
        }
        render(mess);
    }
    public static void order_milk_2(String mess){
        if(renderArgs.get("user") == null) {
            Application.login();
        }
        render(mess);
    }
    public static void order_milk_3(String mess){
        if(renderArgs.get("user") == null) {
            Application.login();
        }
        render(mess);
    }
    
    public static void order_cms(){
    	if(connected() == null) {
            //跳转到登录画面
            Application.index();
        }
		
    	//找到已经送货的订单
    	List<Ordered> orderedSendedList = Ordered.find("bySended",true).fetch();
    	//找到还没送货的订单
    	List<Ordered> orderedNoSendedList = Ordered.find("bySended",false).fetch();
       render(orderedSendedList,orderedNoSendedList);
    }

	// public static void cms() {
	//	 render();    
//}

    public static void order_refresh(long itemId){
    	if(connected() == null) {
            //跳转到登录画面
            Application.index();
        }

		
    	//找到对应数据项
    	Ordered ordered = Ordered.findById(itemId);
    	ordered.sended = true;
    	ordered.messages = "555";
    	ordered.cupCount = 123;
    	ordered.save();
    	//order_cms();
    	Map map = new HashMap();
    	int status = 200;
    	map.put("status", status);
    	renderJSON(map);
		
    }
    
    
    public static void order_dele(long itemId){
    	if(connected() == null) {
            //跳转到登录画面
            Application.index();
        }

		
    	//找到对应数据项
    	int status = 0;
    	Ordered ordered = Ordered.findById(itemId);
    	if(ordered != null){
    		ordered.delete();
        	status = 200;
    	}
    	Map map = new HashMap();    	
   		map.put("status", status);
    	renderJSON(map); 	
		
    }
    
    public static void savePost( Post post) {

		if(connected()==null)
		{
            //跳转到登录画面
            Application.login();
        }		
        
		User author=null;
        String userName = session.get("user");  
		
		 if(userName != null) {
            author=User.find("byUsername", userName).first();
        } 
	
        post.author = author;  
		post.postedAt= new Date();
                
        // Validate
        //validation.valid(post);
        //if(validation.hasErrors()) {
        //   render("@form", post);
        //}

        // Save
        post.save();     
		flash.success("Thanks for posting %s", author);
		words_board();
		
    }


	public static void savePost2( Post post) {

		if(connected()==null)
		{
            //跳转到登录画面
            Application.login();
        }		
        
		
		User author=null;
        String userName = session.get("user");  
		
		 if(userName != null) {
            author=User.find("byUsername", userName).first();
        } 
	
        post.author = author;  
		post.postedAt= new Date();
                
        // Validate
        //validation.valid(post);
        //if(validation.hasErrors()) {
        //   render("@form", post);
        //}

        // Save
        post.save();     
		flash.success("Thanks for posting %s", author);
		index();
    }

	public static void words_board(){
    
    	//从form+1开始返回后面的fetch条记录
    	List<Post> postList = Post.find("order by id desc").fetch();
    	
       render(postList);
    }

	public static void words_board2(){
    
    	//从form+1开始返回后面的fetch条记录
    	List<Post> postList = Post.find("order by id desc").from(0).fetch(10);
    	
       render(postList);
    }

	 public static void postComment(
        Long postId, String content
		 )
         
    {
		if(connected()==null)
		{
            //跳转到登录画面
            Application.login();
        }	
		//String content = content;
        Post post = Post.findById(postId);
		String author = session.get("user");
        post.addComment(author, content);
        flash.success("Thanks for posting %s", author);
        words_board();
    }

	 public static void postComment2(
        Long postId, String content
		 )
         
    {
		if(connected()==null)
		{
            //跳转到登录画面
            Application.login();
        }	
		//String content = content;
        Post post = Post.findById(postId);
		String author = session.get("user");
        post.addComment(author, content);
        flash.success("Thanks for posting %s", author);
        index();
    }

	public static void deleteComment( long commentID) {

	}



    
	/*
    public static void form(Long id) {
        if(id != null) {
            Post post = Post.findById(id);
            render(post);
        
        render();
    }
	*/
}