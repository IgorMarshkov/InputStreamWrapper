package com.itechart;

import com.itechart.core.stream.InputStreamWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) {
        InputStreamWrapper is = null;
        OutputStream os = null;

        try {
            is = new InputStreamWrapper(new Runner().getClass().getResourceAsStream("/test.txt"));
            os = new FileOutputStream("/new_source.txt");

            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = is.read(buffer)) !=-1){
                os.write(buffer, 0, bytesRead);
               // System.out.println(new String(buffer));
            }

            //os.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }
}