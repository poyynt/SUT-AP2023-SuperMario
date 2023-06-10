module SuperMario {
    requires java.base;
    requires java.desktop;
    requires com.google.gson;
    requires java.logging;
    opens supermario.models;
    opens supermario.utils;
}