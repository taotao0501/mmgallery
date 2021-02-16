package com.imooc.mgallery.dao;

import com.imooc.mgallery.entity.Painting;
import com.imooc.mgallery.utils.PageModel;
import com.imooc.mgallery.utils.XmlDataSource;

import java.util.ArrayList;
import java.util.List;

public class PaintingDao {
    /**
     *
     * @param page
     * @param rows
     * @return
     */
    public PageModel pagination(int page, int rows) {
        //这里直接调用
        List<Painting> list = XmlDataSource.getRawData();
        //这里是实例化
        PageModel pageModel = new PageModel(list,page,rows);
        return pageModel;
    }

    // 分类预览的代码
    public PageModel pagination(int category, int page, int rows) {
        List<Painting> list = XmlDataSource.getRawData();
        List<Painting> categoryList = new ArrayList<>();
        for(Painting p:list) {
            if(p.getCategory() == category) {
                categoryList.add(p);
            }
        }
        PageModel pageModel = new PageModel(categoryList,page,rows);
        return pageModel;
    }

    /**
     * 数据新增
     */
    public void create(Painting painting) {
        //这里有个问题：为什么这里不用实例化对象然后再调用其方法呢，
        // 因为外部调用静态方法可以直接使用 类.方法 也可以 对象.方法 进行调用
        XmlDataSource.append(painting);
    }

    /**
     * 修改更新：搜索id
     */
    public Painting findById(Integer id) {
        List<Painting> data = XmlDataSource.getRawData();
        Painting painting = null;
        for(Painting p:data) {
            if(p.getId() == id) {
                painting = p;
                break;
            }
        }
        return painting;
    }

    public void update(Painting painting) {
        XmlDataSource.update(painting);
    }

    public void delete(Integer id) {
        XmlDataSource.delete(id);
    }
}
