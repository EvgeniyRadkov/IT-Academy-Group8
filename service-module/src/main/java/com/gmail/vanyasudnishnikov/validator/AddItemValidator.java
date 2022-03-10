package com.gmail.vanyasudnishnikov.validator;

import com.gmail.vanyasudnishnikov.impl.AddingItemServiceImpl;
import com.gmail.vanyasudnishnikov.model.ItemDTO;
import com.gmail.vanyasudnishnikov.model.ViewItemDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddItemValidator implements ConstraintValidator<NotNullDTO, ItemDTO> {
    private final AddingItemServiceImpl addingItemService;

    public AddItemValidator(AddingItemServiceImpl addingItemService) {
        this.addingItemService = addingItemService;
    }

    @Override
    public boolean isValid(ItemDTO itemDTO, ConstraintValidatorContext constraintValidatorContext) {
        ViewItemDTO addItem = addingItemService.add(itemDTO);
        return addItem != null;
    }
}
