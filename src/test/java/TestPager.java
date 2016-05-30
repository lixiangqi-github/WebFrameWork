import com.google.gson.Gson;
import com.sgaop.web.frame.server.dao.Pager;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/7/1 0001
 * To change this template use File | Settings | File Templates.
 */
public class TestPager {


    public static void main(String[] args) {
        System.out.println("1:"+new Gson().toJson(new Pager(1,5)));
        System.out.println("2:"+new Gson().toJson(new Pager(2,5)));
        System.out.println("3:"+new Gson().toJson(new Pager(3,5)));
        System.out.println("4:"+new Gson().toJson(new Pager(4,5)));
        System.out.println("5:"+new Gson().toJson(new Pager(5,5)));
    }
}
