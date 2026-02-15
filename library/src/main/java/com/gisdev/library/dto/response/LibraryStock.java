package com.gisdev.library.dto.response;

import com.gisdev.library.entity.Library;

public record LibraryStock (
        
        Library library,
        Integer stock
){}
