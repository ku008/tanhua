package com.ku.service;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsObject;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.gridfs.GridFsUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ku
 * @date 2020/12/25
 */
@Service
public class PictureService {

    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;

    public String uploadOne(MultipartFile file) {
        ObjectId objectId = null;
        try {
            objectId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), "application/octet-stream");
            System.out.println("图片的objectId:"+objectId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectId.toString();
    }

    public List<String> uploadMore(MultipartFile[] files) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            list.add(uploadOne(files[i]));
        }
        return list;
    }

    public Map<String, Object> getOne(String objectId, HttpServletResponse response) throws IOException {
        System.out.println("查询的objectId:"+objectId);
        Map<String, Object> resultMap = new HashMap<>();
        Query query = new Query(Criteria.where("_id").is(objectId));
        GridFSFile gridFSFile = gridFsTemplate.findOne(query);
        if (gridFSFile == null) {
            System.out.println("查不到图片");
            return null;
        }

        gridFSBucket.downloadToStream(gridFSFile.getObjectId(),response.getOutputStream());
        resultMap.put("gridFSFile", gridFSFile);
        return resultMap;
    }

    public void deleteOne(String objectId) {
        System.out.println("删除的objectId:"+objectId);
        Query query = new Query(Criteria.where("_id").is(objectId));
        gridFsTemplate.delete(query);
    }

    public Map<String, Object> getAll(HttpServletResponse response) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        Query query = new Query();
        GridFSFindIterable gridFSFiles = gridFsTemplate.find(query);
        if (gridFSFiles == null) {
            return null;
        }

        MongoCursor<GridFSFile> iterator = gridFSFiles.iterator();
        while (iterator.hasNext()){
            gridFSBucket.downloadToStream(iterator.next().getObjectId(),response.getOutputStream());
        }
        resultMap.put("gridFSFiles", gridFSFiles);
        return resultMap;
    }

    public InputStream getImage(String objectId){
        System.out.println("通过image查询的objectId:"+objectId);
        Query query = new Query(Criteria.where("_id").is(objectId));
        GridFSFile gridFSFile = gridFsTemplate.findOne(query);
        if (gridFSFile == null) {
            System.out.println("查不到图片");
            return null;
        }

        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        return gridFSDownloadStream;
    }

}
