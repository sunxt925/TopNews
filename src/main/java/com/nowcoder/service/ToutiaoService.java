package com.nowcoder.service;

import com.nowcoder.dao.NewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nowcoder on 2016/6/26.
 */
@Service
public class ToutiaoService {

    @Autowired
    NewsDAO newsDAO;

    public String say() {
        return "This is from ToutiaoService";
    }

    public String countNews() {
        return newsDAO.countNews();
    }
}
