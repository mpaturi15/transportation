package com.macys.mst.transportation.functional.services;

import java.util.Map;

import com.macys.mst.transportation.functional.util.StepsDataStore;

public class BaseService {

    private StepsDataStore dataStorage = StepsDataStore.getInstance();

    protected Object get(String key){
        return dataStorage.getStoredData().get(key);
    }

    protected String getString(String key){
        return dataStorage.getStoredData().get(key).toString();
    }

    protected Map<String, Object> dataStore(){
        return dataStorage.getStoredData();
    }
}
