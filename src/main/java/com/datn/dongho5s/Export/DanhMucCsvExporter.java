package com.datn.dongho5s.Export;

import com.datn.dongho5s.Entity.DanhMuc;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DanhMucCsvExporter extends AbstractExporter {
    public void export(List<DanhMuc> listDanhMuc, HttpServletResponse response)throws IOException {
        super.setResponseHeader(response,"text/csv",".csv","categories_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"ID","Name","Enabled"};
        String[] filedMapping = {"id","ten","enabled"};

        csvBeanWriter.writeHeader(csvHeader);
        for (DanhMuc danhMuc: listDanhMuc){
            csvBeanWriter.write(danhMuc,filedMapping);
        }
        csvBeanWriter.close();
    }
}
