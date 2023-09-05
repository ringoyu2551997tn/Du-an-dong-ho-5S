package com.datn.dongho5s.Export;

import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Entity.VatLieu;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class VatLieuCsvExporter extends AbstractExporter{
    public void export(List<VatLieu> listVatLieu, HttpServletResponse response)throws IOException {
        super.setResponseHeader(response,"text/csv",".csv","material_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"ID","Name","Description","Enabled"};
        String[] filedMapping = {"idVatLieu","tenVatLieu","moTaVatLieu","enabled"};

        csvBeanWriter.writeHeader(csvHeader);
        for (VatLieu vatLieu: listVatLieu){
            csvBeanWriter.write(vatLieu,filedMapping);
        }
        csvBeanWriter.close();
    }
}
