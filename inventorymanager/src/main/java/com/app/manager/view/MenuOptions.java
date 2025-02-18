package com.app.manager.view;

public enum MenuOptions {
    ADD_PRODUCT("Add a product"),
    VIEW_PRODUCTS("View Products"),
    SEARCH_PRODUCTS("Search Product"),
    UPDATE_PRODUCT("Update Product"),
    DELETE_PRODUCT("Delete product"),
    EXIT("Exit");

    private String message;

    MenuOptions(String name) {
        this.message = name;
    }

    public String getMessage() {
        return message;
    }
}
