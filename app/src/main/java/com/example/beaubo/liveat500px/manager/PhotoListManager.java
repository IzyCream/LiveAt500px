package com.example.beaubo.liveat500px.manager;

import android.content.Context;

import com.example.beaubo.liveat500px.dao.PhotoItemCollectionDao;
import com.example.beaubo.liveat500px.dao.PhotoItemDao;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;

/**
 * Created by beaubo on 6/10/2017 AD.
 */

public class PhotoListManager {



    private Context mContext;
    private PhotoItemCollectionDao dao;

    public PhotoListManager() {
        mContext = Contextor.getInstance().getContext();

        //Load data from Persistent Storage
    }

    public PhotoItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
        // Save to Persistent Storage
    }
    public void insertDaoAtTopPosition(PhotoItemCollectionDao newDao){
        if(dao == null)
            dao = new PhotoItemCollectionDao ();
        if (dao.getData() == null)
            dao.setData(new ArrayList<PhotoItemDao>());
        dao.getData().addAll(0, newDao.getData());



    }

    public int getMaximumId() {
        if (dao == null)
            return 0;
        if (dao.getData()==null)
            return 0;
        if (dao.getData().size() == 0)
            return  0;
        int maxId = dao.getData().get(0).getId();
        for (int i = 1; i < dao.getData().size(); i++)
            maxId = Math.max(maxId, dao.getData().get(i).getId());
        return maxId;
    }

    public int getCount(){
        if (dao == null)
            return 0;
        if (dao.getData() == null)
            return 0;
        return dao.getData().size();
    }


}

