package com.ns.utils;

import com.netoperation.model.MeBean;

import java.util.ArrayList;
import java.util.List;

public class ContentUtil {

    public static StringBuilder getAuthor(List<String> authors) {
        StringBuilder author = new StringBuilder();
        if(authors != null && authors.size()>0) {
            int authorSize = authors.size();
            int count = 1;
            for (String str : authors) {
                if(authorSize > 1) {
                    author.append(str+ ",");
                } else if(authorSize == count) {
                    author.append(str);
                }
                count++;
            }

        }
        return author;
    }

    public static String getThumbUrl(List<String> urls) {
        if(urls != null && urls.size()>0) {
            return urls.get(0);
        }
        return "http://";
    }

    public static String getBannerUrl(ArrayList<MeBean> meBeans, ArrayList<MeBean> briefingMeBeans, List<String> thumbUrls) {
        if(meBeans != null && meBeans.size()>0) {
            return meBeans.get(0).getIm_v2();
        }
        else if(briefingMeBeans != null && briefingMeBeans.size()>0) {
            return briefingMeBeans.get(0).getImage();
        }
        else if(thumbUrls != null && thumbUrls.size()>0) {
            return thumbUrls.get(0);
        }
        return "";
    }
}