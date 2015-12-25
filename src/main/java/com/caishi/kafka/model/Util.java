package com.caishi.kafka.model;

import com.alibaba.fastjson.JSON;

/**
 * Created by root on 15-10-27.
 */
public class Util {
    public static String jsonToText(String data,int flag){
        String formatLog = "";
        if(flag == 1){
            CommentEvent o = JSON.parseObject(data,CommentEvent.class);
            formatLog = o.toString();
        }else if (flag == 2){
            //topic_news_behavior
            NewsBehavior o = JSON.parseObject(data,NewsBehavior.class);
            formatLog = o.toString();
        }else if (flag == 3){
            //topic_news_social
            NewsSocial o = JSON.parseObject(data,NewsSocial.class);
            formatLog = o.toString();
        }else if (flag == 4){
            //topic_common_event
            CommonEvent o = JSON.parseObject(data,CommonEvent.class);
            formatLog = o.toString();
        }else if (flag == 5){
            //topic_scene_behavior
            SceneBehavior o = JSON.parseObject(data,SceneBehavior.class);
            formatLog = o.toString();
        }
       return formatLog;
    }

    public static String convertToJson(String data,int flag){
        String formatLog = "";
        Object o=null;
        if(flag == 1){
            o = JSON.parseObject(data, CommentEvent.class);
        }else if (flag == 2){
            //topic_news_behavior
            o = JSON.parseObject(data, NewsBehavior.class);
        }else if (flag == 3){
            //topic_news_social
            o = JSON.parseObject(data,NewsSocial.class);
        }else if (flag == 4){
            //topic_common_event
            o = JSON.parseObject(data, CommonEvent.class);
        }else if (flag == 5){
            //topic_scene_behavior
            o = JSON.parseObject(data, SceneBehavior.class);
        }else if (flag == 6){
            //topic_recommendation_event
            o = JSON.parseObject(data, PushNews.class);
        }else if (flag == 7){
            //topic_personalization_event
            o = JSON.parseObject(data, ChooseLike.class);
        }
        if(o != null)
            formatLog = JSON.toJSONString(o);
        return formatLog;
    }
}
