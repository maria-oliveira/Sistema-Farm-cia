package com.farmacia;

import com.farmacia.service.MenuService;

public class Main {
    public static void main(String[] args) {
        MenuService menu = new MenuService();
        menu.exibirMenu();
    }
}
