package com.example.smallbusinessmanagement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class SupplyRequest{
        @NotNull Long supplierId;
        @NotEmpty List<SupplyItemRequest> items;
}