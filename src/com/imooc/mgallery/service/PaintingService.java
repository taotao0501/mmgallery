package com.imooc.mgallery.service;

import com.imooc.mgallery.dao.PaintingDao;
import com.imooc.mgallery.entity.Painting;
import com.imooc.mgallery.utils.PageModel;
import java.util.List;

public class PaintingService {
    private PaintingDao paintingDao = new PaintingDao();

    public PageModel pagination(int page, int rows, String...category) {
        if(rows == 0) {
            throw new RuntimeException("无效的rows参数");
        }
        if(category.length == 0 || category[0] == null) {
            return paintingDao.pagination(page,rows);
        } else {
            return paintingDao.pagination(Integer.parseInt(category[0]), page,rows);
        }
    }

    /**
     * 数据新增
     */
    public void create(Painting painting) {
        paintingDao.create(painting);
    }

    public Painting findById(Integer id){
        Painting p = paintingDao.findById(id);
        if(p == null) {
            throw new RuntimeException("[id=" + id + "]油画不存在");
        }
        return p;
    }

    /**
     * 更新业务逻辑
     * @param newPainting 新的皮肤数据
     * @param isPreviewModified 是否修改Preview属性
     */
    public void update(Painting newPainting, Integer isPreviewModified) {
        //在原始数据基础上覆盖更新
        Painting oldPainting = this.findById(newPainting.getId());
        oldPainting.setPname(newPainting.getPname());
        oldPainting.setCategory(newPainting.getCategory());
        oldPainting.setPrice(newPainting.getPrice());
        oldPainting.setDescription(newPainting.getDescription());
        if(isPreviewModified == 1) {
            oldPainting.setPreview(newPainting.getPreview());
        }
        paintingDao.update(oldPainting);
    }

    /**
     * 按id删除数据
     * @param id
     */
    public void delete(Integer id) {
        paintingDao.delete(id);
    }

    public static void main(String[] args) {
        PaintingService paintingService = new PaintingService();

        //这里要考虑到 page*row不能太超过 totoalRows
        PageModel pageModel = paintingService.pagination(1,4);
        List<Painting> paintingList = pageModel.getPageData();
        for(Painting painting:paintingList) {
            System.out.println(painting.getPname());
        }
        System.out.println(pageModel.getPageStartRow() + ":" + pageModel.getPageEndRow());
    }




}
