package com.datn.dongho5s.Export;

import com.datn.dongho5s.Entity.KichCo;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class KichCoCsvExporter extends AbstractExporter{
    public void export(List<KichCo> listKichCo, HttpServletResponse response)throws IOException {
        super.setResponseHeader(response,"text/csv",".csv","material_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"ID","Name","Description", "DateUpdated", "Enabled"};
        String[] filedMapping = {"idKichCo","tenKichCo","moTaKichCo", "ngayTaoKichCo","enabled"};

        csvBeanWriter.writeHeader(csvHeader);
        for (KichCo KichCo: listKichCo){
            csvBeanWriter.write(KichCo,filedMapping);
        }
        csvBeanWriter.close();
    }
}
