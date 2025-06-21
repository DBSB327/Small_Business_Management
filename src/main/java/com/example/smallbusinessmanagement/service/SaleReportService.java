package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.enums.TransactionType;
import com.example.smallbusinessmanagement.model.FinancialTransaction;
import com.example.smallbusinessmanagement.model.Sale;
import com.example.smallbusinessmanagement.repository.FinancialTransactionRepository;
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
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleReportService {

    private final SaleRepository saleRepository;
    private final FinancialTransactionRepository transactionRepository; // добавлен репозиторий транзакций
    private final SaleService saleService;

    // Существующий метод для отчёта по продажам
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

    // Новый метод для отчёта по финансовым транзакциям
    public byte[] generateFinancialTransactionsExcelReport(LocalDate start, LocalDate end, TransactionType type) throws IOException {
        List<FinancialTransaction> transactions = getTransactions(start, end, type);

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Financial Transactions");
            createTransactionsHeaderRow(sheet.createRow(0));

            int rowNum = 1;
            for (FinancialTransaction tx : transactions) {
                Row row = sheet.createRow(rowNum++);
                fillTransactionRow(row, tx);
            }

            workbook.write(baos);
            return baos.toByteArray();
        }
    }

    private List<FinancialTransaction> getTransactions(LocalDate start, LocalDate end, TransactionType type) {
        if (start == null) start = LocalDate.of(2000, 1, 1);
        if (end == null) end = LocalDate.now();

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(23, 59, 59);

        if (type != null) {
            return transactionRepository.findByDateBetweenAndType(startDateTime, endDateTime, type);
        } else {
            return transactionRepository.findByDateBetween(startDateTime, endDateTime);
        }
    }

    private void createTransactionsHeaderRow(Row headerRow) {
        String[] headers = {"Дата", "Тип операции", "Сумма", "Описание"};
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
    }

    private void fillTransactionRow(Row row, FinancialTransaction tx) {
        row.createCell(0).setCellValue(tx.getDate().toString());
        row.createCell(1).setCellValue(tx.getType().name());
        row.createCell(2).setCellValue(tx.getAmount().doubleValue());
        row.createCell(3).setCellValue(tx.getDescription() != null ? tx.getDescription() : "");
    }
}
