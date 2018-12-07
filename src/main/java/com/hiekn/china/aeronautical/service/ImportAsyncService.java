package com.hiekn.china.aeronautical.service;

import java.io.File;

public interface ImportAsyncService {

    void importExcelToDataset(Class cls, File file, String collectionName);

}
