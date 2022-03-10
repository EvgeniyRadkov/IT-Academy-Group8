package com.gmail.vanyasudnishnikov;

import com.gmail.vanyasudnishnikov.model.ItemDTO;
import com.gmail.vanyasudnishnikov.model.ViewItemDTO;

public interface AddingItemService {
    ViewItemDTO add(ItemDTO itemDTO);
}
