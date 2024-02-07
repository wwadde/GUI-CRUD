package org.Log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GuardarErrores {

    private Logger LOGGER;

    public GuardarErrores(Class<?> clase) {
        // Se crea un logger para la clase que recibe como parametro
        LOGGER = Logger.getLogger(clase.getName());

        try {
            FileHandler fileHandler = new FileHandler("src/main/java/org/Logerrores.log", true); // true para que los logs se añadan al final del archivo append
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al configurar el manejador de archivos de registro de errores \n" + e);
        }
    }

    public void log(Level level, String mensaje, Exception e) {
        // Se escoge el primer elemento de la pila de llamadas
        StackTraceElement ste = e.getStackTrace()[0];
        String exceptionClass = ste.getClassName();
        int exceptionLine = ste.getLineNumber();
        String descripcion = ste.toString();


        LOGGER.log(level, mensaje + " \nExcepción generada en la clase: " + exceptionClass + ", línea: " + exceptionLine + "\n" + descripcion + " \n");
    }

}
