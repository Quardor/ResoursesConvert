package com.example.resoursesconvert.data.license;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class License {

    private final String license;
    private final String server;
    private final String plugin;
    private final String requestKey;

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getLicense() {
        return license;
    }

    public String getServer() {
        return server;
    }

    public String getPlugin() {
        return plugin;
    }

    public String getRequestKey() {
        return requestKey;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isValid() {
        return valid;
    }

    public ReturnType getReturnType() {
        return returnType;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public String getLicensedTo() {
        return licensedTo;
    }

    public String getGeneratedIn() {
        return generatedIn;
    }

    private boolean debug = false;
    private boolean valid = false;
    private ReturnType returnType;
    private String generatedBy;
    private String licensedTo;
    private String generatedIn;


    /**
     * {@link License}
     */

    public License(String requestKey, String license, String server, String plugin) {
        this.license = license;
        this.server = server;
        this.plugin = plugin;
        this.requestKey = requestKey;
    }

    public void request() {
        System.Logger logger = System.getLogger(plugin);
        try {
            URL url = new URL(server + "/request.php");
            URLConnection connection = url.openConnection();
            if (debug) logger.log(System.Logger.Level.DEBUG, "[DEBUG] Connecting to request server: " + server + "/request.php");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

            connection.setRequestProperty("License-Key", license);
            connection.setRequestProperty("Plugin-Name", plugin);
            connection.setRequestProperty("Request-Key", requestKey);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (debug) logger.log(System.Logger.Level.DEBUG, "[DEBUG] Reading response");
            if (debug) logger.log(System.Logger.Level.DEBUG, "[DEBUG] Converting to string");
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String response = builder.toString();
            if (debug) logger.log(System.Logger.Level.DEBUG, "[DEBUG] Converted");

            String[] responseSplited = response.split(";");
            if (responseSplited[0].equals("VALID")) {
                if (debug) logger.log(System.Logger.Level.DEBUG, "[DEBUG] VALID LICENSE");
                valid = true;
                returnType = ReturnType.valueOf(responseSplited[0]);

                generatedBy = responseSplited[2];
                generatedIn = responseSplited[3];
                licensedTo = responseSplited[1];
            } else {
                if (debug) logger.log(System.Logger.Level.DEBUG, "[DEBUG] FAILED VALIDATION");
                valid = false;
                returnType = ReturnType.valueOf(responseSplited[0]);

                if (debug) logger.log(System.Logger.Level.DEBUG, "[DEBUG] FAILED WITH RESULT: " + returnType);
            }
        } catch (Exception ex) {
            if (debug) {
                ex.printStackTrace();
            }
        }
    }

}