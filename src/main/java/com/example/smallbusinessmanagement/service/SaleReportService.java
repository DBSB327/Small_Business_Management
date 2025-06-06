package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.model.Sale;
import com.example.smallbusinessmanagement.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleReportService {

    private final SaleRepository saleRepository;
    private final SaleService saleService;


    public byte[] generateSalesExcelReport(LocalDate startDate, LocalDate endDate) throws IOException {
        List<Sale> sales = getSalesForPeriod(startDate, endDate);

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Sales Report");
            fillExcelSheet(sheet, sales);
            workbook.write(baos);

            return baos.toByteArray();
        }
    }

    private List<Sale> getSalesForPeriod(LocalDate startDate, LocalDate endDate) {
        return saleRepository.findByDateBetween(
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59)
        );
    }


    private void fillExcelSheet(Sheet sheet, List<Sale> sales) {
        createExcelHeaderRow(sheet.createRow(0));

        int rowNum = 1;
        for (Sale sale : sales) {
            Row row = sheet.createRow(rowNum++);
            fillExcelRow(row, sale);
        }
    }

    private void createExcelHeaderRow(Row headerRow) {
        String[] headers = {"Дата", "Клиент", "Сотрудник", "Количество продукта", "Сумма", "Метод оплаты"};
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
    }

    private String getCustomerName(Sale sale) {
        return sale.getCustomer() != null ? sale.getCustomer().getFullName() : "Guest";
    }

    private void fillExcelRow(Row row, Sale sale) {
        row.createCell(0).setCellValue(sale.getDate().toString());
        row.createCell(1).setCellValue(getCustomerName(sale));
        row.createCell(2).setCellValue(sale.getEmployee().getFullName());
        row.createCell(3).setCellValue(sale.getItems().size());
        BigDecimal total = saleService.calculateTotal(sale.getItems(), sale.getDiscount());
        row.createCell(4).setCellValue(total.doubleValue());
        row.createCell(5).setCellValue(sale.getPaymentMethod().name());
    }
}