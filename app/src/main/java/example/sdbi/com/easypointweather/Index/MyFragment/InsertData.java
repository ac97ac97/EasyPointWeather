package example.sdbi.com.easypointweather.Index.MyFragment;

import example.sdbi.com.easypointweather.R;
import example.sdbi.com.easypointweather.db.News;

/**
 * Created by Administrator on 2018/3/12.
 */

public  class InsertData {
    public static void insertData(){
        News news = new News();
        news.setImg(R.drawable.changcheng);
        news.setNewsContent("暑假兴趣班报不报? 各方网友");
        news.setFromAdress("长城网");
        news.setDate("2018.3.1");
        news.save();
        News news1=new News();
        news1.setImg(R.drawable.shenzhen);
        news1.setNewsContent("潜海联合控股公司清理三宗违法");
        news1.setFromAdress("深圳新闻网");
        news1.setDate("2018.3.2");
        news1.save();
        News news2=new News();
        news2.setImg(R.drawable.yidian);
        news2.setNewsContent("绍兴媳妇怀孕6个月很生气 抱");
        news2.setFromAdress("一点资讯");
        news2.setDate("2018.3.3");
        news2.save();
        News news3 = new News();
        news3.setImg(R.drawable.yunnan);
        news3.setNewsContent("网报丽江九子海林区遭伐，有");
        news3.setFromAdress("云南网");
        news3.setDate("2018.3.4");
        news3.save();
        News news4=new News();
        news4.setImg(R.drawable.yuncheng);
        news4.setNewsContent("奉劝大家没事别往寺院跑");
        news4.setFromAdress("运城灵通哥");
        news4.setDate("2018.3.5");
        news4.save();
        News news5= new News();
        news5.setImg(R.drawable.renmin);
        news5.setNewsContent("资深夜班编辑在四川“重温长征”");
        news5.setFromAdress("人民网");
        news5.setDate("2018.3.6");
        news5.save();
    }
}