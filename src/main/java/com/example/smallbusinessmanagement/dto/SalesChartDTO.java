    package com.example.smallbusinessmanagement.dto;

    import lombok.Getter;
    import lombok.Setter;

    import java.math.BigDecimal;
    import java.util.List;

    @Getter
    @Setter
    public class SalesChartDTO {
        private List<String> labels;
        private List<BigDecimal> data;

    }
