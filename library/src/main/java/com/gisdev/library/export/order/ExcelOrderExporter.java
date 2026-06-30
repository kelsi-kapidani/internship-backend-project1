package com.gisdev.library.export.order;

import com.gisdev.library.entity.BookLibraryOrder;
import com.gisdev.library.entity.LibraryOrder;
import com.gisdev.library.exception.BadRequestException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class ExcelOrderExporter {

    public byte[] exportOrders(List<LibraryOrder> orders) {

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Orders");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Order ID");
            header.createCell(1).setCellValue("User");
            header.createCell(2).setCellValue("Status");
            header.createCell(3).setCellValue("Total");

            int rowIndex = 1;

            for (LibraryOrder order : orders) {

                Row row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(order.getId());
                row.createCell(1).setCellValue(order.getUser().getName() + " " + order.getUser().getSurname());
                row.createCell(2).setCellValue(order.getStatus().name());

                int total = order.getBooks()
                        .stream()
                        .mapToInt(BookLibraryOrder::getValue)
                        .sum();

                row.createCell(3).setCellValue(total);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            return out.toByteArray();
        } catch (Exception e) {
            throw new BadRequestException("Error generating Excel file");
        }
    }
}
